package com.wechat.dao;

import com.wechat.entity.CashCoupon;
import org.springframework.stereotype.Repository;

@Repository
public interface CashCouponMapper {
    int deleteByPrimaryKey(String cashCouponCode);

    int insert(CashCoupon record);

    int insertSelective(CashCoupon record);

    CashCoupon selectByPrimaryKey(String cashCouponCode);

    int updateByPrimaryKeySelective(CashCoupon record);

    int updateByPrimaryKey(CashCoupon record);
}