package com.wechat.dao;

import java.util.List;

import com.wechat.entity.SchoolClass;
import com.wechat.entity.vo.SchoolClassVO;

public interface SchoolClassMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SchoolClass record);

    int insertSelective(SchoolClass record);

    SchoolClass selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SchoolClass record);

    int updateByPrimaryKey(SchoolClass record);
    
    SchoolClass selectMesByClassId(SchoolClass record);
    
    SchoolClass selectMesByPid(SchoolClass record);

    SchoolClassVO selectSchoolClassVO(Long classId);
    
    List<SchoolClass> selectPidByid(SchoolClass record);
    
    List<SchoolClass> selectAllGroupByPid();
}