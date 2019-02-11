package com.freedomPass.project.service;

import com.freedomPass.project.dao.NotificationTextsDao;
import com.freedomPass.project.model.Language;
import com.freedomPass.project.model.NotificationEvents;
import com.freedomPass.project.model.NotificationTexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("notificationTextsService")
@Transactional
public class NotificationTextsServiceImpl implements NotificationTextsService {

    @Autowired
    private NotificationTextsDao notificationTextsDAO;

    @Override
    public NotificationTexts get(NotificationEvents event, Language language) {
        // TODO Auto-generated method stub
        return notificationTextsDAO.get(event, language);
    }

}
