/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.converter;

import com.freedomPass.project.model.UserOutletOffer;
import com.freedomPass.project.service.UserOutletOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserOutletOfferConverter implements Converter<Object, UserOutletOffer> {

    @Autowired
    UserOutletOfferService userOutletOfferService;

    /**
     * Gets Roles by Id
     *
     * @param element
     * @return
     * @see
     * org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public UserOutletOffer convert(Object element) {
        if (element instanceof String) {
            Long id = Long.parseLong((String) element);
            UserOutletOffer userOutletOffer = userOutletOfferService.getUserOutletOffer(id);
            return userOutletOffer;
        }
        return (UserOutletOffer) element;

    }

}
