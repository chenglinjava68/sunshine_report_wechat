
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:TeacherClassService.java 
	 * 包名:com.wechat.service 
	 * 创建日期:2017年1月9日下午4:24:34 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.service;

import java.util.List;

import com.wechat.entity.Child;
import com.wechat.entity.TeacherClass;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：TeacherClassService    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年1月9日 下午4:24:34    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年1月9日 下午4:24:34    
 * 修改备注：       
 * @version </pre>    
 */

public interface TeacherClassService {

	public List<TeacherClass> selectByTeacherId(TeacherClass record) throws Exception;
	
	public void insert(TeacherClass record) throws Exception;
	
	public List<Child> selectByClassId(Child child) throws Exception;
	
	public void deleteClass(TeacherClass record) throws Exception;
	
	public TeacherClass selectByTeacherIdAndClassId(TeacherClass record) throws Exception;
}

	