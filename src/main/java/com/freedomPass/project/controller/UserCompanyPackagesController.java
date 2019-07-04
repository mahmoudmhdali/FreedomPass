package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserCompanyPasses;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.UserCompanyPassesService;
import javax.mail.internet.AddressException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/userCompanyPasses")
public class UserCompanyPackagesController extends AbstractController {

    @Autowired
    UserCompanyPassesService userCompanyPassesService;

    @GetMapping
    public ResponseEntity getUserCompanyPasses() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userCompanyPasses", userCompanyPassesService.getUserCompanyPasses())
                .returnClientResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserCompanyPasses(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userCompanyPasse", userCompanyPassesService.getUserCompanyPasse(id))
                .returnClientResponse();
    }

    @GetMapping("/{pageNumber}/{maxResult}")
    public ResponseEntity getUserCompanyPassesPagination(@PathVariable Integer pageNumber, @PathVariable Integer maxResult) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userCompanyPasses", userCompanyPassesService.getUserCompanyPassesPagination(pageNumber, maxResult))
                .returnClientResponse();
    }

    @PostMapping("/add")
    public ResponseEntity addUserCompanyPasses(@ModelAttribute @Valid UserCompanyPasses userCompanyPasses, BindingResult userCompanyPassesBindingResults) throws AddressException {
        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(userCompanyPassesBindingResults, null);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userCompanyPassesService.addUserCompanyPasses(userCompanyPasses))
                .returnClientResponse();
    }

    @GetMapping("/myPackages")
    public ResponseEntity myPackages() {
        UserProfile user = getAuthenticatedUser();
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userCompanyPasses", userCompanyPassesService.getUserCompanyPassesByCompanyUserId(user.getUserCompanyInfo().getId()))
                .returnClientResponse();
    }

}
