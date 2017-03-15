package com.wechat.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wechat.dao.ReportMapper;
import com.wechat.entity.Report;
import com.wechat.service.RepostService;
import com.wechat.util.HTTPClientUtils;
import com.wechat.util.jsonToMapUtil;

@Service
public class RepostServiceimp implements RepostService{

	private Logger logger = LoggerFactory.getLogger(RepostServiceimp.class);

	private Gson gson = new Gson();
	
	@Value("${sun.examNameList.url.all}")
	private String examNameListUrl;
	
	@Value("${sun.report.url}")
	private String reportUrl;
	
	@Autowired
	private ReportMapper reportMapper; 

	@Override
	public void saveReport(Report report) {
		reportMapper.insertSelective(report);
	}
	
	@Override
	public Report selectByPrimaryKey(Integer reportId) {
		return reportMapper.selectByPrimaryKey(reportId);
	}
	
	@Override
	public List<Map<String, Object>> getExamName(String code) throws Exception {
		String httpGetRequestJson = HTTPClientUtils.httpGetRequestJson(examNameListUrl + code);
		Map<String, Object> fromJsonMap = null;
		if (null != httpGetRequestJson && 0 < httpGetRequestJson.length()) {
			fromJsonMap = gson.fromJson(httpGetRequestJson, new TypeToken<Map<String, Object>>() {}.getType());
		}
		String json = gson.toJson(fromJsonMap.get("questions"));
		List<Map<String, Object>> mapList = null;
		if (null != json && 0 < json.length()) {
			mapList = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
		}
		return mapList;
			
	}
	
	@Override
	public Map<String, Object> getReportList(String jsons) throws Exception {
		String httpGetRequestJson = HTTPClientUtils.httpPostRequestJson(reportUrl,jsons);
		logger.info("getReportList reponse:" + httpGetRequestJson);
		Map<String, Object> fromJsonMap = null;
		if (null != httpGetRequestJson && 0 < httpGetRequestJson.length()) {
			fromJsonMap = jsonToMapUtil.parseData(httpGetRequestJson);
		}
		return fromJsonMap;
	}

	@Override
	public List<Report> getClassReport(Map<String, Object> queryMap) {
		return reportMapper.getClassReport(queryMap);
	}

	@Override
	public Report selectByChildAndTest(Map<String, Object> queryMap) {
		return reportMapper.selectByChildAndTest(queryMap);
	}

	@Override
	public Report selectByChildAnd(String userUniqueId) {
		return reportMapper.selectByChildAnd(userUniqueId);
	}

}
