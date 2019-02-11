package com.freedomPass.project.dao;

import com.freedomPass.project.model.Language;
import com.freedomPass.project.model.NotificationEvents;
import com.freedomPass.project.model.NotificationTexts;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("notificationTextsDao")
public class NotificationTextsDaoImpl implements NotificationTextsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public NotificationTexts get(NotificationEvents event, Language language) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(NotificationTexts.class);
        criteria.add(Restrictions.eq("NotificationEvent", event));
        criteria.add(Restrictions.eq("language", language));
        List<NotificationTexts> notificationTexts = (List<NotificationTexts>) criteria.list();
        if (notificationTexts != null) {
            return (NotificationTexts) criteria.list().get(0);
        }
        return null;
    }

}
