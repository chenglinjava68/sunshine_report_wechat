package com.wechat.entity;

/**
 * 统一下单   发送参数模型
 * @author pc
 *
 */
public class UnifiedOrder {
 
 
	/**
	 * 公众账号ID
	 */
	private static String appid;
	/**
	 * 商品描述
	 */
	private static String body ;
	
	/**
	 * 商户号
	 */
	private static String mch_id ;
	
	/**
	 * 随机字符串
	 */
	private static String nonce_str ;
	
	/**
	 * 通知地址
	 */
	private static String notify_url ;
	
	/**
	 * 商户订单号
	 */
	private static String out_trade_no ;
	
	/**
	 * 终端IP
	 */
	private static String spbill_create_ip ;
	
	
	/**
	 * 总金额
	 */
	private static String total_fee;
	
	/**
	 * 交易类型
	 */
	private static String trade_type;
	
	/**
	 * API密钥
	 */
	private static String key;
	
	public static String getAppid() {
		return appid;
	}

	public static void setAppid(String appid) {
		UnifiedOrder.appid = appid;
	}

	public static String getBody() {
		return body;
	}

	public static void setBody(String body) {
		UnifiedOrder.body = body;
	}

	public static String getMch_id() {
		return mch_id;
	}

	public static void setMch_id(String mch_id) {
		UnifiedOrder.mch_id = mch_id;
	}

	public static String getNonce_str() {
		return nonce_str;
	}

	public static void setNonce_str(String nonce_str) {
		UnifiedOrder.nonce_str = nonce_str;
	}

	public static String getNotify_url() {
		return notify_url;
	}

	public static void setNotify_url(String notify_url) {
		UnifiedOrder.notify_url = notify_url;
	}

	public static String getOut_trade_no() {
		return out_trade_no;
	}

	public static void setOut_trade_no(String out_trade_no) {
		UnifiedOrder.out_trade_no = out_trade_no;
	}

	public static String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public static void setSpbill_create_ip(String spbill_create_ip) {
		UnifiedOrder.spbill_create_ip = spbill_create_ip;
	}

	public static String getTotal_fee() {
		return total_fee;
	}

	public static void setTotal_fee(String total_fee) {
		UnifiedOrder.total_fee = total_fee;
	}

	public static String getTrade_type() {
		return trade_type;
	}

	public static void setTrade_type(String trade_type) {
		UnifiedOrder.trade_type = trade_type;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		UnifiedOrder.key = key;
	}

	public static String getOpenid() {
		return openid;
	}

	public static void setOpenid(String openid) {
		UnifiedOrder.openid = openid;
	}

	/**
	 * 用户openid
	 */
	private static String openid;
	
}
