
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:InitClassTask.java 
	 * 包名:com.wechat.util 
	 * 创建日期:2017年3月9日下午4:38:37 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.util;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.service.InitClassService;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：InitClassTask    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年3月9日 下午4:38:37    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年3月9日 下午4:38:37    
 * 修改备注：       
 * @version </pre>    
 */

public class InitClassTask implements Callable<Integer> {
	public InitClassTask(InitClassService initClassService) {
		this.initClassService = initClassService;
	}

	private final Logger logger = LoggerFactory.getLogger(InitClassTask.class);
	
	private InitClassService initClassService; 
		
	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		int num = 0;
		try {
			logger.info("开始执行异步任务");
			initClassService.initClass();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			return num;
		}
			
	}

	public InitClassService getInitClassService() {
	
		return initClassService;
	}

	public void setInitClassService(InitClassService initClassService) {
	
		this.initClassService = initClassService;
	}

}

	