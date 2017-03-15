package com.wechat.entity;

import com.wechat.entity.base.SuperVO;

public class Child extends SuperVO {
    private String childId;
    private String parentId;
    private String nickname;
    private String avatar;
    private String areaName;
    private String classes;
    private Long areaId;
    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

	public Long getAreaId() {
	
		return areaId;
	}

	public void setAreaId(Long areaId) {
	
		this.areaId = areaId;
	}

	@Override
	public String toString() {
		return "Child [childId=" + childId + ", parentId=" + parentId
				+ ", nickname=" + nickname + ", avatar=" + avatar
				+ ", areaName=" + areaName + ", classes=" + classes
				+ ", areaId=" + areaId + "]";
	}

	
}
