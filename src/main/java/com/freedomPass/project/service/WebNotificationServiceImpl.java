package com.freedomPass.project.service;

import com.freedomPass.project.dao.WebNotificationsDao;
import com.freedomPass.project.model.WebNotifications;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("webNotificationService")
@Transactional
public class WebNotificationServiceImpl implements WebNotificationService {

    @Autowired
    WebNotificationsDao webNotificationsDao;

    @Override
    public List<WebNotifications> getAll(long userID, boolean all) {
        return webNotificationsDao.getAll(userID, all);
    }

    @Override
    public void updateNotSeen(long userID) {
        webNotificationsDao.updateNotSeen(userID);
    }

    @Override
    public void add(WebNotifications webNotification) {
        webNotificationsDao.add(webNotification);
    }

}
