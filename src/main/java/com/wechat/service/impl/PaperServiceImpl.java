
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:PaperServiceImpl.java 
	 * 包名:com.wechat.service.impl 
	 * 创建日期:2017年1月9日下午3:38:26 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wechat.dao.PaperMapper;
import com.wechat.entity.Paper;
import com.wechat.service.PaperService;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：PaperServiceImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年1月9日 下午3:38:26    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年1月9日 下午3:38:26    
 * 修改备注：       
 * @version </pre>    
 */
@Service
public class PaperServiceImpl implements PaperService {

	@Autowired
	private PaperMapper paperMapper;
		
	 /* (non-Javadoc)    
	 * @see com.wechat.service.PaperService#selectPaperByGradeCode(com.wechat.entity.Paper)    
	 */
		 
	@Override
	public Paper selectPaperByGradeCode(Paper record) throws Exception {
		// TODO Auto-generated method stub
		Paper paper = paperMapper.selectPaperByGradeCode(record);
		return paper;
			
	}

	@Override
	public Paper selectByPrimary(String id) {
		
		return paperMapper.selectByPrimary(id);
	}

}

	