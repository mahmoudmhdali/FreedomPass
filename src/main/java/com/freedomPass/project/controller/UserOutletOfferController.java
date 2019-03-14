package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserOutletInfo;
import com.freedomPass.project.model.UserOutletOffer;
import com.freedomPass.project.service.UserOutletOfferService;
import java.io.IOException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/type/{id}")
    public ResponseEntity getUserOutletOfferByType(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userOutletOffer", userOutletOfferService.getUserOutletOffersByType(id))
                .returnClientResponse();
    }

    @GetMapping("/outlet/{id}")
    public ResponseEntity getUserOutletOffersByOutletId(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userOutletOffer", userOutletOfferService.getUserOutletOffersByOutletId(id))
                .returnClientResponse();
    }

    @GetMapping("/{pageNumber}/{maxResult}")
    public ResponseEntity getOffersPagination(@PathVariable Integer pageNumber, @PathVariable Integer maxResult) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("offers", userOutletOfferService.getOffersPagination(pageNumber, maxResult))
                .returnClientResponse();
    }

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity addOffer(@RequestPart("info") @Valid UserOutletOffer userOutletOffer, BindingResult userOutletOfferBindingResults,
            @RequestPart("outlet") @Valid UserOutletInfo userOutletInfo, BindingResult userOutletInfoBindingResults,
            @RequestPart(value = "uploadFile1", required = false) MultipartFile file1,
            @RequestPart(value = "uploadFile2", required = false) MultipartFile file2,
            @RequestPart(value = "uploadFile3", required = false) MultipartFile file3,
            @RequestPart(value = "uploadFile4", required = false) MultipartFile file4) throws AddressException, IOException {
        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(userOutletOfferBindingResults, null);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userOutletOfferService.addOffer(userOutletOffer, userOutletInfo, file1, file2, file3, file4))
                .returnClientResponse();
    }

    @PostMapping(value = "/edit", consumes = "multipart/form-data")
    public ResponseEntity editOffer(@RequestPart("info") @Valid UserOutletOffer userOutletOffer, BindingResult userOutletOfferBindingResults,
            @RequestPart("outlet") @Valid UserOutletInfo userOutletInfo, BindingResult userOutletInfoBindingResults,
            @RequestPart(value = "uploadFile1", required = false) MultipartFile file1,
            @RequestPart(value = "uploadFile2", required = false) MultipartFile file2,
            @RequestPart(value = "uploadFile3", required = false) MultipartFile file3,
            @RequestPart(value = "uploadFile4", required = false) MultipartFile file4) throws AddressException, IOException {
        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(userOutletOfferBindingResults, null);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userOutletOfferService.editOffer(userOutletOffer, userOutletInfo, file1, file2, file3, file4))
                .returnClientResponse();
    }

}
