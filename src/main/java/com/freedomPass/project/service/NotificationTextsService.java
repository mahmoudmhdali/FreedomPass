package com.freedomPass.project.service;

import com.freedomPass.project.model.Language;
import com.freedomPass.project.model.NotificationEvents;
import com.freedomPass.project.model.NotificationTexts;

public interface NotificationTextsService {

    NotificationTexts get(NotificationEvents event, Language language);
}
