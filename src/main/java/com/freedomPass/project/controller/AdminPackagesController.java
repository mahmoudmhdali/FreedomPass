package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.AdminPasses;
import com.freedomPass.project.service.AdminPassesService;
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
@RequestMapping("/adminPasses")
public class AdminPackagesController extends AbstractController {

    @Autowired
    AdminPassesService adminPassesService;

    @GetMapping
    public ResponseEntity getAdminPasses() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("adminPasses", adminPassesService.getAdminPasses())
                .returnClientResponse();
    }

    @GetMapping("/{pageNumber}/{maxResult}")
    public ResponseEntity getAdminPassesPagination(@PathVariable Integer pageNumber, @PathVariable Integer maxResult) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("passes", adminPassesService.getAdminPassesPagination(pageNumber, maxResult))
                .returnClientResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity getAdminPasse(@PathVariable Long id) {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("adminPasse", adminPassesService.getAdminPasse(id))
                .returnClientResponse();
    }

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity addPass(@RequestPart("info") @Valid AdminPasses adminPasses, BindingResult adminPassesBindingResults,
            @RequestPart(value = "uploadFile1", required = false) MultipartFile file1) throws AddressException, IOException {
        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(adminPassesBindingResults, null);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(adminPassesService.addPass(adminPasses, file1))
                .returnClientResponse();
    }

    @PostMapping(value = "/edit", consumes = "multipart/form-data")
    public ResponseEntity editPass(@RequestPart("info") @Valid AdminPasses adminPasses, BindingResult adminPassesBindingResults,
            @RequestPart(value = "uploadFile1", required = false) MultipartFile file1) throws AddressException, IOException {
        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(adminPassesBindingResults, null);
        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(adminPassesService.editPass(adminPasses, file1))
                .returnClientResponse();
    }

}
