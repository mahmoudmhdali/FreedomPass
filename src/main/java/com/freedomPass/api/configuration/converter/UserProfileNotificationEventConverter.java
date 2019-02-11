/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.converter;

import com.freedomPass.project.model.UserProfileNotificationEvent;
import com.freedomPass.project.service.NotificationEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserProfileNotificationEventConverter implements Converter<Object, UserProfileNotificationEvent> {

    @Autowired
    NotificationEventsService notificationEventsService;

    /**
     * Gets Roles by Id
     *
     * @param element
     * @return
     * @see
     * org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public UserProfileNotificationEvent convert(Object element) {
        if (element instanceof String) {
            Long id = Long.parseLong((String) element);
            UserProfileNotificationEvent userProfileNotificationEvent = new UserProfileNotificationEvent();
            userProfileNotificationEvent.setNotificationEvents(notificationEventsService.get(id));
            return userProfileNotificationEvent;
        }
        return (UserProfileNotificationEvent) element;
    }
}
