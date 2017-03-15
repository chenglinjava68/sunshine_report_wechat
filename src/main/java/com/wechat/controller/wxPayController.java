package com.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.entity.*;
import com.wechat.service.*;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wechat.response.BaseResponse;
import com.wechat.response.ResponseCode;
import com.wechat.sys.CommonConstant;
import com.wechat.util.DESEncode;
import com.wechat.util.HttpRequest;
import com.wechat.util.HttpServletStream;
import com.wechat.util.Md5;
import com.wechat.util.OrderNoUtil;
import com.wechat.util.ParaXml;
import com.wechat.util.SoonJson;
import com.wechat.util.jsonToMapUtil;

@Controller
@RequestMapping("/pay/")
public class wxPayController {
	
	private Logger logger = LoggerFactory.getLogger(wxPayController.class);
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private PaperService paperService;
	@Autowired
	private WeixinPayService weixinPayService;
	@Autowired
	private ChildService childService;
	@Autowired
	private SchoolClassService schoolClassService;
	@Autowired
	private CashCouponService cashCouponService;
	/**
	 * 授权获取openid
	 * @return
	 */
	@RequestMapping("getOpenid")
	public String getOpenid(HttpServletResponse response,HttpServletRequest request,Model model){
		BaseResponse baseResponse = new BaseResponse();
		if(request.getParameter("code").equals("") || request.getParameter("code") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PRIVILEGE_GRANT_FAILED.toString(), ".code");
		}
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+CommonConstant.weixinappid+""
				+ "&secret="+CommonConstant.weixinSecret+"&code="+request.getParameter("code")+"&grant_type=authorization_code";
		String openid="";
		try {
			URL getUrl = new URL(url);
			HttpURLConnection http = (HttpURLConnection) getUrl.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] b = new byte[size];
			is.read(b);
			String message = new String(b, "UTF-8");
			JSONObject json = JSONObject.fromObject(message);
			openid = json.getString("openid");
			if(StringUtils.isBlank(openid)){
				return BaseResponse.setResponse(baseResponse, ResponseCode.PRIVILEGE_GRANT_FAILED.toString(), ".openid");
			}
			model.addAttribute("openid", openid);
			model.addAttribute("redirectUrl", request.getParameter("state"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "index";
	}

	/**
	 * 检测代金券是否存在
	 *
	 * @param params
	 * @return
     */
	@RequestMapping("cashCouponCheck")
	public @ResponseBody BaseResponse cashCouponCheck(@RequestBody String params){
		BaseResponse baseResponse = new BaseResponse();

		if(StringUtils.isBlank(params)){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "params");
		}
		try {
			JSONObject parseData = JSONObject.fromObject(params);
			if(StringUtils.isBlank(parseData.getString("cashCouponCode"))){
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "cashCouponCode");
			}
			CashCoupon cashCoupon = this.cashCouponService.selectByPrimaryKey(parseData.getString("cashCouponCode"));
			if (cashCoupon == null || !cashCoupon.getStatus().equals(1)) {
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.code, "cashCouponCode");
			}
			baseResponse.setResult(cashCoupon);
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.code, "cashCouponCode");
		}
	}

	/**
	 * 统一下单
	 * @param parem
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("placeanorder")
	public @ResponseBody BaseResponse placeAnOrder(@RequestBody String parem){
		BaseResponse baseResponse = new BaseResponse();
		if(StringUtils.isBlank(parem)){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".parem");
		}
		Map<String, Object> parseData = jsonToMapUtil.parseData(parem);
		if(parseData.get("paperName").equals("") || parseData.get("paperName") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".paperName");
		}
		if(parseData.get("sunuserid").equals("") || parseData.get("sunuserid") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".sunuserid");
		}
		if(parseData.get("paperId").equals("") || parseData.get("paperId") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".paperId");
		}
		if(parseData.get("userid").equals("") || parseData.get("userid") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".userid");
		}
		if(parseData.get("openid").equals("") || parseData.get("openid") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".openid");
		}
		if(parseData.get("ipaddress").equals("") || parseData.get("ipaddress") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".ipaddress");
		}
		if(parseData.get("price").equals("") || parseData.get("price") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".price");
		}
		Order order = new Order();
		order.setPaperName(parseData.get("paperName").toString());
		order.setSunuserid(parseData.get("sunuserid").toString());
		order.setPaperId(parseData.get("paperId").toString());
		order.setUserId(parseData.get("userid").toString());
		order.setChannel(ChannelType.ChannelTypes.SUNTYPE.getValue());
		order.setState("0");
		order.setTotalAmout(parseData.get("price").toString());
		order.setSerialNumber("YJLT"+OrderNoUtil.getOrderNumber(""));
		order.setOutTradeNo(OrderNoUtil.getOrderNumber(""));
		order.setCreateTime(new Date());
		order.setUpdateTime(new Date());
		orderService.saveOrder(order);
		UnifiedOrder Unified = new UnifiedOrder();
		Unified.setAppid(CommonConstant.weixinappid);
		Unified.setMch_id(CommonConstant.weixinpartnerid);
		Unified.setKey(CommonConstant.weixinpartnerKey);
		Unified.setBody(parseData.get("paperName").toString());
		Unified.setNotify_url(CommonConstant.notify_url);
		Unified.setOut_trade_no(order.getOutTradeNo());
		Unified.setOpenid(parseData.get("openid").toString());
		Unified.setNonce_str(UUID.randomUUID().toString().replace("-", "").toUpperCase());
		Unified.setSpbill_create_ip(parseData.get("ipaddress").toString());
		Integer price = (int) (Double.valueOf(parseData.get("price").toString()) * 100);
		if (parseData.get("cashCouponCode") != null) {
				String cashCouponCode = String.valueOf(parseData.get("cashCouponCode"));
				CashCoupon cashCoupon = this.cashCouponService.selectByPrimaryKey(cashCouponCode);
				if (cashCoupon != null) {
					Integer denomination = cashCoupon.getDenomination();
					price -= denomination * 100;
				}
		}
		Unified.setTotal_fee(String.valueOf(price));
		Unified.setTrade_type("JSAPI");
		logger.info("开始生成预支付id===========================");
		UnifiedOrderReturn uor = weixinPayService.unifiedorder(Unified);
		/**生成 预支付编号**/
		String prepayid = uor.getPrepay_id();
		logger.info("prepayid==========" + prepayid);
		String packag = "Sign=WXPay";
		String noncestr = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		Long timestamp = new Date().getTime() / 1000;
		/**使用此TreeMap用于排序**/
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("appid", CommonConstant.weixinappid);
		map.put("partnerid", CommonConstant.weixinpartnerid);
		map.put("prepayid", prepayid);
		map.put("package", packag);
		map.put("noncestr", noncestr);
		map.put("timestamp", timestamp.toString());
		String signString = "appId=" + CommonConstant.weixinappid + "&nonceStr=" + noncestr + ""
				+ "&package=prepay_id=" + prepayid + "&signType=MD5&timeStamp=" + timestamp.toString() + "&key=" + CommonConstant.weixinpartnerKey + "";
		map.put("paysign", Md5.GetMD5Code(signString).toUpperCase());
		Map<String, Object> rMap = new LinkedHashMap<>();
		rMap.put("appid", map.get("appid"));
		rMap.put("partnerid", map.get("partnerid"));
		rMap.put("prepayid", map.get("prepayid"));
		rMap.put("package", map.get("package"));
		rMap.put("noncestr", map.get("noncestr"));
		rMap.put("timestamp", map.get("timestamp"));
		rMap.put("paysign", map.get("paysign"));
		rMap.put("outTradeNo", order.getOutTradeNo());
		String rjson = SoonJson.getJson(rMap);
		baseResponse.setResult(rjson);
		logger.info("下单成功==============");
		return baseResponse;
	}
	
	/**
	 * 内部支付
	 * @param parem 待添加抵扣代金券功能
	 * @return
	 */
	@RequestMapping("insidePay")
	public @ResponseBody BaseResponse insidePay(@RequestBody String parem){
		BaseResponse baseResponse = new BaseResponse();
		if(StringUtils.isBlank(parem)){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".parem");
		}
		Map<String, Object> parseData = jsonToMapUtil.parseData(parem);
		if(parseData.get("paperName").equals("") || parseData.get("paperName") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".paperName");
		}
		if(parseData.get("sunuserid").equals("") || parseData.get("sunuserid") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".sunuserid");
		}
		if(parseData.get("paperId").equals("") || parseData.get("paperId") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".paperId");
		}
		if(parseData.get("userid").equals("") || parseData.get("userid") == null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".userid");
		}
		Order order = new Order();
		order.setPaperName(parseData.get("paperName").toString());
		order.setSunuserid(parseData.get("sunuserid").toString());
		order.setPaperId(parseData.get("paperId").toString());
		order.setUserId(parseData.get("userid").toString());
		order.setChannel(ChannelType.ChannelTypes.SUNTYPE.getValue());
		order.setState("1");
		order.setTotalAmout("0");
		order.setSerialNumber("YJLT"+OrderNoUtil.getOrderNumber(""));
		order.setOutTradeNo(OrderNoUtil.getOrderNumber(""));
		order.setCreateTime(new Date());
		order.setUpdateTime(new Date());
		orderService.saveOrder(order);
		CashCoupon cashCoupon;
		try {
			if (StringUtils.isNotBlank(parseData.get("cashCouponCode").toString())) {
				cashCoupon = cashCouponService.selectByPrimaryKey(parseData.get("cashCouponCode").toString());
				if (cashCoupon != null) {
					cashCoupon.setStatus(2);
					this.cashCouponService.updateByPrimaryKey(cashCoupon);
				}
			}
		} catch (Exception ex) {
			logger.error("代金券已使用状态修改失败 cashCouponCode：" + order.getCashCouponCode());
			ex.printStackTrace();
		}
		baseResponse.setResult(order.getOutTradeNo());
		return baseResponse;
	}
	
	/**
	 * 微信支付回调
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "notify", method = RequestMethod.POST)
	public @ResponseBody void notify(HttpServletRequest request, HttpServletResponse response,Order order) {
		logger.info("进入回调url============");
		try {
			String xml = HttpServletStream.getString(request);
			ParaXml px = new ParaXml(xml);
			String return_code = px.getValue("return_code");
			if (return_code.equals("SUCCESS")) {
				/**调用阳光收益接口**/
				order = orderService.getByOrderNo(px.getValue("out_trade_no"));
				if(order==null){
					logger.error("order===不存在！");
				}
				double proportion = 0.2d;
				double tolamout = Double.valueOf(order.getTotalAmout());
				String dess = "E50A24CFFE3DFD26D4EC2C6BE6A4856A";
				String keys = order.getSerialNumber()+order.getTotalAmout()+dess;
				Child child = new Child();
				Map<String, String> map = new HashMap<String, String>();
				child.setChildId(order.getUserId());
				List<Child> childList = childService.selectByPrimaryKey(child);
				if (null != childList && 0 < childList.size()) {
					for (Child child3 : childList) {
						SchoolClass schoolClass = new SchoolClass();
						schoolClass.setId(Long.parseLong(child3.getClasses()));
						SchoolClass schoolClass2 = schoolClassService.selectMesByClassId(schoolClass);
						SchoolClass schoolClass3 = schoolClassService.selectMesByPid(schoolClass2);
						if (null == schoolClass3) {
							map.put("schoolName", schoolClass2.getName());
							continue;
						}
						SchoolClass schoolClass4 = schoolClassService.selectMesByPid(schoolClass3);
						map.put("school", schoolClass4.getName());
						map.put("grade", schoolClass3.getName().toString());
						map.put("class", schoolClass2.getName().toString());
						map.put("province", "山东省");
						map.put("city", "宝鸡市");
						map.put("zone", "陈仓区");
					}
				}
				Gson gson = new Gson();
				DecimalFormat defor = new DecimalFormat( "#,##0.000");
				String url = "http://m.51jindian.net/publicAPI/interestInterface?jdUserId="+DESEncode.encode(DESEncode.decode(order.getSunuserid(), dess), keys)+""
						+ "&amount="+defor.format(proportion*tolamout)+"&serialNum="+order.getSerialNumber()+"&channelNum="+order.getChannel()+"&otherInfo="+URLDecoder.decode(gson.toJson(map), "UTF-8")+"";
				String requestJson = HttpRequest.getRequest(url);
				logger.info("requestJson====" + requestJson);
				if(requestJson!=null){
					Map<String, Object> resultMap = gson.fromJson(requestJson, new TypeToken<Map<String, Object>> () {}.getType());
					if(resultMap.get("status").equals("false")){
						logger.error("调用阳光收益接口失败！");
					}
				}
				order.setSunAmout(defor.format(proportion*tolamout));
				order.setOutTradeNo(px.getValue("out_trade_no"));
				order.setState("1");
				orderService.updateByOrderNo(order);
				logger.info(order.getOutTradeNo()+"支付成功============");
				CashCoupon cashCoupon;
				try {
					if (StringUtils.isNotBlank(order.getCashCouponCode())) {
						cashCoupon = this.cashCouponService.selectByPrimaryKey(order.getCashCouponCode());
						if (cashCoupon != null) {
							cashCoupon.setStatus(2);
							this.cashCouponService.updateByPrimaryKey(cashCoupon);
						}
					}
				} catch (Exception ex) {
					logger.error("代金券已使用状态修改失败 cashCouponCode：" + order.getCashCouponCode());
					ex.printStackTrace();
				}
			} else {
				logger.error("支付失败");
			}
		}catch (Exception ex){
			logger.error("微信支付回调 notify error",ex);
			return;
		}
	}
	
}
