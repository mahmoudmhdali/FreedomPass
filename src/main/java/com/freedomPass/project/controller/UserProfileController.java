package com.freedomPass.project.controller;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserProfilePasswordValidator;
import com.freedomPass.project.model.NotificationEvents;
import com.freedomPass.project.model.UserCompanyInfo;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.UserProfileNotificationEvent;
import com.freedomPass.project.service.NotificationEventsService;
import com.freedomPass.project.service.UserProfileNotificationEventService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserProfileController extends AbstractController {

    @Autowired
    UserProfileNotificationEventService userProfileNotificationEventService;

    @Autowired
    NotificationEventsService notificationEventsService;

    @GetMapping
    public ResponseEntity getUsers() {
        UserProfile user = super.getAuthenticatedUser();
        Long excludeLoggedInUserID = -999999L; // in case you need to include logged in user to the list its set to his ID
        if (super.getAuthenticatedUser() != null) {
            excludeLoggedInUserID = super.getAuthenticatedUser().getId();
        }
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("users", userService.getUsers(excludeLoggedInUserID, user.getType(), user.getParentId()))
                .returnClientResponse();
    }

    @GetMapping("/{pageNumber}/{maxResult}")
    public ResponseEntity getUsersPagination(@PathVariable Integer pageNumber, @PathVariable Integer maxResult) {
        UserProfile user = super.getAuthenticatedUser();
        Long excludeLoggedInUserID = -999999L; // in case you need to include logged in user to the list its set to his ID
        if (super.getAuthenticatedUser() != null) {
            excludeLoggedInUserID = super.getAuthenticatedUser().getId();
        }
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("users", userService.getUsersPagination(excludeLoggedInUserID, user.getType(), user.getId(), pageNumber, maxResult))
                .returnClientResponse();
    }

    @GetMapping("/getUsersNotifications")
    public ResponseEntity getUsersNotifications() {
        List<NotificationEvents> notificationEvents = notificationEventsService.getAll();
        HashMap<Long, List<Long>> notifications = new HashMap<>();
        for (NotificationEvents notificationEvent : notificationEvents) {
            List<Long> IDs = new ArrayList<>();
            for (UserProfileNotificationEvent userProfileNotificationEvent : notificationEvent.getUserProfileNotificationEventCollection()) {
                IDs.add(userProfileNotificationEvent.getUserProfile().getId());
            }
            notifications.put(notificationEvent.getId(), IDs);
        }
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("notifications", notifications)
                .returnClientResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userService.getUser(id))
                .returnClientResponse();
    }

    @RequestMapping("/view")
    public ResponseEntity getUserByEmail(@RequestParam("email") String email) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userService.getUser(email))
                .returnClientResponse();
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@ModelAttribute @Valid UserProfile userProfile, BindingResult userProfileBindingResults,
            @ModelAttribute @Valid UserCompanyInfo userCompanyInfo, BindingResult userCompanyInfoBindingResults,
            @ModelAttribute @Valid UserProfilePasswordValidator userProfilePasswordValidator, BindingResult userProfilePasswordValidatorBindingResults) {

        userProfile.setPassword(userProfilePasswordValidator.getNewPassword());
        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(userProfileBindingResults, null);

        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        // Validate Passwords Structure
        responseBodyEntity = super.checkValidationResults(userProfilePasswordValidatorBindingResults, null);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        // Valdidate Passwords matching
        if (!userProfilePasswordValidator.matchPasswords()) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("confirmPassword", this.getMessageBasedOnLanguage("user.controller.passwordMismatch", null))
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userService.addUser(userProfile))
                .returnClientResponse();
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@ModelAttribute @Valid UserProfile userProfile, BindingResult groupBindingResults) {
        ResponseBodyEntity responseBodyEntity = this.checkValidationResults(groupBindingResults, new String[]{"password", "confirmPassword"});
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        // Valdiate Groups if they at least one group is provided correctly
        if (userProfile.getGroupCollection() == null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("group", this.getMessageBasedOnLanguage("user.controller.groupShoulBeSelected", null))
                    .returnClientResponse();
        }
        boolean isValid = super.checkIfOneCollectionEntryIsNotNull(userProfile.getGroupCollection());

        if (!isValid) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("group", this.getMessageBasedOnLanguage("user.controller.groupShoulBeSelected", null))
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .setHttpResponseEntity(userService.updateUser(userProfile))
                .returnClientResponse();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userService.deleteUser(id))
                .returnClientResponse();
    }

}
