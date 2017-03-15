package com.wechat.service.impl;

import com.wechat.dao.ReportOpinionMapper;
import com.wechat.entity.ReportOpinion;
import com.wechat.service.ReportOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhusen on 2017/3/2.
 */
@Service
public class ReportOpinionServiceImpl implements ReportOpinionService {

    @Autowired
    private ReportOpinionMapper reportOpinionMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return reportOpinionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ReportOpinion record) {
        return reportOpinionMapper.insert(record);
    }

    @Override
    public int insertSelective(ReportOpinion record) {
        return reportOpinionMapper.insertSelective(record);
    }

    @Override
    public ReportOpinion selectByPrimaryKey(Integer id) {
        return reportOpinionMapper.selectByPrimaryKey(id);
    }

    @Override
    public ReportOpinion selectByReportId(Integer reportId) {
        return reportOpinionMapper.selectByReportId(reportId);
    }

    @Override
    public int updateByPrimaryKeySelective(ReportOpinion record) {
        return reportOpinionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(ReportOpinion record) {
        return reportOpinionMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(ReportOpinion record) {
        return reportOpinionMapper.updateByPrimaryKey(record);
    }
}
