package com.freedomPass.project.service;

import com.freedomPass.api.engine.NotificationEngine;
import com.freedomPass.project.dao.UserProfileNotificationEventDao;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.UserProfileNotificationEvent;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userProfileNotificationEventService")
@Transactional
public class UserProfileNotificationEventServiceImpl implements UserProfileNotificationEventService {

    @Autowired
    UserProfileNotificationEventDao userProfileNotificationEventDao;

    @Autowired
    NotificationEngine notificationEngine;

    @Override
    public List<UserProfileNotificationEvent> getAll(UserProfile user) {
        return userProfileNotificationEventDao.getAll(user);
    }

    @Override
    public List<UserProfileNotificationEvent> getAll() {
        return userProfileNotificationEventDao.getAll();
    }

    @Override
    public void deleteByUser(UserProfile user) {
        userProfileNotificationEventDao.deleteByUser(user);
    }

    @Override
    public List<UserProfileNotificationEvent> updateUserNotifications(List<UserProfileNotificationEvent> userProfileNotificationEvents, UserProfile user) {
        List<String> notifications = new ArrayList<>();
        List<UserProfileNotificationEvent> persistanteUserProfileNotificationEvents = getAll(user);
        for (int i = 0; i < persistanteUserProfileNotificationEvents.size(); i++) {
            for (UserProfileNotificationEvent newUserProfileNotificationEvent : userProfileNotificationEvents) {
                if (persistanteUserProfileNotificationEvents.get(i).getId() == newUserProfileNotificationEvent.getId()) {
                    persistanteUserProfileNotificationEvents.get(i).setEnabled(newUserProfileNotificationEvent.getEnabled());
                    if(newUserProfileNotificationEvent.getEnabled()){
                        notifications.add(persistanteUserProfileNotificationEvents.get(i).getNotificationEvents().getName().toLowerCase());
                    }
                    break;
                }
            }
        }
        notificationEngine.updateUserNotification(user.getId(), user.getLanguage().getId(), notifications);
        return userProfileNotificationEvents;
    }

}
