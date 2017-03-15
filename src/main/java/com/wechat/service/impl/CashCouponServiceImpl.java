package com.wechat.service.impl;

import com.wechat.dao.CashCouponMapper;
import com.wechat.entity.CashCoupon;
import com.wechat.service.CashCouponService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhusen on 2017/2/14.
 */
@Service
public class CashCouponServiceImpl implements CashCouponService {
    @Autowired
    private CashCouponMapper cashCouponMapper;

    @Override
    public CashCoupon selectByPrimaryKey(String cashCouponCode) {
        return cashCouponMapper.selectByPrimaryKey(cashCouponCode);
    }

    @Override
    public int updateByPrimaryKey(CashCoupon record) {
        return cashCouponMapper.updateByPrimaryKey(record);
    }

	@Override
	public int deleteByPrimaryKey(String cashCouponCode) {
		
		return cashCouponMapper.deleteByPrimaryKey(cashCouponCode);
	}
}
