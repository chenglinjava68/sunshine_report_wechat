package com.wechat.service;

import java.util.List;
import java.util.Map;

import com.wechat.entity.Report;

public interface RepostService {

	/**
	 * 添加诊断报告
	 * @param report
	 */
	public void saveReport(Report report);
	
	/**
	 * 根据report实体查询
	 * @param report
	 * @return
	 */
	public Report selectByPrimaryKey(Integer reportId);
	
	/**
	 * 获取试题
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getExamName(String code) throws Exception;
	
	/**
	 * 获取柱状诊断报告
	 * @param jsons
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getReportList(String jsons) throws Exception;

	List<Report> getClassReport(Map<String, Object> queryMap);

	Report selectByChildAndTest(Map<String, Object> queryMap);
	
	public Report selectByChildAnd(String userUniqueId);
}
