
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:InitClassUtil.java 
	 * 包名:com.wechat.util 
	 * 创建日期:2017年3月9日下午4:51:59 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：InitClassUtil    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年3月9日 下午4:51:59    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年3月9日 下午4:51:59    
 * 修改备注：       
 * @version </pre>    
 */

public class InitClassUtil {
	private static final Logger logger = LoggerFactory.getLogger(InitClassUtil.class);
	private static  ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    public static void runTask(Callable<Integer> runable){
        try {
           Future<Integer>  result= cachedThreadPool.submit(runable);
           cachedThreadPool.shutdown();
        }catch (Exception ex){
            logger.error("初始化班级任务出错");
        }
    }
}

	