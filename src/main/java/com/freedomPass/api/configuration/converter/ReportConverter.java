/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.converter;

import com.freedomPass.project.model.Report;
import com.freedomPass.project.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReportConverter implements Converter<Object, Report> {

    @Autowired
    ReportsService reportsService;

    /**
     * Gets Roles by Id
     *
     * @param element
     * @return
     * @see
     * org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public Report convert(Object element) {
        if (element instanceof String) {
            Long id = Long.parseLong((String) element);
            Report report = reportsService.getReport(id);
            return report;
        }
        return (Report) element;
    }
}
