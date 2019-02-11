/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.converter;

import com.freedomPass.project.model.ReportStyle;
import com.freedomPass.project.service.ReportStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReportStyleConverter implements Converter<Object, ReportStyle> {

    @Autowired
    ReportStyleService reportStyleService;

    /**
     * Gets Roles by Id
     *
     * @param element
     * @return
     * @see
     * org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public ReportStyle convert(Object element) {
        if (element instanceof String) {
            Long id = Long.parseLong((String) element);
            ReportStyle reportStyle = reportStyleService.getReportStyle(id);
            return reportStyle;
        }
        return (ReportStyle) element;
    }
}
