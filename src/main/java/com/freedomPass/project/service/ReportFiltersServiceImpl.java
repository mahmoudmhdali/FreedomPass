/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.service;

import com.freedomPass.project.dao.ReportFiltersDao;
import com.freedomPass.project.helpermodel.DropDownValues;
import com.freedomPass.project.model.ReportFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reportFiltersService")
@Transactional
public class ReportFiltersServiceImpl implements ReportFiltersService {

    @Autowired
    ReportFiltersDao reportFiltersDao;

    @Override
    public List<ReportFilter> getFiltersByReportId(Long reportId) {
        return reportFiltersDao.getFiltersByReportId(reportId);
    }

    @Override
    public List<DropDownValues> getDropDownByReportFilterId(Long reportFilterId) {
        return reportFiltersDao.getDropDownByReportFilterId(reportFilterId);
    }

}
