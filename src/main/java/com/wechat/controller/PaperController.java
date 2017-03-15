

/**
 * <pre>项目名称:sunshine_report_wechat
 * 文件名称:PaperController.java
 * 包名:com.wechat.controller
 * 创建日期:2017年1月9日下午3:40:01
 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre>
 */

package com.wechat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.entity.ReportOpinion;
import com.wechat.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wechat.entity.Order;
import com.wechat.entity.Paper;
import com.wechat.entity.Report;
import com.wechat.response.BaseResponse;
import com.wechat.response.ResponseCode;

@Controller
@RequestMapping("/paperController")
public class PaperController {

    private final Logger logger = LoggerFactory.getLogger(PaperController.class);
    private Gson gson = new Gson();

    @Autowired
    private PaperService paperServie;

    @Autowired
    private RepostService repostService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReportOpinionService reportOpinionService;

    @Autowired
    private UserService userService;

    @RequestMapping("/getPaperContent")
    @ResponseBody
    public BaseResponse getPaperContent(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("url=/paperController/getPaperContent" + "requestBody====" + requestBody);
        if (StringUtils.isNotBlank(requestBody)) {
            Map<String, String> map = gson.fromJson(requestBody, new TypeToken<Map<String, String>>() {
            }.getType());
            if (StringUtils.isBlank(map.get("gradeCode"))) {
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".gradeCode");
            }
            if (StringUtils.isBlank(map.get("subjectCode"))) {
            	return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".subjectCode");
            }
            /*if (StringUtils.isBlank(map.get("childId"))) {
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".childId");
			}*/
            Paper paper = new Paper();
            paper.setGradecode(Integer.parseInt(map.get("gradeCode")));
            paper.setSubjectcode(Integer.parseInt(map.get("subjectCode")));
            try {
                Paper paperMes = paperServie.selectPaperByGradeCode(paper);
                if (paperMes == null) {
                    return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "未查到有效数据");
                }
                String content = paperMes.getContent();
                if (StringUtils.isBlank(content)) {
                    return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".content");
                }
                Map<String, Object> paperMap = gson.fromJson(content, new TypeToken<Map<String, Object>>() {
                }.getType());
                Object object = paperMap.get("datas");
                String json = gson.toJson(object);
                Map<String, Object> paperMap2 = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
                }.getType());
                String testCode = paperMap2.get("id").toString();
                paperMap2.put("gradeCode", paperMes.getGradecode());
                paperMap2.put("version", paperMes.getVersion());
                paperMap2.put("price", paperMes.getPrice());
                paperMap2.put("paperId", paperMes.getId());
                Map<String, Object> queryMap = new HashMap<>();
                queryMap.put("childId", map.get("childId"));
                queryMap.put("testCode", testCode);
                Report reportByEntity = repostService.selectByChildAndTest(queryMap);
                Order order = new Order();
                order.setUserId(map.get("childId"));
                order.setPaperId(testCode);
                order.setState("1");
                Order selectByUserInfo = orderService.selectByUserInfo(order);
                if (null == reportByEntity) {
                    paperMap2.put("isStudy", "1");
                } else {
                    ReportOpinion reportOpinion = this.reportOpinionService.selectByReportId(reportByEntity.getReportId());
                    if (reportOpinion == null) {
                        paperMap2.put("isEvaluated", "1");
                    } else {
                        paperMap2.put("isEvaluated", "0");
                        paperMap2.put("opinionId", reportOpinion.getId());
                    }
                    paperMap2.put("isStudy", "0");
                }
                if (null == selectByUserInfo) {
                    paperMap2.put("isBuy", "1");
                } else {
                    paperMap2.put("isBuy", "0");
                }
                baseResponse.setResult(paperMap2);
            } catch (Exception e) {
                BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "paper");
                e.printStackTrace();
            }
        } else {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".requestBody");
        }
        return baseResponse;
    }
}

	