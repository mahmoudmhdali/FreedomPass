package com.freedomPass.project.helpermodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashMap;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class NotificationEngineClass {

    private Boolean webNotification;
    private Boolean emailNotification;
    private Boolean smsNotification;
    private HashMap<Long, String> languageTextMap;
    private HashMap<Long, Long> userLanguageMap;

    public Boolean getWebNotification() {
        return webNotification;
    }

    public void setWebNotification(Boolean webNotification) {
        this.webNotification = webNotification;
    }

    public Boolean getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public Boolean getSmsNotification() {
        return smsNotification;
    }

    public void setSmsNotification(Boolean smsNotification) {
        this.smsNotification = smsNotification;
    }

    public HashMap<Long, String> getLanguageTextMap() {
        return languageTextMap;
    }

    public void setLanguageTextMap(HashMap<Long, String> languageTextMap) {
        this.languageTextMap = languageTextMap;
    }

    public HashMap<Long, Long> getUserLanguageMap() {
        return userLanguageMap;
    }

    public void setUserLanguageMap(HashMap<Long, Long> userLanguageMap) {
        this.userLanguageMap = userLanguageMap;
    }

}
