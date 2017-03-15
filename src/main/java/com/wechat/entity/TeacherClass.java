package com.wechat.entity;

public class TeacherClass {
    private String teacherId;

    private String classId;

	public String getTeacherId() {
	
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
	
		this.teacherId = teacherId;
	}

	public String getClassId() {
	
		return classId;
	}

	public void setClassId(String classId) {
	
		this.classId = classId;
	}

	@Override
	public String toString() {
		return "TeacherClass [teacherId=" + teacherId + ", classId=" + classId
				+ "]";
	}
    
    
}