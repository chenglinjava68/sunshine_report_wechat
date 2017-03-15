package com.wechat.service;

import com.wechat.entity.ReportOpinion;

/**
 * Created by zhusen on 2017/3/2.
 */
public interface ReportOpinionService {

    int deleteByPrimaryKey(Integer id);

    int insert(ReportOpinion record);

    int insertSelective(ReportOpinion record);

    ReportOpinion selectByPrimaryKey(Integer id);

    ReportOpinion selectByReportId(Integer reportId);

    int updateByPrimaryKeySelective(ReportOpinion record);

    int updateByPrimaryKeyWithBLOBs(ReportOpinion record);

    int updateByPrimaryKey(ReportOpinion record);
}
