/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.SettingsMappingService;
import java.sql.SQLException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SettingsMapping")
public class SettingsMappingController extends AbstractController {

    @Autowired
    SettingsMappingService SettingsMappingService;

    @GetMapping("/getSettingsDetails/{SettingID}/{CategoryName}")
    public ResponseEntity<?> getSettingsDetails(@PathVariable Long SettingID, @PathVariable String CategoryName) throws SQLException, Exception {

        int secondLevelFlag = 0;
        if (!SettingID.equals("0")) {
            secondLevelFlag = 1;
        }
        UserProfile user = getAuthenticatedUser();
        Locale locale = new Locale(user.getLanguage().getPrefix().toLowerCase());

        if (checkIfAdmin() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("secondLevelFlag", secondLevelFlag)
                    .addHttpResponseEntityData("SettingsMapping", SettingsMappingService.getSettingsDetails(SettingID, CategoryName, 0, locale.toString()))
                    .returnClientResponse();
        } else if (checkIfSupport() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("secondLevelFlag", secondLevelFlag)
                    .addHttpResponseEntityData("SettingsMapping", SettingsMappingService.getSettingsDetails(SettingID, CategoryName, 1, locale.toString()))
                    .returnClientResponse();
        } else {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("secondLevelFlag", secondLevelFlag)
                    .addHttpResponseEntityData("SettingsMapping", SettingsMappingService.getSettingsDetails(SettingID, CategoryName, 0, locale.toString()))
                    .returnClientResponse();
        }
    }

    @GetMapping("/getCategories")
    public ResponseEntity<?> getCategories() throws SQLException, Exception {

        UserProfile user = getAuthenticatedUser();
        Locale locale = new Locale(user.getLanguage().getPrefix().toLowerCase());
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("Categories", SettingsMappingService.getCategories(locale.toString()))
                .returnClientResponse();
    }

}
