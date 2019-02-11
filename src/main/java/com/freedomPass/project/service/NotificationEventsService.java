package com.freedomPass.project.service;

import com.freedomPass.project.model.NotificationEvents;
import com.freedomPass.project.model.UserProfile;
import java.util.List;

public interface NotificationEventsService {

    NotificationEvents get(String eventName);

    List<NotificationEvents> getByUser(UserProfile user);
    
    NotificationEvents get(Long id);

    List<NotificationEvents> getAll();

    List<NotificationEvents> getEnabled();

    List<NotificationEvents> getAllWithTexts();

    List<NotificationEvents> getEnabledWithTextsAndUsers();
}
