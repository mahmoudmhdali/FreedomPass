package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.UserProfileNotificationEvent;
import java.util.List;

public interface UserProfileNotificationEventDao {

    List<UserProfileNotificationEvent> getAll(UserProfile user);

    void deleteByUser(UserProfile user);

    List<UserProfileNotificationEvent> getAll();

}
