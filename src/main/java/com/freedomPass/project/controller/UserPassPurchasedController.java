package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.service.UserPassPurchasedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userPassPurchased")
public class UserPassPurchasedController extends AbstractController {

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

}
