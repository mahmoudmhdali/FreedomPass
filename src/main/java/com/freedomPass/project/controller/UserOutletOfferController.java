package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.service.UserOutletOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userOutletOffer")
public class UserOutletOfferController extends AbstractController {

    @Autowired
    UserOutletOfferService userOutletOfferService;

    @GetMapping
    public ResponseEntity getUserOutletOffers() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userOutletOffers", userOutletOfferService.getUserOutletOffers())
                .returnClientResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserOutletOffer(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userOutletOffer", userOutletOfferService.getUserOutletOffer(id))
                .returnClientResponse();
    }

}
