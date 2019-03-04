package com.freedomPass.project.controller;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserProfilePasswordValidator;
import com.freedomPass.project.model.NotificationEvents;
import com.freedomPass.project.model.UserCompanyInfo;
import com.freedomPass.project.model.UserOutletInfo;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.UserProfileNotificationEvent;
import com.freedomPass.project.service.NotificationEventsService;
import com.freedomPass.project.service.UserProfileNotificationEventService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.mail.internet.AddressException;
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

    @GetMapping("/auth/token/{token}")
    public ResponseEntity getUserByToken(@PathVariable String token) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userService.getUserByToken(token))
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
            @ModelAttribute @Valid UserOutletInfo userOutletInfo, BindingResult userOutletInfoBindingResults) throws AddressException {
        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(userProfileBindingResults, null);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        UserProfile loggedInUser = this.getAuthenticatedUser();
        if (loggedInUser != null) {
            if (loggedInUser.getType() != 0 && loggedInUser.getType() != 1 && loggedInUser.getType() != 99) {
                responseBodyEntity = ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.UNAUTHORIZED_USER_ACTION)
                        .setHttpResponseEntityResultDescription("Access denied for this resource. Contact your service provider for more help")
                        .getResponse();
                return ResponseBuilder.getInstance()
                        .setHttpStatus(HttpStatus.OK)
                        .setHttpResponseEntity(userService.addUser(userProfile, userCompanyInfo, userOutletInfo))
                        .returnClientResponse();
            }
            if (loggedInUser.getType() == 1) {
                userProfile.setType(3);
                userProfile.setParentId(loggedInUser.getId());
            }
            if ((loggedInUser.getType() == 0 || loggedInUser.getType() == 99) && userProfile.getType() == null) {
                responseBodyEntity = ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("type", "Type is required")
                        .getResponse();
                return ResponseBuilder.getInstance()
                        .setHttpStatus(HttpStatus.OK)
                        .setHttpResponseEntity(userService.addUser(userProfile, userCompanyInfo, userOutletInfo))
                        .returnClientResponse();
            }
            if (loggedInUser.getType() == 0 && userProfile.getType() != 0 && userProfile.getType() != 1 && userProfile.getType() != 2 && userProfile.getType() != 3) {
                responseBodyEntity = ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("type", "Type not exist")
                        .getResponse();
                return ResponseBuilder.getInstance()
                        .setHttpStatus(HttpStatus.OK)
                        .setHttpResponseEntity(userService.addUser(userProfile, userCompanyInfo, userOutletInfo))
                        .returnClientResponse();
            }
        } else {
            userProfile.setType(4);
        }

        if (null != userProfile.getType()) {
            switch (userProfile.getType()) {
                case 0: {
                    break;
                }
                case 1: {
                    responseBodyEntity = super.checkValidationResults(userCompanyInfoBindingResults, new String[]{"id"});
                    if (responseBodyEntity != null) {
                        return ResponseBuilder.getInstance()
                                .setHttpStatus(HttpStatus.OK)
                                .setHttpResponseEntity(responseBodyEntity)
                                .returnClientResponse();
                    }
                    break;
                }
                case 2: {
                    responseBodyEntity = super.checkValidationResults(userOutletInfoBindingResults, new String[]{"id"});
                    if (responseBodyEntity != null) {
                        return ResponseBuilder.getInstance()
                                .setHttpStatus(HttpStatus.OK)
                                .setHttpResponseEntity(responseBodyEntity)
                                .returnClientResponse();
                    }
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    break;
                }
                case 99: {
                    break;
                }
                default:
                    break;
            }
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userService.addUser(userProfile, userCompanyInfo, userOutletInfo))
                .returnClientResponse();
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@ModelAttribute @Valid UserProfile userProfile, BindingResult groupBindingResults,
            @ModelAttribute @Valid UserCompanyInfo userCompanyInfo, BindingResult userCompanyInfoBindingResults,
            @ModelAttribute @Valid UserOutletInfo userOutletInfo, BindingResult userOutletInfoBindingResults) {
        ResponseBodyEntity responseBodyEntity = this.checkValidationResults(groupBindingResults, new String[]{"password", "confirmPassword"});
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        if (null != userProfile.getType()) {
            switch (userProfile.getType()) {
                case 0: {
                    break;
                }
                case 1: {
                    responseBodyEntity = super.checkValidationResults(userCompanyInfoBindingResults, new String[]{"id"});
                    if (responseBodyEntity != null) {
                        return ResponseBuilder.getInstance()
                                .setHttpStatus(HttpStatus.OK)
                                .setHttpResponseEntity(responseBodyEntity)
                                .returnClientResponse();
                    }
                    break;
                }
                case 2: {
                    responseBodyEntity = super.checkValidationResults(userOutletInfoBindingResults, new String[]{"id"});
                    if (responseBodyEntity != null) {
                        return ResponseBuilder.getInstance()
                                .setHttpStatus(HttpStatus.OK)
                                .setHttpResponseEntity(responseBodyEntity)
                                .returnClientResponse();
                    }
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    break;
                }
                case 99: {
                    break;
                }
                default:
                    break;
            }
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .setHttpResponseEntity(userService.updateUser(userProfile, userCompanyInfo, userOutletInfo))
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
