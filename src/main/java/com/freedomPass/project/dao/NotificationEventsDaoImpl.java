package com.freedomPass.project.dao;

import com.freedomPass.project.model.NotificationEvents;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.UserProfileNotificationEvent;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("notificationEventsDao")
public class NotificationEventsDaoImpl extends AbstractDao<Long, NotificationEvents> implements NotificationEventsDao {

    @Override
    public NotificationEvents get(String eventName) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("name", eventName));
        NotificationEvents notificationEvent = (NotificationEvents) criteria.uniqueResult();
        return notificationEvent;
    }

    @Override
    public NotificationEvents get(Long id) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("id", id));
        NotificationEvents notificationEvent = (NotificationEvents) criteria.uniqueResult();
        return notificationEvent;
    }

    @Override
    public List<NotificationEvents> getAll() {
        Criteria criteria = createEntityCriteria();
        List<NotificationEvents> notificationEvents = (List<NotificationEvents>) criteria.list();
        for (NotificationEvents notificationEvent : notificationEvents) {
            Hibernate.initialize(notificationEvent.getUserProfileNotificationEventCollection());
        }
        return notificationEvents;
    }

    @Override
    public List<NotificationEvents> getAllWithTexts() {
        Criteria criteria = createEntityCriteria();
        List<NotificationEvents> notificationEvents = (List<NotificationEvents>) criteria.list();
        for (NotificationEvents notificationEvent : notificationEvents) {
            Hibernate.initialize(notificationEvent.getTexts());
        }
        return notificationEvents;
    }

    @Override
    public List<NotificationEvents> getEnabled() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.or(Restrictions.eq("hasWeb", true), Restrictions.eq("hasSMS", true), Restrictions.eq("hasEmail", true)));
        List<NotificationEvents> notificationEvents = (List<NotificationEvents>) criteria.list();
        return notificationEvents;
    }

    @Override
    public List<NotificationEvents> getEnabledWithTextsAndUsers() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.or(Restrictions.eq("hasWeb", true), Restrictions.eq("hasSMS", true), Restrictions.eq("hasEmail", true)));
        List<NotificationEvents> notificationEvents = (List<NotificationEvents>) criteria.list();
        for (NotificationEvents notificationEvent : notificationEvents) {
            Hibernate.initialize(notificationEvent.getTexts());
            Hibernate.initialize(notificationEvent.getUserProfileNotificationEventCollection());
            if (notificationEvent.getUserProfileNotificationEventCollection() != null) {
                for (UserProfileNotificationEvent userProfileNotificationEvent : notificationEvent.getUserProfileNotificationEventCollection()) {
                    Hibernate.initialize(userProfileNotificationEvent.getUserProfile().getLanguage());
                }
            }
        }
        return notificationEvents;
    }

    @Override
    public List<NotificationEvents> getByUser(UserProfile user) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.or(Restrictions.eq("hasWeb", true), Restrictions.eq("hasSMS", true), Restrictions.eq("hasEmail", true)))
                .createAlias("userProfileNotificationEventCollection", "userProfileNotificationEvent")
                .add(Restrictions.eq("userProfileNotificationEvent.userProfile", user));
        List<NotificationEvents> notificationEvents = (List<NotificationEvents>) criteria.list();
        for (NotificationEvents notificationEvent : notificationEvents) {
            Hibernate.initialize(notificationEvent.getUserProfileNotificationEventCollection());
        }
        return notificationEvents;
    }

}
