package com.wechat.dao;

import com.wechat.entity.Report;

import java.util.List;
import java.util.Map;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);

    List<Report> getClassReport(Map<String, Object> queryMap);

    Report selectByChildAndTest(Map<String, Object> queryMap);
    
    Report selectByChildAnd(String userUniqueId);
}