package com.freedomPass.api.engine;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.controller.AbstractController;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.service.EmailService;
import javax.mail.internet.AddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiEngine")
public class APIEngineController extends AbstractController {

    @Autowired
    NotificationEngine notificationEngine;

    @Autowired
    SettingsEngine settingsEngine;

    @Autowired
    UsersEngine usersEngine;

    @Autowired
    EmailService emailService;

    @Autowired
    Environment environment;

    public void sendEmail(String text, String[] to, String subject) throws AddressException {
        //Log.append("Email Message Data [Sender ==> " + "Outbound Dialer <" + environment.getRequiredProperty("mail.username") + ">" + ", Receivers ==>" + receiversEmails + ", Body ==>" + text + ", Subject ==>" + subject + "]", "", "", Log.LOG_TYPE_NORMAL);
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("Freedom Pass <" + environment.getProperty("mail.username") + ">");
        emailMessage.setBcc(to);//setTo(to) is not used here in order not to show all recepient mails;
        emailMessage.setSubject(subject);
        emailMessage.setText(text);
        emailService.sendEmail(emailMessage);
    }

    @GetMapping("/sendEmail")
    public ResponseEntity<?> sendEmail() throws AddressException {
        String[] email = {"mahmoudmhdali@gmail.com"};
        sendEmail("Email Sent", email, "Account Created");
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .returnClientResponse();
    }

    @GetMapping("/setLogLevel/{logLevel}")
    public ResponseEntity<?> DebugMode(@PathVariable int logLevel) {
        if (logLevel >= 1 && logLevel <= 4) {
            Logger.LOG_LEVEL = logLevel;
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .returnClientResponse();
        } else {
            return ResponseBuilder.getInstance()
                    .setHttpStatus(HttpStatus.OK)
                    .setHttpResponseEntityResultCode(ResponseCode.INVALID_INPUT)
                    .returnClientResponse();
        }
    }

    @GetMapping("/enableHourlyLogs/{enableFlag}")
    public ResponseEntity<?> hourlyLogs(@PathVariable boolean enableFlag) {
        Logger.ENABLE_HOURLY_LOGS = enableFlag;
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .returnClientResponse();
    }

    @GetMapping("/refreshNotifications")
    public ResponseEntity refreshNotifications() {
        notificationEngine.loadNotifications();
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("success", "Notifications refreshed successfully")
                .returnClientResponse();
    }

    @GetMapping("/testNotifications")
    public ResponseEntity testNotifications() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("success", notificationEngine.getNotificationsMapping())
                .returnClientResponse();
    }

    @GetMapping("/refreshSettings")
    public ResponseEntity refreshSettings() {
        settingsEngine.loadSettings();
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("success", "Settings refreshed successfully")
                .returnClientResponse();
    }

    @GetMapping("/testSettings")
    public ResponseEntity testSettings() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("success", settingsEngine.getSettingssMapping())
                .returnClientResponse();
    }

    @GetMapping("/refreshLockedAccounts")
    public ResponseEntity refreshLockedAccounts() {
        usersEngine.loadLockedUsers();
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("success", "Locked Accounts refreshed successfully")
                .returnClientResponse();
    }

    @GetMapping("/testLockedAccounts")
    public ResponseEntity testLockedAccounts() {
        return ResponseBuilder.getInstance()
                .setHttpStatus(HttpStatus.OK)
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("success", usersEngine.getLockedAccounts())
                .returnClientResponse();
    }

}
