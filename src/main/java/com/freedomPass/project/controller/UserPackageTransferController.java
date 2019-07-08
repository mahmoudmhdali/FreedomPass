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
@RequestMapping("/userPackageTransfer")
public class UserPackageTransferController extends AbstractController {

    @Autowired
    UserPassPurchasedService userPassPurchasedService;

    @GetMapping("/getPackagesPurchased/{userID}")
    public ResponseEntity getUserPassPurchasedPagination(@PathVariable Long userID) {
        UserProfile user = getAuthenticatedUser();
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userPassPurchaseds", userPassPurchasedService.getUserPassPurchasedsGifted(userID, true, user.getId()))
                .returnClientResponse();
    }

    @PostMapping("/transferPackage/{fromUserID}/{toUserID}/{packageID}")
    public ResponseEntity transferPackage(@PathVariable Long userID, @PathVariable Long toUserID, @PathVariable Long packageID) {
        UserProfile user = getAuthenticatedUser();
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userPassPurchaseds", userPassPurchasedService.getUserPassPurchasedsGifted(userID, true, user.getId()))
                .returnClientResponse();
    }

}
