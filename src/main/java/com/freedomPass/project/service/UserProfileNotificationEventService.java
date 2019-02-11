package com.freedomPass.project.service;

import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.UserProfileNotificationEvent;
import java.util.List;

public interface UserProfileNotificationEventService {

    List<UserProfileNotificationEvent> getAll(UserProfile user);

    void deleteByUser(UserProfile user);

    List<UserProfileNotificationEvent> getAll();

    List<UserProfileNotificationEvent> updateUserNotifications(List<UserProfileNotificationEvent> userProfileNotificationEvents, UserProfile user);
}
