

/**
 * <pre>项目名称:sunshine_report_wechat
 * 文件名称:TeacherController.java
 * 包名:com.wechat.controller
 * 创建日期:2017年1月9日下午4:29:00
 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre>
 */

package com.wechat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.entity.ReportOpinion;
import com.wechat.service.ReportOpinionService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.wechat.entity.Child;
import com.wechat.entity.SchoolClass;
import com.wechat.entity.TeacherClass;
import com.wechat.response.BaseResponse;
import com.wechat.response.ResponseCode;
import com.wechat.service.ChildService;
import com.wechat.service.SchoolClassService;
import com.wechat.service.TeacherClassService;
import com.wechat.util.GradeEum;

/**
 * <pre>项目名称：sunshine_report_wechat
 * 类名称：TeacherController
 * 类描述：
 * 创建人：王亮 wanglmir@163.com
 * 创建时间：2017年1月9日 下午4:29:00
 * 修改人：王亮 wanglmir@163.com
 * 修改时间：2017年1月9日 下午4:29:00
 * 修改备注：
 * @version </pre>
 */
@Controller
@RequestMapping("/teacherController")
public class TeacherController {

    private final Logger logger = LoggerFactory.getLogger(TeacherController.class);
    private Gson gson = new Gson();

    @Autowired
    private SchoolClassService schoolClassService;

    @Autowired
    private TeacherClassService teacherClassService;

    @Autowired
    private ReportOpinionService reportOpinionService;

