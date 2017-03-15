package com.wechat.entity;

import java.util.Date;

public class Order {
    private Integer id;

    private String paperId;

    private String outTradeNo;

    private String userId;

    private String state;

    private String description;

    private String totalAmout;

    private String serialNumber;

    private String paperName;
    
    private String sunuserid;

    public String getSunuserid() {
		return sunuserid;
	}

	public void setSunuserid(String sunuserid) {
		this.sunuserid = sunuserid;
	}

	private Date createTime;

    private Date updateTime;

    private String channel;

    private String sunAmout;

    private String cashCouponCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId == null ? null : paperId.trim();
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(String totalAmout) {
        this.totalAmout = totalAmout == null ? null : totalAmout.trim();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName == null ? null : paperName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getSunAmout() {
        return sunAmout;
    }

    public void setSunAmout(String sunAmout) {
        this.sunAmout = sunAmout == null ? null : sunAmout.trim();
    }

    public String getCashCouponCode() {
        return cashCouponCode;
    }

    public void setCashCouponCode(String cashCouponCode) {
        this.cashCouponCode = cashCouponCode;
    }
}