/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.converter;

import com.freedomPass.project.model.UserCompanyInfo;
import com.freedomPass.project.service.UserCompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserCompanyInfoConverter implements Converter<Object, UserCompanyInfo> {

    @Autowired
    UserCompanyInfoService userCompanyInfoService;

    @Override
    public UserCompanyInfo convert(Object element) {
        if (element instanceof String) {
            Long id = Long.parseLong((String) element);
            UserCompanyInfo userCompanyInfo = userCompanyInfoService.getUserCompanyInfo(id);
            return userCompanyInfo;
        }
        return (UserCompanyInfo) element;
    }
}
