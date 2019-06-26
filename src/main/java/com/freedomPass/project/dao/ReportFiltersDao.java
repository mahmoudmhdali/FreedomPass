/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.dao;

import com.freedomPass.project.helpermodel.DropDownValues;
import com.freedomPass.project.model.ReportFilter;
import java.util.List;

public interface ReportFiltersDao {

    public List<ReportFilter> getFiltersByReportId(Long reportId);
    
    public ReportFilter getTypeByNameAndReport(String name, Long reportId);
    
    public List<DropDownValues> getDropDownByReportFilterId(Long reportFilterId);
    
    public Integer deleteSelection(List<Long> ids);
}
