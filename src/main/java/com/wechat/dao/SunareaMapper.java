package com.wechat.dao;

import java.util.List;

import com.wechat.entity.Sunarea;

public interface SunareaMapper {
    int deleteByPrimaryKey(Integer areaId);

    int insert(Sunarea record);

    int insertSelective(Sunarea record);

    Sunarea selectByPrimaryKey(Integer areaId);

    int updateByPrimaryKeySelective(Sunarea record);

    int updateByPrimaryKey(Sunarea record);
    
    List<Sunarea> selectAllArea();
}