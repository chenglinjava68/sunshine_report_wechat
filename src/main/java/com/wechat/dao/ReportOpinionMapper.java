package com.wechat.dao;

import com.wechat.entity.ReportOpinion;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportOpinionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReportOpinion record);

    int insertSelective(ReportOpinion record);

    ReportOpinion selectByPrimaryKey(Integer id);

    ReportOpinion selectByReportId(Integer reportId);

    int updateByPrimaryKeySelective(ReportOpinion record);

    int updateByPrimaryKeyWithBLOBs(ReportOpinion record);

    int updateByPrimaryKey(ReportOpinion record);
}