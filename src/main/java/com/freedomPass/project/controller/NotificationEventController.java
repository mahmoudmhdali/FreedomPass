package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.service.NotificationEventsService;
import com.freedomPass.project.service.UserProfileNotificationEventService;
import com.freedomPass.project.service.WebNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificationEvent")
public class NotificationEventController extends AbstractController {

    @Autowired
    WebNotificationService webNotificationService;

    @Autowired
    UserProfileNotificationEventService userProfileNotificationEventService;

    @Autowired
    NotificationEventsService uotificationEventsService;

    @GetMapping
    public ResponseEntity getAllNotifications() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("notifications", uotificationEventsService.getAll())
                .returnClientResponse();
    }

}
