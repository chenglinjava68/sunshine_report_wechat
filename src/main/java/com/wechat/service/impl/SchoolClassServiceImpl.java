
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:SchoolClassServiceImpl.java 
	 * 包名:com.wechat.service.impl 
	 * 创建日期:2017年1月9日下午4:14:13 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.service.impl;

import java.util.List;

import com.wechat.entity.vo.SchoolClassVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wechat.dao.SchoolClassMapper;
import com.wechat.entity.SchoolClass;
import com.wechat.service.SchoolClassService;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：SchoolClassServiceImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年1月9日 下午4:14:13    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年1月9日 下午4:14:13    
 * 修改备注：       
 * @version </pre>    
 */
@Service
public class SchoolClassServiceImpl implements SchoolClassService {

	@Autowired
	private SchoolClassMapper schoolClassMapper;

	
	 /* (non-Javadoc)    
	 * @see com.wechat.service.SchoolClassService#selectMesByClassId(com.wechat.entity.SchoolClass)    
	 */
		 
	@Override
	public SchoolClass selectMesByClassId(SchoolClass record) throws Exception {
		// TODO Auto-generated method stub
		SchoolClass selectMesByClassId = schoolClassMapper.selectMesByClassId(record);
		return selectMesByClassId;
	}


	
	 /* (non-Javadoc)    
	 * @see com.wechat.service.SchoolClassService#selectMesByPid(com.wechat.entity.SchoolClass)    
	 */
		 
	@Override
	public SchoolClass selectMesByPid(SchoolClass record) throws Exception {
		// TODO Auto-generated method stub
		SchoolClass selectMesByPid = schoolClassMapper.selectMesByPid(record);
		return selectMesByPid;
			
	}

		 @Override
		 public SchoolClassVO selectSchoolClassVO(Long classId) {
			 return schoolClassMapper.selectSchoolClassVO(classId);
		 }



		
	 /* (non-Javadoc)    
	 * @see com.wechat.service.SchoolClassService#selectPidByid(com.wechat.entity.SchoolClass)    
	 */
		 
	@Override
	public List<SchoolClass> selectPidByid(SchoolClass record)
			throws Exception {
		// TODO Auto-generated method stub
		List<SchoolClass> selectPidByid = schoolClassMapper.selectPidByid(record);
		return selectPidByid;
	}



	
		 /* (non-Javadoc)    
		 * @see com.wechat.service.SchoolClassService#selectAllGroupByPid()    
		 */
		 
	@Override
	public List<SchoolClass> selectAllGroupByPid() throws Exception {
		// TODO Auto-generated method stub
			return schoolClassMapper.selectAllGroupByPid();
			
	}
 }

	