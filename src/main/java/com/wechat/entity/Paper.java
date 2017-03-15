package com.wechat.entity;

public class Paper {
    private String id;

    private Integer gradecode;

    private Integer version;

    private Integer subjectcode;

    private String content;

    private String price;
    public String getId() {
	
		return id;
	}

	public void setId(String id) {
	
		this.id = id;
	}

	public Integer getGradecode() {
        return gradecode;
    }

    public void setGradecode(Integer gradecode) {
        this.gradecode = gradecode;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getSubjectcode() {
        return subjectcode;
    }

    public void setSubjectcode(Integer subjectcode) {
        this.subjectcode = subjectcode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	@Override
	public String toString() {
		return "Paper [id=" + id + ", gradecode=" + gradecode + ", version="
				+ version + ", subjectcode=" + subjectcode + ", content="
				+ content + ", price=" + price + "]";
	}

	public String getPrice() {
	
		return price;
	}

	public void setPrice(String price) {
	
		this.price = price;
	}
    
}