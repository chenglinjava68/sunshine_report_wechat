
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:PaperService.java 
	 * 包名:com.wechat.service 
	 * 创建日期:2017年1月9日下午3:37:03 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.service;

import com.wechat.entity.Paper;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：PaperService    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年1月9日 下午3:37:03    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年1月9日 下午3:37:03    
 * 修改备注：       
 * @version </pre>    
 */

public interface PaperService {

	/**
	 * <pre>selectPaperByGradeCode(通过年级查询试卷)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2017年1月9日 下午3:37:22    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2017年1月9日 下午3:37:22    
		 * 修改备注： 
		 * @param record
		 * @return
		 * @throws Exception</pre>
	 */
	public Paper selectPaperByGradeCode(Paper record) throws Exception;
	
	public Paper selectByPrimary(String id);
}

	