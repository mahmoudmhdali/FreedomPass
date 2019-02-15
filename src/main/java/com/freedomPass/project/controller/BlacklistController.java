package com.freedomPass.project.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.Blacklist;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.AuditTrailService;
import com.freedomPass.project.service.BlacklistService;
import java.io.IOException;
import java.util.Locale;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/blacklists")
public class BlacklistController extends AbstractController {

    @Autowired
    AuditTrailService audittrailservice;

    @Autowired
    BlacklistService blacklistService;

    @GetMapping
    public ResponseEntity getBlacklists() {

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("blacklists", blacklistService.getBlacklists("0"))
                .returnClientResponse();
    }

    @PostMapping("/add")
    public ResponseEntity addBlacklist(@ModelAttribute @Valid Blacklist blacklist, BindingResult blacklistBindingResults) {

        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(blacklistBindingResults, null);

        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(blacklistService.addBlacklist(blacklist, "0"))
                .returnClientResponse();
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity UploadBulkBlacklists(@RequestParam(value = "file", required = true) MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
        Object[] filePath = blacklistService.store(file);
        if (filePath != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("message", filePath[1])
                    .addHttpResponseEntityData("blacklists", filePath[2])
                    .returnClientResponse();
        } else {
            UserProfile user = getAuthenticatedUser();
            Locale locale = new Locale(user.getLanguage().getPrefix().toLowerCase());
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.UNAUTHORIZED_USER_ACTION)
                    .addHttpResponseEntityData("message", messageSource.getMessage("blacklist.unauthorizedaccess", null, locale))
                    .returnClientResponse();
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBlacklist(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(blacklistService.deleteBlacklist(id))
                .returnClientResponse();
    }

    @PostMapping(value = "/delete", consumes = "multipart/form-data")
    public ResponseEntity RemoveBulkBlacklists(@RequestParam(value = "file", required = true) MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
        Object[] filePath = blacklistService.delete(file);
        if (filePath != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("message", filePath[1])
                    .addHttpResponseEntityData("blacklists", filePath[2])
                    .returnClientResponse();
        } else {
            UserProfile user = getAuthenticatedUser();
            Locale locale = new Locale(user.getLanguage().getPrefix().toLowerCase());
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.UNAUTHORIZED_USER_ACTION)
                    .addHttpResponseEntityData("message", messageSource.getMessage("blacklist.unauthorizedaccess", null, locale))
                    .returnClientResponse();
        }
    }

}
