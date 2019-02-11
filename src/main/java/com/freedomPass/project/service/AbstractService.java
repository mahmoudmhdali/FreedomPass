package com.freedomPass.project.service;

import com.freedomPass.api.engine.NotificationEngine;
import com.freedomPass.api.io.NetworkFileManager;
import com.freedomPass.project.helpermodel.NotificationEngineClass;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.WebNotifications;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.mail.internet.AddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractService {

    @Autowired
    UserService userService;

    @Autowired
    Environment environment;

    @Autowired
    NetworkFileManager networkFileManager;

    @Autowired
    EmailService emailService;

    @Autowired
    NotificationEngine notificationEngine;

    @Autowired
    MessageSource messageSource;

    public void sendEmail(String text, String[] to, String subject) throws AddressException {
        //Log.append("Email Message Data [Sender ==> " + "Outbound Dialer <" + environment.getRequiredProperty("mail.username") + ">" + ", Receivers ==>" + receiversEmails + ", Body ==>" + text + ", Subject ==>" + subject + "]", "", "", Log.LOG_TYPE_NORMAL);
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("Freedom Pass <" + environment.getProperty("mail.username") + ">");
        emailMessage.setBcc(to);//setTo(to) is not used here in order not to show all recepient mails;
        emailMessage.setSubject(subject);
        emailMessage.setText(text);
        emailService.sendEmail(emailMessage);
    }

    public UserProfile getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) { // Access is done from anynomous user (no login was made)
            if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                UserProfile userProfile = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return userService.toUser(userProfile.getId());
            }
        }
        return null;
    }

    public void addNotification(String notificationKey, HashMap<String, String> valuesToReplace, String href) {
        NotificationEngineClass notification = notificationEngine.getNotificationsMapping().get(notificationKey.toLowerCase());
        if (notification != null) {
            HashMap<Long, Long> userLanguageMap = notification.getUserLanguageMap();
            for (Map.Entry<Long, Long> userLanguageEntry : userLanguageMap.entrySet()) {
                String notificationText = notification.getLanguageTextMap().get(userLanguageEntry.getValue());
                if (valuesToReplace != null) {
                    for (Map.Entry<String, String> entry : valuesToReplace.entrySet()) {
                        notificationText = notificationText.replace(entry.getKey(), entry.getValue());
                    }
                }
                UserProfile loggedInUser = getAuthenticatedUser();
                notificationText = notificationText.replace("$USERNAME$", loggedInUser.getName());
                if (notification.getWebNotification()) {
                    UserProfile user = userService.toUser(userLanguageEntry.getKey());
                    if (!Objects.equals(loggedInUser.getId(), user.getId())) {
                        this.addWebNotification(user, notificationText, href);
                    }
                }
                if (notification.getSmsNotification()) {
                    this.addSMSNotification(userService.toUser(userLanguageEntry.getKey()), notificationText);
                }
                if (notification.getEmailNotification()) {
                    this.addEmailNotification(userService.toUser(userLanguageEntry.getKey()), notificationText);
                }
            }
        }
    }

    private void addWebNotification(UserProfile user, String notificationText, String href) {
        WebNotifications webNotification = new WebNotifications();
        webNotification.setUser(user);
        webNotification.setText(notificationText);
        webNotification.setHref(href);
        userService.addNotification(webNotification);
    }

    private void addSMSNotification(UserProfile user, String notificationText) {

    }

    private void addEmailNotification(UserProfile user, String notificationText) {

    }

    public String getMessageBasedOnLanguage(String key, Object[] additionalData) {
        UserProfile user = getAuthenticatedUser();
        Locale locale = null;
        if (user != null) {
            locale = new Locale(getAuthenticatedUser().getLanguage().getPrefix().toLowerCase());
        } else {
            locale = new Locale("en");
        }
        return messageSource.getMessage(key, additionalData, locale);
    }
}
