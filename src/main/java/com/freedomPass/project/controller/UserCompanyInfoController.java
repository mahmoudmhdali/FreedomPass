package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.OutletAndCompanyLocations;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserCompanyInfo;
import com.freedomPass.project.model.UserCompanyInfoImages;
import com.freedomPass.project.model.UserOutletInfo;
import com.freedomPass.project.model.UserOutletOffer;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.UserCompanyInfoImagesService;
import com.freedomPass.project.service.UserCompanyInfoLocationsService;
import com.freedomPass.project.service.UserCompanyInfoService;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/userCompanyInfo")
public class UserCompanyInfoController extends AbstractController {

    @Autowired
    UserCompanyInfoService userCompanyInfoService;

    @Autowired
    UserCompanyInfoImagesService userCompanyInfoImagesService;

    @Autowired
    UserCompanyInfoLocationsService userCompanyLocationsService;

    @GetMapping("/userProfile")
    public ResponseEntity getUserCompany() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("users", userService.getCompanyUsers())
                .returnClientResponse();
    }

    @GetMapping
    public ResponseEntity getUserCompanyInfos() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userCompanyInfos", userCompanyInfoService.getUserCompanyInfos())
                .returnClientResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserCompanyInfo(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userCompanyInfo", userCompanyInfoService.getUserCompanyInfo(id))
                .returnClientResponse();
    }

    @PostMapping(value = "/editImages", consumes = "multipart/form-data")
    public ResponseEntity editOffer(@RequestPart("info") @Valid UserCompanyInfoImages userCompanyInfoImages, BindingResult userOutletOfferBindingResults,
            @RequestPart(value = "uploadFile1", required = false) MultipartFile file1,
            @RequestPart(value = "uploadFile2", required = false) MultipartFile file2,
            @RequestPart(value = "uploadFile3", required = false) MultipartFile file3,
            @RequestPart(value = "uploadFile4", required = false) MultipartFile file4) throws AddressException, IOException {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userCompanyInfoImagesService.editImages(userCompanyInfoImages, file1, file2, file3, file4))
                .returnClientResponse();
    }

    @PostMapping("/editLocations")
    public ResponseEntity editLocations(@ModelAttribute @Valid OutletAndCompanyLocations outletAndCompanyLocations, BindingResult outletAndCompanyLocationsBindingResults) throws AddressException {
        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(outletAndCompanyLocationsBindingResults, null);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userCompanyLocationsService.editLocations(outletAndCompanyLocations))
                .returnClientResponse();
    }

}
