package com.wechat.service;

import com.wechat.entity.Child;
import com.wechat.service.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by zhusen on 2017/1/9.
 */
public interface ChildService extends BaseService<Child> {
    List<Map<String,Object>> getChildRankingList(Map<String, Object> queryMap);
    
    List<Child> selectByPrimaryKey(Child child) throws Exception;
}
