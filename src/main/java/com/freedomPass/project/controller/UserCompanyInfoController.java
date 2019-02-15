package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.service.UserCompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userCompanyInfo")
public class UserCompanyInfoController extends AbstractController {

    @Autowired
    UserCompanyInfoService userCompanyInfoService;

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

}