    @RequestMapping("/getTeacherClass")
    @ResponseBody
    public BaseResponse getTeacherClass(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("url=/teacherController/getTeacherClass" + "requestBody====" + requestBody);

        List<SchoolClass> schoolClassList = new ArrayList<SchoolClass>();
        Map<String, Object> lastMap = new HashMap<String, Object>();
        List<Map<String, String>> classMapList = new ArrayList<Map<String, String>>();
        if (StringUtils.isBlank(requestBody)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".requestBody");
        }
        TeacherClass teacherClass = gson.fromJson(requestBody, TeacherClass.class);
        if (StringUtils.isBlank(teacherClass.getTeacherId())) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".teacherId");
        }
        try {
            List<TeacherClass> teacherList = teacherClassService.selectByTeacherId(teacherClass);
            if (null != teacherList && 0 < teacherList.size()) {
                for (TeacherClass teacherClass2 : teacherList) {
                    Map<String, String> map = new HashMap<String, String>();
                    SchoolClass schoolClass = new SchoolClass();
                    schoolClass.setId(Long.parseLong(teacherClass2.getClassId()));
                    SchoolClass schoolClass2 = schoolClassService.selectMesByClassId(schoolClass);
                    SchoolClass schoolClass3 = schoolClassService.selectMesByPid(schoolClass2);
                    if (null == schoolClass3) {
                        lastMap.put("schoolName", schoolClass2.getName());
                        lastMap.put("schoolId", schoolClass2.getId().toString());
                        lastMap.put("message", null);
                        continue;
                    }
                    SchoolClass schoolClass5 = schoolClassService.selectMesByPid(schoolClass3);
                    /*SchoolClass schoolClass4 = new SchoolClass();
                    schoolClass4.setId(schoolClass2.getId());
					schoolClass4.setLevel(schoolClass2.getLevel());
					schoolClass4.setName(schoolClass3.getName() + schoolClass2.getName());
					schoolClass4.setPid(schoolClass2.getPid());
					schoolClassList.add(schoolClass4);*/
                    //map.put("schoolName", schoolClass5.getName());
                    //map.put("schoolId", schoolClass5.getId().toString());
                    lastMap.put("schoolName", schoolClass5.getName());
                    lastMap.put("schoolId", schoolClass5.getId().toString());
                    map.put("gradeName", schoolClass3.getName().toString());
                    Long gradeCode = GradeEum.getGradeCode((schoolClass3.getName().toString()));
                    map.put("gradeId", gradeCode+"");
                    
                    map.put("className", schoolClass3.getName().toString() + schoolClass2.getName().toString());
                    map.put("classId", schoolClass2.getId().toString());
                    Child child = new Child();
                    child.setClasses(schoolClass2.getId().toString());
                    List<Child> childList = teacherClassService.selectByClassId(child);
                    map.put("count", childList.size() + "");
                    map.put("childList", gson.toJson(childList));
                    classMapList.add(map);
                }
                lastMap.put("message", classMapList);
            }
            baseResponse.setResult(lastMap);
        } catch (Exception e) {
            baseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "resource");
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return baseResponse;
    }

    @RequestMapping("/insertTeacherClass")
    @ResponseBody
    public BaseResponse insertTeacherClass(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("url=/teacherController/insertTeacherClass" + "requestBody====" + requestBody);
        Child child = new Child();
        Map<String, Object> lastMap = new HashMap<String, Object>();
        List<Map<String, String>> classMapList = new ArrayList<Map<String, String>>();
        if (StringUtils.isBlank(requestBody)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".requestBody");
        }
        TeacherClass teacherClass = gson.fromJson(requestBody, TeacherClass.class);
        if (StringUtils.isBlank(teacherClass.getTeacherId())) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherId");
        }
        if (StringUtils.isBlank(teacherClass.getClassId())) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classId");
        }
        try {
            TeacherClass model = teacherClassService.selectByTeacherIdAndClassId(teacherClass);
            if (null != model) {
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), ".RESOURCEDUPLICATE");
            }
            teacherClassService.insert(teacherClass);
            List<TeacherClass> teacherList = teacherClassService.selectByTeacherId(teacherClass);
            if (null != teacherList && 0 < teacherList.size()) {
                for (TeacherClass teacherClass2 : teacherList) {
                    Map<String, String> map = new HashMap<String, String>();
                    SchoolClass schoolClass = new SchoolClass();
                    schoolClass.setId(Long.parseLong(teacherClass2.getClassId()));
                    SchoolClass schoolClass2 = schoolClassService.selectMesByClassId(schoolClass);
                    SchoolClass schoolClass3 = schoolClassService.selectMesByPid(schoolClass2);
                    if (null == schoolClass3) {
                        lastMap.put("schoolName", schoolClass2.getName());
                        lastMap.put("schoolId", schoolClass2.getId().toString());
                        lastMap.put("message", null);
                        continue;
                    }
                    SchoolClass schoolClass5 = schoolClassService.selectMesByPid(schoolClass3);
					/*SchoolClass schoolClass4 = new SchoolClass();
					schoolClass4.setId(schoolClass2.getId());
					schoolClass4.setLevel(schoolClass2.getLevel());
					schoolClass4.setName(schoolClass3.getName() + schoolClass2.getName());
					schoolClass4.setPid(schoolClass2.getPid());
					schoolClassList.add(schoolClass4);*/
                    //map.put("schoolName", schoolClass5.getName());
                    //map.put("schoolId", schoolClass5.getId().toString());
                    lastMap.put("schoolName", schoolClass5.getName());
                    lastMap.put("schoolId", schoolClass5.getId().toString());
                    //map.put("gradeName", schoolClass3.getName().toString());
                    //map.put("gradeId", schoolClass3.getId().toString());
                    map.put("className", schoolClass3.getName().toString() + schoolClass2.getName().toString());
                    map.put("classId", schoolClass2.getId().toString());
                    Child child2 = new Child();
                    child2.setClasses(schoolClass2.getId().toString());
                    List<Child> childList = teacherClassService.selectByClassId(child2);
                    map.put("count", childList.size() + "");
                    map.put("childList", gson.toJson(childList));
                    classMapList.add(map);
                }
            }
            baseResponse.setResult(classMapList);
        } catch (Exception e) {
            baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(), ".server");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseResponse;
    }

    @RequestMapping("/deleteClass")
    @ResponseBody
    public BaseResponse deleteClass(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("url=/teacherController/deleteClass" + "requestBody====" + requestBody);
        if (StringUtils.isBlank(requestBody)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".requestBody");
        }
        TeacherClass teacherClass = gson.fromJson(requestBody, TeacherClass.class);
        if (StringUtils.isBlank(teacherClass.getClassId())) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".classId");
        }
        if (StringUtils.isBlank(teacherClass.getTeacherId())) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".teacherId");
        }
        try {
            teacherClassService.deleteClass(teacherClass);
        } catch (Exception e) {
            baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(), ".server");
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return baseResponse;
    }

    /**
     * 教师评价学生诊断报告
     *
     * @param requestBody
     * @return
     */
    @RequestMapping("/evaluateReport")
    @ResponseBody
    public BaseResponse evaluateReport(@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("url=/teacherController/evaluateReport" + "requestBody====" + requestBody);
        if (StringUtils.isBlank(requestBody)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".requestBody");
        }
        ReportOpinion reportOpinion = gson.fromJson(requestBody, ReportOpinion.class);
        if (reportOpinion.getReportId() == null) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".reportId");
        }
        if (StringUtils.isBlank(reportOpinion.getTeacherId())) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".teacherId");
        }
        if (StringUtils.isBlank(reportOpinion.getOpinion())) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), ".opinion");
        }
        try {
            this.reportOpinionService.insert(reportOpinion);
        } catch (Exception e) {
            baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(), ".server");
            e.printStackTrace();
        }
        return baseResponse;
    }
}

	