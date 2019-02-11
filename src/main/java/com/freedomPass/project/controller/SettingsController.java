package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.SettingsService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Settings")
@JsonIgnoreProperties(ignoreUnknown = true)

public class SettingsController extends AbstractController {

    @Autowired
    SettingsService SettingService;

    @GetMapping("/getAllSettings/")
    public ResponseEntity<?> getSettings() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("settings", SettingService.selectAllSettings())
                .returnClientResponse();
    }

    @PostMapping("/addSetting")
    public ResponseEntity<?> addSettings(@RequestBody Object Setting) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String stn = gson.toJson(Setting);
        UserProfile user = getAuthenticatedUser();
        Locale locale = new Locale(user.getLanguage().getPrefix().toLowerCase());
        if (checkIfAdmin() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.addSetting(stn, 0, "0", locale.toString()))
                    .returnClientResponse();
        } else if (checkIfSupport() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.addSetting(stn, 1, "0", locale.toString()))
                    .returnClientResponse();
        } else {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.addSetting(stn, 0, "0", locale.toString()))
                    .returnClientResponse();
        }

    }

    @PostMapping("/editSetting")
    public ResponseEntity<?> editSettings(@RequestBody Object Setting) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String stn = gson.toJson(Setting);

        UserProfile user = getAuthenticatedUser();
        Locale locale = new Locale(user.getLanguage().getPrefix().toLowerCase());
        if (checkIfAdmin() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.editSetting(stn, 0, "0", locale.toString()))
                    .returnClientResponse();
        } else if (checkIfSupport() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.editSetting(stn, 1, "0", locale.toString()))
                    .returnClientResponse();
        } else {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.editSetting(stn, 0, "0", locale.toString()))
                    .returnClientResponse();
        }

    }

    @PostMapping("/addSubSetting")
    public ResponseEntity<?> addSubSettings(@RequestBody Object Setting) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String stn = gson.toJson(Setting);
        UserProfile user = getAuthenticatedUser();
        Locale locale = new Locale(user.getLanguage().getPrefix().toLowerCase());
        if (checkIfAdmin() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.addSubSetting(stn, 0, locale.toString()))
                    .returnClientResponse();
        } else if (checkIfSupport() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.addSubSetting(stn, 1, locale.toString()))
                    .returnClientResponse();
        } else {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.addSubSetting(stn, 0, locale.toString()))
                    .returnClientResponse();
        }

    }

    @PostMapping("/editSubSetting")
    public ResponseEntity<?> editSubSettings(@RequestBody Object Setting) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String stn = gson.toJson(Setting);
        UserProfile user = getAuthenticatedUser();
        Locale locale = new Locale(user.getLanguage().getPrefix().toLowerCase());
        if (checkIfAdmin() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.editSubSetting(stn, 0, locale.toString()))
                    .returnClientResponse();
        } else if (checkIfSupport() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.editSubSetting(stn, 1, locale.toString()))
                    .returnClientResponse();
        } else {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.editSubSetting(stn, 0, locale.toString()))
                    .returnClientResponse();
        }

    }

    @PostMapping("/deleteSubSetting")
    public ResponseEntity<?> deleteSubSettings(@RequestBody Object Setting) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String set = gson.toJson(Setting);
        UserProfile user = getAuthenticatedUser();
        Locale locale = new Locale(user.getLanguage().getPrefix().toLowerCase());
        if (checkIfAdmin() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.deleteSubSetting(set, 0, locale.toString()))
                    .returnClientResponse();
        } else if (checkIfSupport() == true) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.deleteSubSetting(set, 1, locale.toString()))
                    .returnClientResponse();
        } else {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(SettingService.deleteSubSetting(set, 0, locale.toString()))
                    .returnClientResponse();
        }

    }

    @PostMapping("/getSettings/{ResponseFormat}/{RequestLevel}/{MainParameter}/{Parameters}/{Conditions}/{Values}")
    public ResponseEntity<?> getSettings(@PathVariable Long ResponseFormat, @PathVariable Long RequestLevel, @PathVariable String MainParameter, @PathVariable String Parameters, @PathVariable String Conditions, @PathVariable String Values) {

        return SettingService.getSettings(ResponseFormat, RequestLevel, MainParameter, Parameters, Conditions, Values, 1);

    }

    @RequestMapping("/getSettings/specific")
    public ResponseEntity<?> getSettings(@RequestParam("keys") String[] keys) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("settings", SettingService.selectSettings(keys))
                .returnClientResponse();
    }

}
