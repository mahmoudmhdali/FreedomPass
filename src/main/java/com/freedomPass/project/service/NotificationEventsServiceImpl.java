package com.freedomPass.project.service;

import com.freedomPass.project.dao.NotificationEventsDao;
import com.freedomPass.project.model.NotificationEvents;
import com.freedomPass.project.model.UserProfile;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("notificationEventsService")
@Transactional
public class NotificationEventsServiceImpl implements NotificationEventsService {

    @Autowired
    private NotificationEventsDao notificationEventsDAO;

    @Override
    public NotificationEvents get(String eventName) {
        return notificationEventsDAO.get(eventName);
    }

    @Override
    public NotificationEvents get(Long id) {
        return notificationEventsDAO.get(id);
    }

    @Override
    public List<NotificationEvents> getByUser(UserProfile user) {
        return notificationEventsDAO.getByUser(user);
    }

    @Override
    public List<NotificationEvents> getAll() {
        return notificationEventsDAO.getAll();
    }

    @Override
    public List<NotificationEvents> getEnabled() {
        return notificationEventsDAO.getEnabled();
    }

    @Override
    public List<NotificationEvents> getAllWithTexts() {
        return notificationEventsDAO.getAllWithTexts();
    }

    @Override
    public List<NotificationEvents> getEnabledWithTextsAndUsers() {
        return notificationEventsDAO.getEnabledWithTextsAndUsers();
    }

}
