package com.wechat.entity;

import java.util.Date;

public class Report {
    private Integer reportId;

    private String userUniqueId;

    private String testCode;

    private Integer totalScore;

    private Integer userScore;

    private Integer useTime;

    private Date createTime;

    private String title;

    private String subjectName;

    private String gradeName;

    private String subjectCode;

    private String gradeCode;
    
    private String testContent;
    
    private String errorFrequency;
    
    private String columnar;

    private Integer isEvaluated;

    private Integer opinionId;
    
    public String getColumnar() {
		return columnar;
	}

	public void setColumnar(String columnar) {
		this.columnar = columnar;
	}

	public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getUserUniqueId() {
        return userUniqueId;
    }

    public void setUserUniqueId(String userUniqueId) {
        this.userUniqueId = userUniqueId == null ? null : userUniqueId.trim();
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode == null ? null : testCode.trim();
    }

    public String getTestContent() {
		return testContent;
	}

	public void setTestContent(String testContent) {
		this.testContent = testContent;
	}

	public String getErrorFrequency() {
		return errorFrequency;
	}

	public void setErrorFrequency(String errorFrequency) {
		this.errorFrequency = errorFrequency;
	}

	public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName == null ? null : gradeName.trim();
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode == null ? null : subjectCode.trim();
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode == null ? null : gradeCode.trim();
    }

    public Integer getIsEvaluated() {
        return isEvaluated;
    }

    public void setIsEvaluated(Integer isEvaluated) {
        this.isEvaluated = isEvaluated;
    }

    public Integer getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(Integer opinionId) {
        this.opinionId = opinionId;
    }
}
