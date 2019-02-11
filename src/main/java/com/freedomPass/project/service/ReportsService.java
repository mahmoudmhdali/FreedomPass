/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.model.Report;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ReportsService {

    List<Report> getReports();

    List<Report> getReportsBasedOnRoles();
    
    Report getReport(Long id);
    
    ResponseBodyEntity addReport(Report report);

    ResponseBodyEntity editReport(Report report);

    String callStoredProcedure(Long procID, Integer pageNumber, Integer offset, Integer withCounter, Map<String, String[]> filters, Integer withLimit) throws SQLException, Exception;
        
    String exportToExcel(Long procID, Map<String, String[]> filters) throws ParseException, SQLException;
    
    ResponseBodyEntity deleteReport(Long id);
}
