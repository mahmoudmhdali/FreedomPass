package com.freedomPass.api.engine;

import com.freedomPass.project.helpermodel.NotificationEngineClass;
import com.freedomPass.project.model.NotificationEvents;
import com.freedomPass.project.model.NotificationTexts;
import com.freedomPass.project.model.UserProfileNotificationEvent;
import com.freedomPass.project.service.NotificationEventsService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationEngine {

    public boolean initialized = false;

    private final Map<String, NotificationEngineClass> notificationsMapping = new HashMap<>();

    private List<NotificationEvents> notificationsMappingList = new ArrayList<>();

    @Autowired
    NotificationEventsService notificationEventsService;

    @Scheduled(fixedDelay = 86400000, initialDelay = 30000)
    public synchronized void init() {
        if (!initialized) {
        notificationsMapping.clear();
            this.loadNotifications();
        }
    }

    public synchronized void loadNotifications() {
        notificationsMappingList = notificationEventsService.getEnabledWithTextsAndUsers();
        for (NotificationEvents notificationEvent : notificationsMappingList) {
            NotificationEngineClass notificationClass = new NotificationEngineClass();
            notificationClass.setWebNotification(notificationEvent.getHasWeb());
            notificationClass.setEmailNotification(notificationEvent.getHasEmail());
            notificationClass.setSmsNotification(notificationEvent.getHasSMS());
            HashMap<Long, String> languageTextMap = new HashMap<>();
            for (NotificationTexts notificationText : notificationEvent.getTexts()) {
                languageTextMap.put(notificationText.getLanguage().getId(), notificationText.getText());
            }
            notificationClass.setLanguageTextMap(languageTextMap);
            HashMap<Long, Long> userLanguageMap = new HashMap<>();
            for (UserProfileNotificationEvent userProfileNotificationEvent : notificationEvent.getUserProfileNotificationEventCollection()) {
                if (userProfileNotificationEvent.getUserProfile().isEnabled() && userProfileNotificationEvent.getUserProfile().getDeletedDate() == null && userProfileNotificationEvent.getEnabled()) {
                    userLanguageMap.put(userProfileNotificationEvent.getUserProfile().getId(), userProfileNotificationEvent.getUserProfile().getLanguage().getId());
                }
            }
            notificationClass.setUserLanguageMap(userLanguageMap);
            notificationsMapping.put(notificationEvent.getName().toLowerCase(), notificationClass);
        }
        initialized = true;
    }

    public void addnewUser(Long userId, Long languageId, List<String> notificationNames) {
        if (!initialized) {
            this.loadNotifications();
        }
        for (String notificationName : notificationNames) {
            NotificationEngineClass notification = notificationsMapping.get(notificationName.toLowerCase());
            HashMap<Long, Long> userLanguageMap = notification.getUserLanguageMap();
            userLanguageMap.put(userId, languageId);
            notification.setUserLanguageMap(userLanguageMap);
            notificationsMapping.put(notificationName.toLowerCase(), notification);
        }
    }

    public void updateUserNotification(Long userId, Long languageId, List<String> notificationNames) {
        if (!initialized) {
            this.loadNotifications();
        }
        for (Map.Entry<String, NotificationEngineClass> entry : notificationsMapping.entrySet()) {
            NotificationEngineClass notification = entry.getValue();
            if (notificationNames.contains(entry.getKey())) {
                HashMap<Long, Long> userLanguageMap = notification.getUserLanguageMap();
                if (userLanguageMap.get(userId) == null) {
                    userLanguageMap.put(userId, languageId);
                }
            } else {
                HashMap<Long, Long> userLanguageMap = notification.getUserLanguageMap();
                if (userLanguageMap.get(userId) != null) {
                    userLanguageMap.remove(userId);
                }
            }
        }
    }

    public void deleteUser(Long userId) {
        if (!initialized) {
            this.loadNotifications();
        }
        for (Map.Entry<String, NotificationEngineClass> entry : notificationsMapping.entrySet()) {
            NotificationEngineClass notification = entry.getValue();
            HashMap<Long, Long> userLanguageMap = notification.getUserLanguageMap();
            if (userLanguageMap.get(userId) != null) {
                userLanguageMap.remove(userId);
            }

        }
    }

    public Map<String, NotificationEngineClass> getNotificationsMapping() {
        return notificationsMapping;
    }

}
