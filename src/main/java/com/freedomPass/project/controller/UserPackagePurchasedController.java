package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserPassPurchased;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.UserPassPurchasedService;
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
@RequestMapping("/userPassPurchased")
public class UserPackagePurchasedController extends AbstractController {

    @Autowired
    UserPassPurchasedService userPassPurchasedService;

    @GetMapping
    public ResponseEntity getUserPassPurchaseds() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userPassPurchaseds", userPassPurchasedService.getUserPassPurchaseds())
                .returnClientResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserPassPurchased(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userPassPurchased", userPassPurchasedService.getUserPassPurchased(id))
                .returnClientResponse();
    }

    @GetMapping("/{pageNumber}/{maxResult}")
    public ResponseEntity getUserPassPurchasedPagination(@PathVariable Integer pageNumber, @PathVariable Integer maxResult) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userPassPurchaseds", userPassPurchasedService.getUserPassPurchasedPagination(pageNumber, maxResult))
                .returnClientResponse();
    }

    @PostMapping("/add/{packageId}")
    public ResponseEntity addUserPassPurchased(@PathVariable Long packageId, @ModelAttribute @Valid UserPassPurchased userPassPurchased, BindingResult userPassPurchasedBindingResults) throws AddressException {
        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(userPassPurchasedBindingResults, null);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userPassPurchasedService.addUserPassPurchased(userPassPurchased, packageId, false))
                .returnClientResponse();
    }

}
