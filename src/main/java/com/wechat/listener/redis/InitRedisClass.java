
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:InitRedisClass.java 
	 * 包名:com.wechat.listener.redis 
	 * 创建日期:2017年3月9日下午4:30:45 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.listener.redis;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wechat.service.InitClassService;
import com.wechat.util.InitClassTask;
import com.wechat.util.InitClassUtil;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：InitRedisClass    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年3月9日 下午4:30:45    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年3月9日 下午4:30:45    
 * 修改备注：       
 * @version </pre>    
 */

public class InitRedisClass implements ServletContextListener,HttpSessionListener,ServletRequestListener {
		private static final Logger logger = LoggerFactory.getLogger(InitRedisClass.class);
		private static ApplicationContext ctx = null;
			 /* (non-Javadoc)    
			 * @see javax.servlet.ServletRequestListener#requestDestroyed(javax.servlet.ServletRequestEvent)    
			 */
			 
		@Override
		public void requestDestroyed(ServletRequestEvent arg0) {
			// TODO Auto-generated method stub
				
		}

		
			 /* (non-Javadoc)    
			 * @see javax.servlet.ServletRequestListener#requestInitialized(javax.servlet.ServletRequestEvent)    
			 */
			 
		@Override
		public void requestInitialized(ServletRequestEvent arg0) {
			// TODO Auto-generated method stub
				
		}

		
			 /* (non-Javadoc)    
			 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)    
			 */
			 
		@Override
		public void sessionCreated(HttpSessionEvent arg0) {
			// TODO Auto-generated method stub
				
		}

		
			 /* (non-Javadoc)    
			 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)    
			 */
			 
		@Override
		public void sessionDestroyed(HttpSessionEvent arg0) {
			// TODO Auto-generated method stub
				
		}

		
			 /* (non-Javadoc)    
			 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)    
			 */
			 
		@Override
		public void contextDestroyed(ServletContextEvent arg0) {
			// TODO Auto-generated method stub
				
		}

		
			 /* (non-Javadoc)    
			 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)    
			 */
			 
		@Override
		public void contextInitialized(ServletContextEvent evt) {
			// TODO Auto-generated method stub
			logger.info("开始初始化配置信息");
			
			//WebApplicationContext ctx = (WebApplicationContext) (evt.getServletContext()).getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			 ctx = WebApplicationContextUtils.getWebApplicationContext(evt.getServletContext());
			 InitClassService initClassService = (InitClassService) ctx.getBean("initClassService");
			 InitClassUtil.runTask(new InitClassTask(initClassService));
		}

}

	