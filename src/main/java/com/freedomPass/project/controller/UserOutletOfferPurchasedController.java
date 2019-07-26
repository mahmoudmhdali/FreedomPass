package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserOutletOffer;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.UserOutletOfferPurchasedService;
import com.freedomPass.project.service.UserOutletOfferService;
import com.freedomPass.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userOutletOfferPurchased")
public class UserOutletOfferPurchasedController extends AbstractController {

    @Autowired
    UserOutletOfferPurchasedService userOutletOfferPurchasedService;

    @Autowired
    UserOutletOfferService userOutletOfferService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity getUserOutletOfferPurchaseds() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userOutletOfferPurchaseds", userOutletOfferPurchasedService.getUserOutletOfferPurchaseds())
                .returnClientResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserOutletOfferPurchased(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userOutletOfferPurchased", userOutletOfferPurchasedService.getUserOutletOfferPurchased(id))
                .returnClientResponse();
    }

    @GetMapping("/purchase/{offerID}/{userID}")
    public ResponseEntity myPackages(@PathVariable Long offerID, @PathVariable Long userID) {
        UserProfile loggedInUser = getAuthenticatedUser();
        UserProfile user = userService.toUser(userID);
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userCompanyPasses", userOutletOfferPurchasedService.addUserOutletOfferPurchased(offerID, user, loggedInUser))
                .returnClientResponse();
    }

}
