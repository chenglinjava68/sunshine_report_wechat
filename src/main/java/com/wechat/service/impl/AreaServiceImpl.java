
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:AreaServiceImpl.java 
	 * 包名:com.wechat.service.impl 
	 * 创建日期:2017年3月9日下午5:12:01 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wechat.dao.SunareaMapper;
import com.wechat.entity.Sunarea;
import com.wechat.service.AreaService;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：AreaServiceImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年3月9日 下午5:12:01    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年3月9日 下午5:12:01    
 * 修改备注：       
 * @version </pre>    
 */
@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private SunareaMapper sunareaMapper;	
	@Override
	public List<Sunarea> selectAllArea() throws Exception {
		// TODO Auto-generated method stub
			return sunareaMapper.selectAllArea();
	}

}

	