package com.freedomPass.project.controller;

import com.freedomPass.api.engine.NotificationEngine;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.UserProfileNotificationEvent;
import com.freedomPass.project.service.NotificationEventsService;
import com.freedomPass.project.service.UserProfileNotificationEventService;
import com.freedomPass.project.service.WebNotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webNotifications")
public class WebNotificationController extends AbstractController {

    @Autowired
    WebNotificationService webNotificationService;

    @Autowired
    UserProfileNotificationEventService userProfileNotificationEventService;

    @Autowired
    NotificationEventsService uotificationEventsService;

    @Autowired
    NotificationEngine notificationEngine;

    @GetMapping("/userNotification")
    public ResponseEntity userNotification() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("notifications", uotificationEventsService.getByUser(getAuthenticatedUser()))
                .returnClientResponse();
    }

    @PostMapping("/updateUserNotifications")
    public ResponseEntity updateWebNotification(@RequestBody List<UserProfileNotificationEvent> userProfileNotificationEvents,
            BindingResult userProfileBindingResults) {
        UserProfile user = getAuthenticatedUser();
        List<UserProfileNotificationEvent> userProfileNotifications = userProfileNotificationEventService.updateUserNotifications(userProfileNotificationEvents, user);
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("notifications", userProfileNotifications)
                .returnClientResponse();
    }

    @GetMapping("/refreshNotifications")
    public ResponseEntity refreshNotifications() {
        notificationEngine.loadNotifications();
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("success", "Notifications refreshed successfully")
                .returnClientResponse();
    }

    @GetMapping("/testNotifications")
    public ResponseEntity testNotifications() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("success", notificationEngine.getNotificationsMapping())
                .returnClientResponse();
    }

    @GetMapping("/getWebNotifications")
    public ResponseEntity getWebNotification(@RequestParam boolean all) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userService.getWebNotifications(getAuthenticatedUser().getId(), all))
                .returnClientResponse();
    }

    @GetMapping("/getCountWebNotifications")
    public ResponseEntity getCountWebNotifications() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userService.getCountWebNotifications(getAuthenticatedUser().getId()))
                .returnClientResponse();
    }

    @PostMapping("/update")
    public ResponseEntity updateWebNotification() {
        userService.updateNotSeen(getAuthenticatedUser().getId());
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .addHttpResponseEntityData("notifications", "success")
                .returnClientResponse();
    }

}
