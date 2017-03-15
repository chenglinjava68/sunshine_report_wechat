
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:AreaService.java 
	 * 包名:com.wechat.service 
	 * 创建日期:2017年3月9日下午5:11:41 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.service;

import java.util.List;

import com.wechat.entity.Sunarea;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：AreaService    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年3月9日 下午5:11:41    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年3月9日 下午5:11:41    
 * 修改备注：       
 * @version </pre>    
 */

public interface AreaService {
	public List<Sunarea> selectAllArea() throws Exception;
}

	