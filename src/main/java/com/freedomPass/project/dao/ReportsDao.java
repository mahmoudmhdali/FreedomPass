/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.dao;

import com.freedomPass.project.model.Report;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ReportsDao {

    public List<Report> getReports();
    
    public List<Report> getReportsBasedOnRoles(Long id);
    
    Report getReport(Long id);

    Report addReport(Report report);

    public String callStoredProcedure(Long procID, Integer pageNumber, Integer offset, Integer withCounter, Map<String, String[]> filters, Integer withLimit) throws SQLException, Exception;
    
    String exportToExcel(Long procID, Map<String, String[]> filters) throws ParseException, SQLException;
            
    void deleteReport(Report report);
}
