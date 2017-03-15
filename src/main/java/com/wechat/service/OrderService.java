package com.wechat.service;

import com.wechat.entity.Order;

public interface OrderService {
	
	/**
	 * 保存订单
	 * @param record
	 * @return
	 */
	public boolean  saveOrder(Order record);

	public Order selectByUserInfo(Order order) throws Exception;
	
	/**
	 * 更新订单
	 * @param order
	 * @return
	 */
	public boolean updateByOrderNo(Order order);
	
	public Order getByOrderNo(String orderNo);
}
