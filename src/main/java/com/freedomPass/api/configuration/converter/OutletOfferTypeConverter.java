/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.converter;

import com.freedomPass.project.model.Group;
import com.freedomPass.project.model.OutletOfferType;
import com.freedomPass.project.model.UserOutletInfo;
import com.freedomPass.project.service.GroupService;
import com.freedomPass.project.service.OutletOfferTypeService;
import com.freedomPass.project.service.UserOutletInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OutletOfferTypeConverter implements Converter<Object, OutletOfferType> {

    @Autowired
    OutletOfferTypeService outletOfferTypeService;

    /**
     * Gets Roles by Id
     *
     * @param element
     * @return
     * @see
     * org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public OutletOfferType convert(Object element) {
        if (element instanceof String) {
            Long id = Long.parseLong((String) element);
            OutletOfferType outletOfferType = outletOfferTypeService.getOutletOfferType(id);
            return outletOfferType;
        }
        return (OutletOfferType) element;

    }

}
