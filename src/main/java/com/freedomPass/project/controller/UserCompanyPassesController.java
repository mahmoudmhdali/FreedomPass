package com.freedomPass.project.controller;

import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.service.UserCompanyPassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userCompanyPasses")
public class UserCompanyPassesController extends AbstractController {

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

}
