
	 
	 /** 
	 * <pre>项目名称:sunshine_report_wechat 
	 * 文件名称:MyTask.java 
	 * 包名:com.wechat.sys 
	 * 创建日期:2017年3月9日下午6:51:28 
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package com.wechat.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.wechat.entity.SchoolClass;
import com.wechat.entity.Sunarea;
import com.wechat.service.AreaService;
import com.wechat.service.SchoolClassService;
import com.wechat.util.redis.RedisClientTemplate;
	
	 /** 
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：MyTask    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年3月9日 下午6:51:28    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年3月9日 下午6:51:28    
 * 修改备注：       
 * @version </pre>    
 */

public class MyTask implements Runnable {

	private SchoolClassService schoolClassService;
	
	private RedisClientTemplate redisClientTemplate;
	
	private Gson gson = new Gson();
	
    List<SchoolClass> arList;
	
	@Override
	public void run() {
			try {
				// TODO Auto-generated method stub
				for (SchoolClass scClass : arList) {
					String keys = "sun_report" + scClass.getPid();
					if (redisClientTemplate.exists(keys)) {
						 redisClientTemplate.del(keys);
					};
					SchoolClass sc = new SchoolClass();
					sc.setId((long)scClass.getPid());
					List<SchoolClass> list = schoolClassService.selectPidByid(sc);
					for (SchoolClass schoolClass : list) {
						List<SchoolClass> list2 = schoolClassService.selectPidByid(schoolClass);
						if (null != list2 && list2.size() > 0) {
							schoolClass.setChildList(list2);
							for (SchoolClass schoolClass2 : list2) {
								List<SchoolClass> list3 = schoolClassService.selectPidByid(schoolClass2);
								if (null != list3 && list3.size()>0) {
									schoolClass2.setChildList(list3);
								}
							}
						}
					}
					redisClientTemplate.setnx("sun_report" + scClass.getPid(), gson.toJson(list));
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		
	}
	public SchoolClassService getSchoolClassService() {
	
		return schoolClassService;
	}
	public void setSchoolClassService(SchoolClassService schoolClassService) {
	
		this.schoolClassService = schoolClassService;
	}
	public RedisClientTemplate getRedisClientTemplate() {
	
		return redisClientTemplate;
	}
	public void setRedisClientTemplate(RedisClientTemplate redisClientTemplate) {
	
		this.redisClientTemplate = redisClientTemplate;
	}
	public List<SchoolClass> getArList() {
	
		return arList;
	}
	public void setArList(List<SchoolClass> arList) {
	
		this.arList = arList;
	}	

}

	