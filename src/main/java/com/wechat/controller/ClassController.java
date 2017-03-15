
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:ClassController.java 
	 * 包名:com.wechat.controller 
	 * 创建日期:2017年1月9日下午5:37:48 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.wechat.entity.Child;
import com.wechat.entity.SchoolClass;
import com.wechat.response.BaseResponse;
import com.wechat.response.ResponseCode;
import com.wechat.service.ChildService;
import com.wechat.service.SchoolClassService;
import com.wechat.service.TeacherClassService;
import com.wechat.util.GradeEum;
import com.wechat.util.redis.RedisClientTemplate;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：ClassController    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年1月9日 下午5:37:48    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年1月9日 下午5:37:48    
 * 修改备注：       
 * @version </pre>    
 */
@Controller
@RequestMapping("/classController")
public final class ClassController {

	private final Logger logger = LoggerFactory.getLogger(ClassController.class);
	
	private Gson gson = new Gson();
	
	@Autowired
	private TeacherClassService teacherClassService;
	
	@Autowired
	private SchoolClassService schoolClassService;
	
	@Autowired
	private ChildService childService;
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	@RequestMapping("/getChildList")
	@ResponseBody
	public BaseResponse getChildList(HttpServletRequest request,HttpServletResponse response,@RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse();
		logger.info("url=/classController/getChildList" + "requestBody====" + requestBody);
		if (StringUtils.isBlank(requestBody)) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".requestBody");
		}
		Child child = new Child();
		Map<String,String> map = gson.fromJson(requestBody, new TypeToken<Map<String,String>> () {}.getType());
		
		if (StringUtils.isBlank(map.get("classes"))) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".classes");
		}
		child.setClasses(map.get("classes"));
		try {
			List<Child> childList = teacherClassService.selectByClassId(child);
			baseResponse.setResult(childList);
		} catch (Exception e) {
			baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(), ".server");
			// TODO Auto-generated catch block
			e.printStackTrace();
				
		}
		return baseResponse;
	}
	
	@RequestMapping("/getClass")
	@ResponseBody
	public BaseResponse getClass(HttpServletRequest request,HttpServletResponse response,@RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse();
		logger.info("url=/classController/getClass" + "requestBody====" + requestBody);
		if (StringUtils.isBlank(requestBody)) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".requestBody");
		}
		Map<String, String> map = gson.fromJson(requestBody, new TypeToken<Map<String, String>> () {}.getType());
		if (StringUtils.isBlank(map.get("areaId"))) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".areaId");
		}
		if (StringUtils.isBlank(map.get("type"))) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".type");
		}
		SchoolClass sc = new SchoolClass();
		sc.setId(Long.parseLong(map.get("areaId")));
		try {
			List<Long> longs = new ArrayList<Long>();
			String string = redisClientTemplate.get("area_id");
			if (StringUtils.isBlank(string) || null ==string) {
				List<SchoolClass> selectAllGroupByPid = schoolClassService.selectAllGroupByPid();
				for (SchoolClass sc1 : selectAllGroupByPid) {
					Long pid = sc1.getPid();
					longs.add(pid);
				}
			} else {
				longs = gson.fromJson(string, new TypeToken<List<Long>> () {}.getType());
			}
			if (longs.contains(Long.parseLong(map.get("areaId")))) {
				String redisJson = redisClientTemplate.get("sun_report" + map.get("areaId"));
				if (StringUtils.isNotBlank(redisJson)) {
					List<SchoolClass> classList = gson.fromJson(redisJson, new TypeToken<List<SchoolClass>> () {}.getType());
					List<SchoolClass> schoolClasses = new ArrayList<SchoolClass>();
					if (map.get("type").equals("1")) {
						for (SchoolClass schoolClass : classList) {
							schoolClass.setChildList(null);
							schoolClasses.add(schoolClass);
						}
						baseResponse.setResult(schoolClasses);
					} else if (map.get("type").equals("2")) {
						for (SchoolClass schoolClass : classList) {
							Long id = Long.parseLong(map.get("schoolId"));
							if (schoolClass.getId().equals(id)) {
								List<SchoolClass> childList = schoolClass.getChildList();
								baseResponse.setResult(childList);
							}
						}
					}
				}
			} else {
				baseResponse.setCode("520");
				baseResponse.setMessage("该地区暂无学校信息");
			}
		} catch (Exception e) {
			baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(), ".server");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseResponse;
	}
	
	public List<SchoolClass> getSchoolClassList(SchoolClass sc) throws Exception {
			List<SchoolClass> list = schoolClassService.selectPidByid(sc);
			List<SchoolClass> list1 = new ArrayList<SchoolClass>();
			for (SchoolClass schoolClass : list) {
				List<SchoolClass> list3 = getSchoolClassList(schoolClass);
				schoolClass.setChildList(list3);
				list1.add(schoolClass);
			}
		return list1;
	}
	
	@RequestMapping("/getChildClass")
	@ResponseBody
	public BaseResponse getChildClass(HttpServletRequest request,HttpServletResponse response,@RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse();
		logger.info("url=/classController/getChildClass" + "requestBody====" + requestBody);
		SchoolClass schoolClass = new SchoolClass();
		List<SchoolClass> schoolClassList = new ArrayList<SchoolClass>();
		Map<String, String> map = new HashMap<String, String>();
		List<Map<String, String>> classMapList = new ArrayList<Map<String,String>>();
		Map<String, Object> lastMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(requestBody)) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".requestBody");
		}
		Child child = gson.fromJson(requestBody, Child.class);
		if (StringUtils.isBlank(child.getChildId())) {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".childId");
		}
		try {
			List<Child> childList = childService.selectByPrimaryKey(child);
			if (null != childList && 0 < childList.size()) {
				for (Child child3 : childList) {
					schoolClass.setId(Long.parseLong(child3.getClasses()));
					SchoolClass schoolClass2 = schoolClassService.selectMesByClassId(schoolClass);
					SchoolClass schoolClass3 = schoolClassService.selectMesByPid(schoolClass2);
					if (null == schoolClass3) {
						lastMap.put("schoolName", schoolClass2.getName());
						lastMap.put("schoolId", schoolClass2.getId().toString());
						lastMap.put("message", null);
						continue;
					}
					SchoolClass schoolClass4 = schoolClassService.selectMesByPid(schoolClass3);
					//map.put("schoolName", schoolClass4.getName());
					//map.put("schoolId", schoolClass4.getId().toString());
					lastMap.put("schoolName", schoolClass4.getName());
					lastMap.put("schoolId", schoolClass4.getId().toString());
					//map.put("gradeName", schoolClass3.getName().toString());
					//map.put("gradeId", schoolClass3.getId().toString());
					map.put("className", schoolClass3.getName().toString() + schoolClass2.getName().toString());
					map.put("classId", schoolClass2.getId().toString());
					classMapList.add(map);
				}
				lastMap.put("message", classMapList);
			}
			baseResponse.setResult(lastMap);
		} catch (Exception e) {
			baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(), ".server");
			// TODO Auto-generated catch block
			e.printStackTrace();
				
		}
		return baseResponse;
	}
	public static void main(String[] args) {
		Long gradeCode = GradeEum.getGradeCode("五年级");
		System.out.println(gradeCode);
	}
}

	