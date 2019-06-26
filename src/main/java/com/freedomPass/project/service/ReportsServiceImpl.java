/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.service;

import com.freedomPass.project.dao.ReportFiltersDao;
import com.freedomPass.project.dao.ReportsDao;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.Group;
import com.freedomPass.project.model.Report;
import com.freedomPass.project.model.ReportFilter;
import com.freedomPass.project.model.UserProfile;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reportsService")
@Transactional
public class ReportsServiceImpl extends AbstractService implements ReportsService {

    @Autowired
    ReportsDao reportsDao;

    @Autowired
    ReportFiltersDao reportFiltersDao;

    @Override
    public List<Report> getReportsBasedOnRoles() {
        UserProfile user = this.getAuthenticatedUser();
        if (user.getGroupCollection() != null) {
            for (Group group : user.getGroupCollection()) {
                if (group.getId() == 1 || group.getId() == 3) {
                    return reportsDao.getReports();
                }
            }
        }
        return reportsDao.getReportsBasedOnRoles(user.getId());
    }

    @Override
    public List<Report> getReports() {
        return reportsDao.getReports();
    }

    @Override
    public Report getReport(Long id) {
        return reportsDao.getReport(id);
    }

    @Override
    public String callStoredProcedure(Long procID, Integer pageNumber, Integer offset, Integer withCounter, Map<String, String[]> filters, Integer withLimit) throws SQLException, Exception {
        return reportsDao.callStoredProcedure(procID, pageNumber, offset, withCounter, filters, withLimit);
    }

    @Override
    public String exportToExcel(Long procID, Map<String, String[]> filters) throws ParseException, SQLException {
        return reportsDao.exportToExcel(procID, filters);
    }

    @Override
    public ResponseBodyEntity addReport(Report report) {

        if (report.getReportFilters() != null) {
            for (ReportFilter reportFilter : report.getReportFilters()) {
                reportFilter.setReport(report);
            }
        }

        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("report", reportsDao.addReport(report))
                .getResponse();
    }

    @Override
    public ResponseBodyEntity editReport(Report report) {
        Report persistantReport = this.getReport(report.getId());

        List<ReportFilter> persistantFilters = persistantReport.getReportFilters();
        List<Long> ids = new ArrayList<>();
        if (persistantFilters != null) {
            for (ReportFilter reportFilter : persistantFilters) {
                ids.add(reportFilter.getId());
            }
            reportFiltersDao.deleteSelection(ids);

        }

        if (report.getReportFilters() != null) {
            for (ReportFilter reportFilter : report.getReportFilters()) {
                reportFilter.setReport(report);
            }
            persistantReport.setReportFilters(report.getReportFilters());
        }

        persistantReport.setChartSubTitle(report.getChartSubTitle());
        persistantReport.setChartTitle(report.getChartTitle());
        persistantReport.setName(report.getName());
        persistantReport.setProcName(report.getProcName());
        persistantReport.setReportStyleCollection(report.getReportStyleCollection());

        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("report", persistantReport)
                .getResponse();
    }

    @Override
    public ResponseBodyEntity deleteReport(Long id) {
        reportsDao.deleteReport(getReport(id));
        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("report", "success")
                .getResponse();
    }

}
