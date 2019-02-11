package com.freedomPass.project.dao;

import com.freedomPass.project.model.WebNotifications;
import java.util.List;

public interface WebNotificationsDao {

    List<WebNotifications> getAll(long userID, boolean all);

    void updateNotSeen(long userID);

    void add(WebNotifications webNotification);
    
    Integer getCountAll(long userID);

}
