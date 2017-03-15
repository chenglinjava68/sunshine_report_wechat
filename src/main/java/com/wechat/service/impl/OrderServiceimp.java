package com.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wechat.dao.OrderMapper;
import com.wechat.entity.Order;
import com.wechat.service.OrderService;

@Service
public class OrderServiceimp implements OrderService{

	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public boolean saveOrder(Order record) {
		
		return orderMapper.insertSelective(record)>0?true:false;
	}

	@Override
	public Order selectByUserInfo(Order order) throws Exception {
		Order selectByUserInfo = orderMapper.selectBypeperId(order);
		return selectByUserInfo;
	}


	@Override
	public boolean updateByOrderNo(Order order) {
		return orderMapper.updateByOrderNo(order)>0?true:false;
	}

	@Override
	public Order getByOrderNo(String orderNo) {
		
		return orderMapper.getByOrderNo(orderNo);
	}

}
