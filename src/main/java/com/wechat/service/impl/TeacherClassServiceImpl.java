
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:TeacherClassServiceImpl.java 
	 * 包名:com.wechat.service.impl 
	 * 创建日期:2017年1月9日下午4:24:53 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wechat.dao.ChildMapper;
import com.wechat.dao.TeacherClassMapper;
import com.wechat.entity.Child;
import com.wechat.entity.TeacherClass;
import com.wechat.service.TeacherClassService;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：TeacherClassServiceImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年1月9日 下午4:24:53    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年1月9日 下午4:24:53    
 * 修改备注：       
 * @version </pre>    
 */
@Service
public class TeacherClassServiceImpl implements TeacherClassService {

	@Autowired
	private TeacherClassMapper teacherClassMapper;
	
	@Autowired
	private ChildMapper childMapper;
	 /* (non-Javadoc)    
	 * @see com.wechat.service.TeacherClassService#selectByTeacherId(com.wechat.entity.TeacherClass)    
	 */
		 
	@Override
	public List<TeacherClass> selectByTeacherId(TeacherClass record)
			throws Exception {
		// TODO Auto-generated method stub
		List<TeacherClass> selectByTeacherId = teacherClassMapper.selectByTeacherId(record);
		return selectByTeacherId;
	}

	
	 /* (non-Javadoc)    
	 * @see com.wechat.service.TeacherClassService#insert(com.wechat.entity.TeacherClass)    
	 */
		 
	@Override
	public void insert(TeacherClass record) throws Exception {
		// TODO Auto-generated method stub
		teacherClassMapper.insert(record);
	}
	
	
	 /* (non-Javadoc)    
	 * @see com.wechat.service.TeacherClassService#selectByClassId(com.wechat.entity.Child)    
	 */
		 
	@Override
	public List<Child> selectByClassId(Child child) throws Exception {
		// TODO Auto-generated method stub
		List<Child> selectByClassId = childMapper.selectByClassId(child);
		return selectByClassId;
	}


	
	 /* (non-Javadoc)    
	 * @see com.wechat.service.TeacherClassService#deleteClass(com.wechat.entity.TeacherClass)    
	 */
		 
	@Override
	public void deleteClass(TeacherClass record) throws Exception {
		// TODO Auto-generated method stub
		teacherClassMapper.deleteClass(record);	
	}


	
	 /* (non-Javadoc)    
	 * @see com.wechat.service.TeacherClassService#selectByTeacherIdAndClassId(com.wechat.entity.TeacherClass)    
	 */
		 
	@Override
	public TeacherClass selectByTeacherIdAndClassId(TeacherClass record)
			throws Exception {
		// TODO Auto-generated method stub
		TeacherClass selectByTeacherIdAndClassId = teacherClassMapper.selectByTeacherIdAndClassId(record);
		return selectByTeacherIdAndClassId;
	}
}

	