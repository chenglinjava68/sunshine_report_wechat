package com.wechat.dao;

import com.wechat.entity.Paper;

public interface PaperMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Paper record);

    int insertSelective(Paper record);

    Paper selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Paper record);

    int updateByPrimaryKeyWithBLOBs(Paper record);

    int updateByPrimaryKey(Paper record);
    
    Paper selectPaperByGradeCode(Paper record);
    
    public Paper selectByPrimary(String id);
}