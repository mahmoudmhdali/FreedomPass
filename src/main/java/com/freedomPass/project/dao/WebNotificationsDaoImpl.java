package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.WebNotifications;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("webNotificationsDao")
public class WebNotificationsDaoImpl implements WebNotificationsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<WebNotifications> getAll(long userID, boolean all) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(WebNotifications.class);

        UserProfile user = (UserProfile) session.get(UserProfile.class, userID);
        criteria.add(Restrictions.eq("user", user));
        criteria.addOrder(Order.desc("dateAdded"));
        if (!all) {
            criteria.add(Restrictions.eq("hasSEEN", false));
        }

        List<WebNotifications> list = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return list;
    }

    @Override
    public Integer getCountAll(long userID) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(WebNotifications.class);

        UserProfile user = (UserProfile) session.get(UserProfile.class, userID);
        criteria.add(Restrictions.eq("user", user));
        criteria.addOrder(Order.desc("dateAdded"));
        criteria.add(Restrictions.eq("hasSEEN", false));
        criteria.setProjection(Projections.rowCount());
        Number num = (Number) criteria.uniqueResult();
        return num.intValue();
    }

    @Override
    public void updateNotSeen(long userID) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(WebNotifications.class);
        UserProfile user = (UserProfile) session.get(UserProfile.class, userID);
        criteria.add(Restrictions.eq("user", user));
        criteria.add(Restrictions.eq("hasSEEN", false));
        List<WebNotifications> notifications = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        for (WebNotifications notification : notifications) {
            notification.setHasSEEN(true);
            session.flush();
        }
    }

    @Override
    public void add(WebNotifications webNotification) {
        sessionFactory.getCurrentSession().save(webNotification);

    }

}
