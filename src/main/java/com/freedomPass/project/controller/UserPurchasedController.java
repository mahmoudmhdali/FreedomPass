package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserProfilePasswordValidator;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.NotificationEventsService;
import com.freedomPass.project.service.UserProfileNotificationEventService;
import javax.mail.internet.AddressException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newUser")
public class UserPurchasedController extends AbstractController {

    @Autowired
    UserProfileNotificationEventService userProfileNotificationEventService;

    @Autowired
    NotificationEventsService notificationEventsService;

    @PostMapping("/add")
    public ResponseEntity addUser(@ModelAttribute @Valid UserProfile userProfile, BindingResult userProfileBindingResults) throws AddressException {

        userProfile.setType(4);
        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(userProfileBindingResults, null);

        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userService.addUser(userProfile, null, null))
                .returnClientResponse();
    }

}
