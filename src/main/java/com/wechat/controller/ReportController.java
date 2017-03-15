package com.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.entity.ReportOpinion;
import com.wechat.entity.User;
import com.wechat.service.ReportOpinionService;
import com.wechat.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.wechat.entity.Report;
import com.wechat.response.BaseResponse;
import com.wechat.response.ResponseCode;
import com.wechat.service.ArithmeticService;
import com.wechat.service.RepostService;
import com.wechat.util.SoonJson;
import com.wechat.util.jsonToMapUtil;

@Controller
@RequestMapping("/report/")
public class ReportController {
	
	private Gson gson = new Gson();
	@Autowired
	private ArithmeticService arithmeticService;
	@Autowired
	private RepostService repostService;

	@Autowired
	private ReportOpinionService reportOpinionService;

	@Autowired
	private UserService userService;

	/**
	 * 保存诊断报告
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("savereport")
	public @ResponseBody BaseResponse submitResult(HttpServletRequest request, HttpServletResponse response,@RequestBody String parem){
		BaseResponse baseResponse = new BaseResponse();
		/**柱状报告**/
		Report report = new Report();
		try {
			Map<String,Object> paremMap = jsonToMapUtil.parseData(parem);
			Map<String, Object> reportMap = repostService.getReportList(gson.toJson(paremMap.get("test_content")));
			@SuppressWarnings("rawtypes")
			Map data = (Map) reportMap.get("datas");
			List<Map<String, Object>> columnarList = (List<Map<String, Object>>) data.get("Columnar");
			if(columnarList != null && columnarList.size()>0){
				report.setColumnar(gson.toJson(columnarList));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject root = JSONObject.fromObject(parem);
		String testCode = root.get("test_code").toString();// 试卷编号1
		String userUniqueId = root.get("user_id").toString();// 用户唯一标识
		String userScore = root.get("user_score").toString();// 用户成绩(得分)
		String subjectName = root.get("subject_name").toString();// 学科名称
		String gradeName = root.get("grade_name").toString();// 年级名称
		String subjectCode = root.get("subject_code").toString();// 学科code
		String gradeCode = root.get("grade_code").toString();// 年级code
		Integer time = Integer.valueOf(root.get("time").toString());// 完成时间 
		String testTitle = root.get("testTitle").toString();// 试卷标题
		Double totalScore = Double.valueOf(root.get("total_score").toString());// 试卷总分
		/**获取错误知识点**/
		String errorFrequency = arithmeticService.getErrorFrequency(root.getString("test_content"));

		report.setTestCode(testCode);
		report.setUserUniqueId(userUniqueId);
		report.setGradeName(gradeName);
		report.setSubjectName(subjectName);
        report.setGradeCode(gradeCode);
        report.setSubjectCode(subjectCode);
		report.setTestContent(root.getString("test_content"));
		report.setTitle(testTitle + "-试题诊断");
		report.setCreateTime(new Date());
		report.setUseTime(time);
		long round = Math.round(Double.valueOf(userScore));
		report.setUserScore((int)round);
		report.setTotalScore(totalScore.intValue());
		report.setErrorFrequency(errorFrequency);
		/**保存报告**/
		repostService.saveReport(report);
		baseResponse.setResult(report);
		return baseResponse;
	}
	
	/**
	 * 获取诊断报告MAP
	 * 
	 * @param r
	 * @return
	 */
	private Map<String, Object> getMap(Report r) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("reportId", r.getReportId());
		map.put("testCode", r.getTestCode());
		map.put("subjectName", r.getSubjectName());
		map.put("subjectCode", r.getSubjectCode());
		map.put("gradeName", r.getGradeName());
		map.put("gradeCode", r.getGradeCode());
		map.put("title", r.getTitle());
		map.put("totalScore", r.getTotalScore());
		map.put("useTime", r.getUseTime());
		map.put("answers", r.getTestContent());
		map.put("errorFrequency", r.getErrorFrequency() == "" ? null : r.getErrorFrequency());
		return map;
	}
	
