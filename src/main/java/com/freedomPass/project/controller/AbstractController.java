package com.freedomPass.project.controller;

import com.freedomPass.api.commons.utils.HttpUtils;
import com.freedomPass.api.io.NetworkFileManager;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.Group;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.UserService;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public abstract class AbstractController {

    @Autowired
    UserService userService;

    @Autowired
    NetworkFileManager networkFileManager;

    @Autowired
    MessageSource messageSource;

    public String getRemoteIP() {
        return HttpUtils.getClientIp();
    }

    public UserProfile getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) { // Access is done from anynomous user (no login was made)
            if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                UserProfile userProfile = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return userService.toUser(userProfile.getId());
            }
        }
        return null;
    }

    public ResponseBodyEntity checkValidationResults(BindingResult bindingResult, String[] byPassFields) {
        ResponseBuilder responseBuilder = ResponseBuilder.getInstance();
        UserProfile user = getAuthenticatedUser();
        Locale locale = null;
        if (user != null) {
            locale = new Locale(getAuthenticatedUser().getLanguage().getPrefix().toLowerCase());
        } else {
            locale = new Locale("en");
        }
        Set<String> byPassFieldsSet = new HashSet<>();
        if (byPassFields != null) {
            byPassFieldsSet = new HashSet<>(Arrays.asList(byPassFields));
        }
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                if ((byPassFieldsSet != null && !byPassFieldsSet.contains(error.getField())) || byPassFieldsSet == null) {
                    try {
                        if (error.getDefaultMessage().contains("**")) {
                            responseBuilder.addHttpResponseEntityData(error.getField(), messageSource.getMessage(error.getDefaultMessage().split("\\*\\*")[0], new Object[]{error.getDefaultMessage().split("\\*\\*")[1], error.getDefaultMessage().split("\\*\\*")[2]}, locale));
                        } else if (error.getDefaultMessage().contains("*")) {
                            responseBuilder.addHttpResponseEntityData(error.getField(), messageSource.getMessage(error.getDefaultMessage().split("\\*")[0], new Object[]{error.getDefaultMessage().split("\\*")[1]}, locale));
                        } else {
                            responseBuilder.addHttpResponseEntityData(error.getField(), messageSource.getMessage(error.getDefaultMessage(), null, locale));
                        }
                    } catch (Exception ex) {
                        responseBuilder.addHttpResponseEntityData(error.getField(), error.getDefaultMessage());
                    }
                }
            }
        }
        if (responseBuilder.getHttpResponseEntity().getData().size() != 0) {
            return responseBuilder
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .setHttpResponseEntityResultDescription("Validation Error")
                    .getResponse();
        }
        return null;
    }

    public boolean checkIfOneCollectionEntryIsNotNull(Collection collection) {
        Iterator<Group> iterator = collection.iterator();
        boolean instanceValidDetected = false;
        while (iterator.hasNext()) {
            Object instance = iterator.next();
            if (instance != null) {
                instanceValidDetected = true;
                break;
            }
        }
        return instanceValidDetected;
    }

    public boolean checkIfAllCollectionEntriesNotNull(Collection collection) {
        Iterator<Group> iterator = collection.iterator();
        boolean instanceValidDetected = true;
        while (iterator.hasNext()) {
            Object instance = iterator.next();
            if (instance == null) {
                instanceValidDetected = false;
                break;
            }
        }
        return instanceValidDetected;
    }

    public boolean checkIfAdmin() {
        for (Group group : getAuthenticatedUser().getGroupCollection()) {
            if (group.getId() == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfSupport() {
        for (Group group : getAuthenticatedUser().getGroupCollection()) {
            if (group.getId() == 2) {
                return true;
            }
        }
        return false;
    }

    public String getMessageBasedOnLanguage(String key, Object[] additionalData) {
        UserProfile user = getAuthenticatedUser();
        Locale locale = null;
        if (user != null) {
            locale = new Locale(getAuthenticatedUser().getLanguage().getPrefix().toLowerCase());
        } else {
            locale = new Locale("en");
        }
        return messageSource.getMessage(key, additionalData, locale);
    }

}
