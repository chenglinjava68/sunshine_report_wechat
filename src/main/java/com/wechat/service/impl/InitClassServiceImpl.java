
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:InitClassImpl.java 
	 * 包名:com.wechat.service 
	 * 创建日期:2017年3月9日下午4:44:30 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.service.impl;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.wechat.entity.SchoolClass;
import com.wechat.service.InitClassService;
import com.wechat.service.SchoolClassService;
import com.wechat.sys.MyTask;
import com.wechat.util.redis.RedisClientTemplate;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：InitClassImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年3月9日 下午4:44:30    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年3月9日 下午4:44:30    
 * 修改备注：       
 * @version </pre>    
 */
@Service("initClassService")
public class InitClassServiceImpl implements InitClassService {
	private static final Logger logger = LoggerFactory.getLogger(InitClassServiceImpl.class);
	@Autowired
	private SchoolClassService schoolClassService;
	
	private Gson gson = new Gson();
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Override
	public void initClass() throws Exception {
		// TODO Auto-generated method stub
		 List<SchoolClass> areas = schoolClassService.selectAllGroupByPid();
		 /*List<Long> longs = new ArrayList<Long>();
		 for (SchoolClass schoolClass : areas) {
			 Long pid = schoolClass.getPid();
			 longs.add(pid);
		}
		 redisClientTemplate.setnx("area_id", gson.toJson(longs));*/
		 
		 if (null != areas && 0 < areas.size()) {
			int i =areas.size()/3;
			if(areas.size()%3>0){
				i++;
			}
			for(int j=0;j<i;j++){
				 List<SchoolClass> sc=areas.subList(j*3, (j+1)*3);
				 MyTask task=new MyTask();
				 task.setArList(sc);
				 task.setRedisClientTemplate(redisClientTemplate);
				 task.setSchoolClassService(schoolClassService);
				 new Thread(task).start();
			}
		}
	}
	
	

	
}

	
