

	 /**
	 * <pre>项目名称:sunshine_report_wechat
	 * 文件名称:SchoolClassService.java
	 * 包名:com.wechat.service
	 * 创建日期:2017年1月9日下午4:13:40
	 * Copyright (c) 2017, wanglmir@163.com All Rights Reserved.</pre>
	 */

	package com.wechat.service;

import java.util.List;

import com.wechat.entity.SchoolClass;
import com.wechat.entity.vo.SchoolClassVO;

	 /**
 * <pre>项目名称：sunshine_report_wechat    
 * 类名称：SchoolClassService    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2017年1月9日 下午4:13:40    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2017年1月9日 下午4:13:40    
 * 修改备注：       
 * @version </pre>    
 */

public interface SchoolClassService {

	public SchoolClass selectMesByClassId(SchoolClass record) throws Exception;

	public SchoolClass selectMesByPid(SchoolClass record) throws Exception;

    SchoolClassVO selectSchoolClassVO(Long classId);
    
    public List<SchoolClass> selectPidByid(SchoolClass record) throws Exception;
    
    public List<SchoolClass> selectAllGroupByPid() throws Exception;
}

	