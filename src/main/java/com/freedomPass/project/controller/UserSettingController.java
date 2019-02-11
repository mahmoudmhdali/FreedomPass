package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserProfilePasswordValidator;
import com.freedomPass.project.model.UserProfile;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userSettings")
public class UserSettingController extends AbstractController {

    @GetMapping
    public ResponseEntity getUsers() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("user", getAuthenticatedUser())
                .returnClientResponse();
    }

    @PostMapping("/changeLang/{langId}")
    public ResponseEntity changeLang(@PathVariable Long langId) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userService.updateUserLanguage(langId))
                .returnClientResponse();
    }

    @PostMapping("/update")
    public ResponseEntity updateUserSttings(@ModelAttribute @Valid UserProfile userProfile, BindingResult groupBindingResults) {

        ResponseBodyEntity responseBodyEntity = this.checkValidationResults(groupBindingResults, new String[]{"password", "confirmPassword", "email",});
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .setHttpResponseEntity(userService.updateUserSettings(userProfile))
                .returnClientResponse();
    }

    @PostMapping("/updatePassword")
    public ResponseEntity updatePassword(UserProfile userProfile, @ModelAttribute @Valid UserProfilePasswordValidator userProfilePasswordValidator,
            BindingResult userProfilePasswordValidatorBindingResults) {

        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(userProfilePasswordValidatorBindingResults, null);

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
                .setHttpResponseEntity(userService.changeUserPassword(userProfile, userProfilePasswordValidator))
                .returnClientResponse();
    }

}
