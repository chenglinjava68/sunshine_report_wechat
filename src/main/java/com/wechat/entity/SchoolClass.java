package com.wechat.entity;

import java.util.List;

public class SchoolClass {
    private Long id;

    private String name;

    private Long pid;

    private Integer level;
    
    private List<SchoolClass> childList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

	public List<SchoolClass> getChildList() {
	
		return childList;
	}

	public void setChildList(List<SchoolClass> childList) {
	
		this.childList = childList;
	}

	@Override
	public String toString() {
		return "SchoolClass [id=" + id + ", name=" + name + ", pid=" + pid
				+ ", level=" + level + ", childList=" + childList + "]";
	}


    
    
    
}