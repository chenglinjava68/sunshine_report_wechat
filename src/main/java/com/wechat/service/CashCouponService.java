package com.wechat.service;

import com.wechat.entity.CashCoupon;

/**
 * Created by zhusen on 2017/2/14.
 */
public interface CashCouponService {

    CashCoupon selectByPrimaryKey(String cashCouponCode);

    int updateByPrimaryKey(CashCoupon record);
    
    public int deleteByPrimaryKey(String cashCouponCode);
}
