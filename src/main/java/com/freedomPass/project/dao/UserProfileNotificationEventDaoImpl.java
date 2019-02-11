package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.UserProfileNotificationEvent;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userProfileNotificationEventDao")
public class UserProfileNotificationEventDaoImpl extends AbstractDao<Long, UserProfileNotificationEvent> implements UserProfileNotificationEventDao {

    @Override
    public List<UserProfileNotificationEvent> getAll(UserProfile user) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("userProfile", user))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<UserProfileNotificationEvent> list = criteria.list();
        for (UserProfileNotificationEvent UserProfileNotification : list) {
            Hibernate.initialize(UserProfileNotification.getNotificationEvents());
        }
        return list;
    }

    @Override
    public List<UserProfileNotificationEvent> getAll() {
        Criteria criteria = createEntityCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<UserProfileNotificationEvent> list = criteria.list();
        return list;
    }

    @Override
    public void deleteByUser(UserProfile user) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("userProfile", user))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<UserProfileNotificationEvent> userProfileNotificationEvents = criteria.list();
        for (UserProfileNotificationEvent userProfileNotificationEvent : userProfileNotificationEvents) {
            delete(userProfileNotificationEvent);
        }
    }

}
