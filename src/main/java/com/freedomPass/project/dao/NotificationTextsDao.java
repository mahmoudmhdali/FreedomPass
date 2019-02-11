package com.freedomPass.project.dao;

import com.freedomPass.project.model.Language;
import com.freedomPass.project.model.NotificationEvents;
import com.freedomPass.project.model.NotificationTexts;

public interface NotificationTextsDao {

    NotificationTexts get(NotificationEvents event, Language language);
}
