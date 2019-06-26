/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.service;

import com.freedomPass.project.dao.ReportStyleDao;
import com.freedomPass.project.model.ReportStyle;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reportStyleService")
@Transactional
public class ReportStyleServiceImpl implements ReportStyleService {

    @Autowired
    ReportStyleDao reportStyleDao;

    @Override
    public List<ReportStyle> getReportStyles() {
        return reportStyleDao.getReportStyles();
    }

    @Override
    public ReportStyle getReportStyle(Long id) {
        return reportStyleDao.getReportStyle(id);
    }

}
