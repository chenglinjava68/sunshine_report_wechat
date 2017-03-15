package com.wechat.entity;

import java.util.Date;

public class CashCoupon {
    private String cashCouponCode;

    private String name;

    private Integer denomination;

    private Date expiryDateStart;

    private Date expiryDateEnd;

    private Integer status;

    public String getCashCouponCode() {
        return cashCouponCode;
    }

    public void setCashCouponCode(String cashCouponCode) {
        this.cashCouponCode = cashCouponCode == null ? null : cashCouponCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getDenomination() {
        return denomination;
    }

    public void setDenomination(Integer denomination) {
        this.denomination = denomination;
    }

    public Date getExpiryDateStart() {
        return expiryDateStart;
    }

    public void setExpiryDateStart(Date expiryDateStart) {
        this.expiryDateStart = expiryDateStart;
    }

    public Date getExpiryDateEnd() {
        return expiryDateEnd;
    }

    public void setExpiryDateEnd(Date expiryDateEnd) {
        this.expiryDateEnd = expiryDateEnd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}