	/**
	 * 根据报告id获取诊断报告
	 * @param request
	 * @param response
	 */
	@RequestMapping("diagnose")
	public @ResponseBody BaseResponse getDiagnose(HttpServletRequest request, HttpServletResponse response) {
		BaseResponse baseResponse = new BaseResponse();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jsonString = request.getParameter("data");
		if(jsonString==null || jsonString.equals("")){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString() , "data not find");
		}
		JSONObject root = JSONObject.fromObject(jsonString);
		Integer reportId = Integer.valueOf(root.get("reportId").toString());
		Report reportByEntity = repostService.selectByPrimaryKey(reportId);
		if (reportByEntity == null) {
			baseResponse.setResult(null);
		} else {
			String json = SoonJson.getJson(getMap(reportByEntity));
			baseResponse.setResult(json);
		}
		return baseResponse;
	}
	
	@RequestMapping("examName")
	public @ResponseBody BaseResponse getExamName(HttpServletRequest request,HttpServletResponse response,String code) {
		BaseResponse baseResponse = new BaseResponse();
		try {
			if (null != code && 0 < code.length()) {
				List<Map<String, Object>> examNameList = repostService.getExamName(code);
				if (null != examNameList && 0 < examNameList.size()) {
					baseResponse.setMessage("调用资源库查询试题成功");
					baseResponse.setResult(examNameList);
				} else {
					return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "examNameList");
				}
			} else {
				return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "code");
			}
		} catch (Exception e) {
			baseResponse.setMessage("调用资源库查询试题失败");
			e.printStackTrace();
		}
		return baseResponse;
	}
	
	@RequestMapping(value = "/classReport", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public BaseResponse classReport(String classId, String testCode) {
		BaseResponse baseResponse = new BaseResponse();
		if (StringUtils.isBlank(classId)) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "classId");
		}
		if (StringUtils.isBlank(testCode)) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "testCode");
		}
		try {
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put("classId", classId);
			queryMap.put("testCode", testCode);
			List<Report> reports = this.repostService.getClassReport(queryMap);
			if (reports == null || reports.isEmpty()) {
				return baseResponse;
			}

			Map<String, JSONObject> typecolumnarMap = new HashMap<>();
			Map<String, JSONObject> errorFrequencyMap = new HashMap<>();
			Integer totalScore = 0;
			for (int i = 0; i < reports.size(); i++) {
				Report report = reports.get(i);
				if (report == null) {
					continue;
				}
				totalScore += report.getUserScore();
				if (StringUtils.isNotBlank(report.getColumnar())) {
					JSONArray typecolumnarJson = JSONArray.fromObject(report.getColumnar());
					for (int j = 0; j < typecolumnarJson.size(); j++) {
						JSONObject tc = typecolumnarJson.getJSONObject(j);
						String key = tc.getString("childrenCtbCode");
						if (typecolumnarMap.containsKey(key)) {
							Integer score = tc.getInt("score") + typecolumnarMap.get(key).getInt("score");
							tc.put("score", score);
							typecolumnarMap.put(key, tc);
						} else {
							typecolumnarMap.put(key, tc);
						}
					}
				}
				if (StringUtils.isNotBlank(report.getErrorFrequency())) {
					JSONArray errorFrequencyArray = JSONArray.fromObject(report.getErrorFrequency());
					for (int j = 0; j < errorFrequencyArray.size(); j++) {
						JSONObject errorFrequencyJson = errorFrequencyArray.getJSONObject(j);
						if (errorFrequencyJson == null) {
							continue;
						}
						String typeCode = errorFrequencyJson.getString("knowledgeCode");
						Integer score = errorFrequencyJson.getInt("errorSize");
						if (errorFrequencyMap.containsKey(typeCode)) {
							JSONObject classErrorJson = errorFrequencyMap.get(typeCode);
							Integer classErrorSize = classErrorJson.getInt("errorSize");
							classErrorJson.put("errorSize", score + classErrorSize);
							errorFrequencyMap.put(typeCode, classErrorJson);
						} else {
							errorFrequencyMap.put(typeCode, errorFrequencyJson);
						}
					}
				}
			}

			JSONArray typecolumnar = new JSONArray();
			Iterator<String> typecolumnarIt = typecolumnarMap.keySet().iterator();
			while (typecolumnarIt.hasNext()) {
				String key = typecolumnarIt.next();
				JSONObject tc = typecolumnarMap.get(key);
				JSONObject cJson = new JSONObject();
				cJson.put("typeName", tc.getString("childrenCtbCodeName"));
				cJson.put("typeCode", tc.getString("childrenCtbCode"));
				cJson.put("score", tc.getInt("score") / reports.size());
				typecolumnar.add(cJson);
			}
			JSONArray errorFrequency = new JSONArray();
			errorFrequency.addAll(errorFrequencyMap.values());

			JSONObject result = new JSONObject();
			result.put("typecolumnar", typecolumnar);
			result.put("errorFrequency", errorFrequency);
			result.put("average", totalScore / reports.size());
			baseResponse.setResult(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			baseResponse.isFail(ResponseCode.SERVICE_ERROR, "服务器异常");
		}
		return baseResponse;
	}

	@RequestMapping("getChildReport")
	public @ResponseBody BaseResponse getChildReport(String childId, String testCode) {
		BaseResponse baseResponse = new BaseResponse();
		try {
			if(StringUtils.isBlank(childId)){
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString() , "childId");
			}
			if(StringUtils.isBlank(testCode)){
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString() , "testCode");
			}
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put("childId", childId);
			queryMap.put("testCode", testCode);
			Report reportByEntity = repostService.selectByChildAndTest(queryMap);
			if (reportByEntity == null) {
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.code, "未查到有效数据");
			}
			ReportOpinion reportOpinion = this.reportOpinionService.selectByReportId(reportByEntity.getReportId());
			if (reportOpinion == null) {
				reportByEntity.setIsEvaluated(1);
			} else {
				reportByEntity.setIsEvaluated(0);
				reportByEntity.setOpinionId(reportOpinion.getId());
			}
			baseResponse.setResult(reportByEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseResponse;
	}

	/**
	 * 获取老师对报告的评价
	 *
	 * @param opinionId
	 * @return
     */
	@RequestMapping("opinion")
	public @ResponseBody BaseResponse opinion(Integer opinionId) {
		BaseResponse baseResponse = new BaseResponse();
		try {
			if(opinionId == null){
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString() , "opinionId");
			}
			ReportOpinion reportOpinion = this.reportOpinionService.selectByPrimaryKey(opinionId);
			if (reportOpinion != null) {
				User teacher = this.userService.queryById(reportOpinion.getTeacherId());
				reportOpinion.setTeacher(teacher);
			}
			baseResponse.setResult(reportOpinion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baseResponse;
	}

	/*@RequestMapping("/getReport"})
	@ResponseBody
	public BaseResponse getReport(String classId, String testCode) {
		BaseResponse baseResponse = new BaseResponse();
		if (StringUtils.isBlank(classId)) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "classId");
		}
		if (StringUtils.isBlank(testCode)) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "testCode");
		}
		try {
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put("classId", classId);
			queryMap.put("testCode", testCode);
			List<Report> reports = this.repostService.getClassReport(queryMap);
			if (reports == null || reports.isEmpty()) {
				return baseResponse;
			}
			Map<String, Knowledge> errorMap = new HashMap<String, Knowledge>();
			Map<String, Map<String, String>> columnarMap = new HashMap<String, Map<String, String>>();
			List<Knowledge> list1 = new ArrayList<Knowledge>();
			List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
			for (Report report : reports) {
				String errorFrequency = report.getErrorFrequency();
				List<Knowledge> list = gson.fromJson(errorFrequency, new TypeToken<List<Knowledge>> () {}.getType());
				for (Knowledge knowledge : list) {
					Integer errorSize = knowledge.getErrorSize();
					String knowledgeCode = knowledge.getKnowledgeCode();
					String knowledgeName = knowledge.getKnowledgeName();
					Integer m = errorMap.get(knowledgeCode)==null?0:errorMap.get(knowledgeCode).getErrorSize();
					errorSize = errorSize + m;
					knowledge.setErrorSize(errorSize);
					errorMap.put(knowledgeCode, knowledge);
				}
				String columnar = report.getColumnar();
				List<Map<String, String>> columnarList = gson.fromJson(columnar, new TypeToken<List<Map<String, String>>> () {}.getType());
				for (Map<String, String> map : columnarList) {
					Map<String, String> map2 = new HashMap<String, String>();
					String childrenCtbCodeName = map.get("childrenCtbCodeName");
					String childrenCtbCode = map.get("childrenCtbCode");
					int score = Integer.parseInt(map.get("score").substring(0, map.get("score").lastIndexOf(".")));
					map2.put("childrenCtbCodeName", childrenCtbCodeName);
					map2.put("childrenCtbCode", childrenCtbCode);
					map2.put("score", score+"");
					//Integer k = columnarMap.get(childrenCtbCode)==null?0:Integer.parseInt(columnarMap.get(childrenCtbCode).get("score").substring(0, columnarMap.get(childrenCtbCode).get("score").lastIndexOf(".")));
					Integer k = columnarMap.get(childrenCtbCode)==null?0:Integer.parseInt(columnarMap.get(childrenCtbCode).get("score"));
					score = score + k;
					map2.put("score", score+"");
					columnarMap.put(childrenCtbCode, map2);
				}
			}
			for (String key : errorMap.keySet()) {
				//BigDecimal bigDecimal = new BigDecimal(errorMap.get(key).getErrorSize()/errorMap.size()+"").setScale(0, BigDecimal.ROUND_HALF_UP);
				int round = (int) Math.ceil(errorMap.get(key).getErrorSize()/reports.size());
				if (round == 0) {
					round=1;
				}
				errorMap.get(key).setErrorSize(round);
				list1.add(errorMap.get(key));
			}
			for (String key : columnarMap.keySet()) {
				//BigDecimal bigDecimal = new BigDecimal(Integer.parseInt(columnarMap.get(key).get("score"))/columnarMap.size()+"").setScale(0, BigDecimal.ROUND_HALF_UP);
				int parseInt = Integer.parseInt(columnarMap.get(key).get("score"));
				int round = Math.round(parseInt/reports.size());
				columnarMap.get(key).put("score",round + "");
				list2.add(columnarMap.get(key));
			}
			System.out.println("list1====" + gson.toJson(list1));
			System.out.println("list2====" + gson.toJson(list2));
			Map<String,Object> map9 = new HashMap<String, Object>();
			map9.put("typecolumnar", list2);
			map9.put("errorFrequency", list1);
			System.out.println(gson.toJson(map9));
			baseResponse.setResult(map9);
		} catch (Exception ex) {
			ex.printStackTrace();
			baseResponse.isFail(ResponseCode.SERVICE_ERROR, "服务器异常");
		}
		return baseResponse;
	}*/	
}
