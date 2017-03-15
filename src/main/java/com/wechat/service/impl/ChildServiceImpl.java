package com.wechat.service.impl;

import com.wechat.dao.ChildMapper;
import com.wechat.dao.base.BaseDao;
import com.wechat.entity.Child;
import com.wechat.service.ChildService;
import com.wechat.service.base.impl.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhusen on 2017/1/9.
 */
@Service
public class ChildServiceImpl extends BaseServiceImpl<Child> implements ChildService {

    @Autowired
    private ChildMapper childMapper;

    @Override
    protected BaseDao<Child> getBaseDao() {
        return childMapper;
    }

    @Override
    public List<Map<String, Object>> getChildRankingList(Map<String, Object> queryMap) {
        return childMapper.getChildRankingList(queryMap);
    }

	
	 /* (non-Javadoc)    
	 * @see com.wechat.service.ChildService#selectByPrimaryKey(com.wechat.entity.Child)    
	 */
		 
	@Override
	public List<Child> selectByPrimaryKey(Child child) throws Exception {
		// TODO Auto-generated method stub
		List<Child> selectByPrimaryKey = childMapper.selectByPrimaryKey(child);
		return selectByPrimaryKey;
	}
}
