package com.wechat.dao;

import java.util.List;
import java.util.Map;

import com.wechat.dao.base.BaseDao;
import com.wechat.entity.Child;

public interface ChildMapper extends BaseDao<Child> {
	
	List<Child> selectByClassId(Child child);
	
	List<Map<String,Object>> getChildRankingList(Map<String, Object> queryMap);
	
	List<Child> selectByPrimaryKey(Child child);
}
