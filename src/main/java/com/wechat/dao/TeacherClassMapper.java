package com.wechat.dao;

import java.util.List;

import com.wechat.entity.TeacherClass;

public interface TeacherClassMapper {
    void insert(TeacherClass record);

    int insertSelective(TeacherClass record);
    
    List<TeacherClass> selectByTeacherId(TeacherClass record);
    
    void deleteClass(TeacherClass record);
    
    TeacherClass selectByTeacherIdAndClassId(TeacherClass record);
}