package com.wechat.entity.vo;

import com.wechat.entity.base.SuperVO;

/**
 * Created by zhusen on 2017/1/10.
 */
public class SchoolClassVO extends SuperVO {

    private Long classId;

    private String className;

    private Long gradeId;

    private String gradeName;

    private Long schoolId;

    private String schoolName;
    
    private Long areaId;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

	public Long getAreaId() {
	
		return areaId;
	}

	public void setAreaId(Long areaId) {
	
		this.areaId = areaId;
	}

	@Override
	public String toString() {
		return "SchoolClassVO [classId=" + classId + ", className=" + className
				+ ", gradeId=" + gradeId + ", gradeName=" + gradeName
				+ ", schoolId=" + schoolId + ", schoolName=" + schoolName
				+ ", areaId=" + areaId + "]";
	}
    
}
