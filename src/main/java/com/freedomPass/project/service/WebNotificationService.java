package com.freedomPass.project.service;

import com.freedomPass.project.model.WebNotifications;
import java.util.List;

public interface WebNotificationService {

    List<WebNotifications> getAll(long userID, boolean all);

    void updateNotSeen(long userID);

    void add(WebNotifications webNotification);
}
