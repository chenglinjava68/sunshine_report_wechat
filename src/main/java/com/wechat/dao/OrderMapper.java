package com.wechat.dao;

import com.wechat.entity.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    Order selectBypeperId(Order order);
    
    public int updateByOrderNo(Order order);
    
    public Order getByOrderNo(String orderNo);
}