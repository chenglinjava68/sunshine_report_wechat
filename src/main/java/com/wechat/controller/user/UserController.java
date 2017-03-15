package com.wechat.controller.user;

import com.wechat.controller.ABaseController;
import com.wechat.entity.Child;
import com.wechat.entity.Report;
import com.wechat.entity.User;
import com.wechat.entity.vo.SchoolClassVO;
import com.wechat.response.BaseResponse;
import com.wechat.response.ResponseCode;
import com.wechat.service.ChildService;
import com.wechat.service.RepostService;
import com.wechat.service.SchoolClassService;
import com.wechat.service.UserService;
import com.wechat.util.DESEncode;
import com.wechat.util.GradeEum;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhusen on 2017/1/4.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends ABaseController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ChildService childService;

    @Autowired
    private SchoolClassService schoolClassService;
    
    @Autowired
    private RepostService repostService;

    /**
     * 登录
     *
     * @param mobile
     * @param userType
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BaseResponse login(String mobile, Integer userType) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(mobile)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "mobile");
        }
        if (userType == null) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "userType");
        }
        try {
            mobile = DESEncode.decode(mobile, "E50A24CFFE3DFD26D4EC2C6BE6A4856A");
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("mobile", mobile);
            queryMap.put("userType", userType);
            User user = this.userService.queryOne(queryMap);
            if (user == null) {
                user = new User();
                user.setMobile(mobile);
                user.setUserType(userType);
                user.setStatus(1);
                user.setLoginTime(new Date());
                this.userService.add(user);
                user = this.userService.queryOne(queryMap);
            } else {
                user.setLoginTime(new Date());
                this.userService.updateById(user);
            }
            baseResponse.setResult(user);
        } catch (Exception ex) {
            logger.error("login error:", ex);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, "服务器异常");
        }
        return baseResponse;

    }

    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BaseResponse detail(String userId, String mobile) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(userId) && StringUtils.isBlank(mobile)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "userId||mobile");
        }
        try {
            User user = this.userService.queryById(userId);
            if (StringUtils.isNotBlank(userId)) {
                user = this.userService.queryById(userId);
            } else if (StringUtils.isNotBlank(mobile)) {
                mobile = DESEncode.decode(mobile, "E50A24CFFE3DFD26D4EC2C6BE6A4856A");
                Map<String, Object> map = new HashMap<>();
                map.put("mobile", mobile);
                user = this.userService.queryOne(map);
            }
            if (user == null) {
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.code, "userId");
            }
            baseResponse.setResult(user);
        } catch (Exception ex) {
            logger.error("user detail error:", ex);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, "服务器异常");
        }
        return baseResponse;

    }

    /**
     * 修改用户信息
     *
     * @param userId
     * @param headImgUrl
     * @param userName
     * @param areaName
     * @param school
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BaseResponse update(String userId, String headImgUrl, String userName, String areaName, Integer areaId, String school, Long schoolId) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(userId)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "userId");
        }
        try {
            User user = this.userService.queryById(userId);
            if (user == null) {
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.code, "userId");
            }
            if (StringUtils.isNotBlank(headImgUrl)) {
                user.setHeadImgUrl(headImgUrl);
            }
            if (StringUtils.isNotBlank(userName)) {
                user.setUserName(userName);
            }
            if (StringUtils.isNotBlank(areaName) && areaId != null) {
                user.setAreaName(areaName);
                user.setAreaId(areaId);
            }
            if (StringUtils.isNotBlank(school) && schoolId != null) {
                user.setSchool(school);
                user.setSchoolId(schoolId);
            }
            this.userService.updateById(user);
            user = this.userService.queryById(userId);
            baseResponse.setResult(user);
        } catch (Exception ex) {
            logger.error("user update error:", ex);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, "服务器异常");
        }
        return baseResponse;

    }

    /**
     * 家长新建孩子
     *
     * @param parentId
     * @param nickname
     * @param avatar
     * @param classes
     * @return
     */
    @RequestMapping(value = "/addChild", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BaseResponse addChild(String parentId, String nickname, String avatar, String areaName, String classes,String areaId) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(parentId)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "parentId");
        }
        if (StringUtils.isBlank(nickname)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "nickname");
        }
        if (StringUtils.isBlank(avatar)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "avatar");
        }
        if (StringUtils.isBlank(classes)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "classes");
        }
        if (StringUtils.isBlank(areaName)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "areaName");
        }
        if (StringUtils.isBlank(areaId)) {
        	return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "areaId");
        }
        try {
            Child child = new Child();
            child.setParentId(parentId);
            child.setAvatar(avatar);
            child.setNickname(nickname);
            child.setAreaName(areaName);
            child.setClasses(classes);
            child.setAreaId(Long.parseLong(areaId));
            this.childService.add(child);
            return childDetail(child.getChildId());
        } catch (Exception ex) {
            logger.error("addChild error:", ex);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, "服务器异常");
        }
        return baseResponse;

    }

    /**
     * 孩子详情
     *
     * @param childId
     * @return
     */
    @RequestMapping(value = "/childDetail", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BaseResponse childDetail(String childId) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(childId)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "childId");
        }
        try {
            Child child = this.childService.queryById(childId);
            if (child == null) {
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.code, "childId");
            }
            JSONObject result = JSONObject.fromObject(child);
            SchoolClassVO scvo = this.schoolClassService.selectSchoolClassVO(Long.parseLong(child.getClasses()));
            if (scvo != null) {
            	scvo.setGradeId(GradeEum.getGradeCode(scvo.getGradeName()));
            	scvo.setAreaId(child.getAreaId());
                result.putAll(JSONObject.fromObject(scvo));
                
            }
            baseResponse.setResult(result);
        } catch (Exception ex) {
            logger.error("childDetail error:", ex);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, "服务器异常");
        }
        return baseResponse;

    }
    
    /**
     * 修改孩子信息
     *
     * @param childId
     * @param nickname
     * @param avatar
     * @param classes
     * @return
     */
    @RequestMapping(value = "/updateChild", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BaseResponse updateChild(String childId, String nickname, String avatar, String areaName, String classes,String areaId) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(childId)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "childId");
        }
        try {
            Child child = this.childService.queryById(childId);
            if (child == null) {
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.code, "childId");
            }
            if (StringUtils.isNotBlank(nickname)) {
                child.setNickname(nickname);
            }
            if (StringUtils.isNotBlank(avatar)) {
                child.setAvatar(avatar);
            }
            if (StringUtils.isNotBlank(areaName)) {
                child.setAreaName(areaName);
            }
            if (StringUtils.isNotBlank(classes)) {
                child.setClasses(classes);
            }
            if (StringUtils.isNotBlank(areaId)) {
            	child.setAreaId(Long.parseLong(areaId));
            }
            this.childService.updateById(child);
            return childDetail(childId);
        } catch (Exception ex) {
            logger.error("childDetail error:", ex);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, "服务器异常");
        }
        return baseResponse;

    }

    /**
     * 家长下的孩子列表
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/childList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BaseResponse childList(String parentId) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(parentId)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "parentId");
        }
        try {
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("parentId", parentId);
            List<Child> childList = this.childService.selectList(queryMap);
            baseResponse.setResult(childList);
        } catch (Exception ex) {
            logger.error("childList error:", ex);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, "服务器异常");
        }
        return baseResponse;
    }

    @RequestMapping(value = "/rankingList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BaseResponse rankingList(String classId, String testCode) {
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
            List<Map<String, Object>> childList = this.childService.getChildRankingList(queryMap);
            for (int i = 0; i < childList.size(); i++) {
            	Map<String, Object> maps = childList.get(i);
                queryMap = new HashMap<>();
                queryMap.put("childId", maps.get("child_id"));
                queryMap.put("testCode", testCode);
            	Report report = repostService.selectByChildAndTest(queryMap);
            	if(report==null){
            		maps.put("isReport", false);
            	}else{
            		maps.put("isReport", true);
            	}
			}
            baseResponse.setResult(childList);
        } catch (Exception ex) {
            logger.error("children error:", ex);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, "服务器异常");
        }
        return baseResponse;
    }
}
