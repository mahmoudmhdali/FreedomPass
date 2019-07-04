package com.freedomPass.project.controller;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserProfilePasswordValidator;
import com.freedomPass.project.model.AdminPasses;
import com.freedomPass.project.model.UserCompanyInfo;
import com.freedomPass.project.model.UserOutletInfo;
import com.freedomPass.project.model.UserPassPurchased;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.AdminPassesService;
import com.freedomPass.project.service.NotificationEventsService;
import com.freedomPass.project.service.UserPassPurchasedService;
import com.freedomPass.project.service.UserProfileNotificationEventService;
import java.util.Calendar;
import java.util.Date;
import javax.mail.internet.AddressException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newUser")
public class UserPurchasedController extends AbstractController {

    @Autowired
    UserProfileNotificationEventService userProfileNotificationEventService;

    @Autowired
    NotificationEventsService notificationEventsService;

    @Autowired
    UserPassPurchasedService userPassPurchasedService;

    @Autowired
    AdminPassesService adminPassesService;

    @PostMapping("/add")
    public ResponseEntity addUser(@ModelAttribute @Valid UserProfile userProfile, BindingResult userProfileBindingResults) throws AddressException {

        userProfile.setType(4);
        // Validate User Inputs
        ResponseBodyEntity responseBodyEntity = super.checkValidationResults(userProfileBindingResults, null);

        if (responseBodyEntity != null) {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntity(responseBodyEntity)
                    .returnClientResponse();
        }

        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(userService.addUser(userProfile, null, null))
                .returnClientResponse();
    }

    @PostMapping("/purchasePackage/{packageId}")
    public ResponseEntity addCompanyUser(@PathVariable Long packageId) throws AddressException {
        AdminPasses adminPass = adminPassesService.getAdminPasse(packageId);
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntity(manageAddUserUnderCompany(adminPass, packageId))
                .returnClientResponse();
    }

    private synchronized ResponseBodyEntity manageAddUserUnderCompany(AdminPasses adminPass, Long packageId) {
        try {
            if (adminPass == null) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("Package", "Package no more available")
                        .getResponse();
            }
            UserProfile persistantUser = getAuthenticatedUser();
            if (persistantUser != null) {
                UserPassPurchased userPassPurchased = new UserPassPurchased();
                userPassPurchased.setAdminPasses(adminPass);
                userPassPurchased.setIsPaid(false);
                userPassPurchased.setStatus(0);
                userPassPurchased.setIsGifted(false);
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.YEAR, 1);
                userPassPurchased.setValidTill(c.getTime());
                userPassPurchased.setUserProfileId(persistantUser);
                return userPassPurchasedService.addUserPassPurchased(userPassPurchased, packageId, false);
            }
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserPurchasedController 1 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

}
