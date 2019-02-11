/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.dao;

import com.freedomPass.project.model.ReportStyle;
import java.util.List;

public interface ReportStyleDao {

    public List<ReportStyle> getReportStyles();

    ReportStyle getReportStyle(Long id);
    
}
