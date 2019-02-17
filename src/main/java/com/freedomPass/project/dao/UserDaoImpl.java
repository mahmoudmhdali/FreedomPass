package com.freedomPass.project.dao;

import com.freedomPass.project.model.Group;
import com.freedomPass.project.model.UserProfile;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Long, UserProfile> implements UserDao {

    @Override
    public List<UserProfile> getUsers(Long excludeLoggedInUserID) {
        Criteria criteria = createEntityCriteria()
                .addOrder(Order.asc("id"))
                .add(Restrictions.ne("id", excludeLoggedInUserID)) // To avoid including logged in user
                .createAlias("groupCollection", "groups")
                .add(Restrictions.ne("groups.id", (long) 1)) // To avoid including Users who are of group Support
                .add(Restrictions.ne("groups.id", (long) 2)) // To avoid including Users who are of group Installer
                .add(Restrictions.isNull("deletedDate"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
        List<UserProfile> users = (List<UserProfile>) criteria.list();
        for (UserProfile user : users) {
            Hibernate.initialize(user.getGroupCollection());
        }
        return users;
    }

    @Override
    public UserProfile getUser(Long id) {
        UserProfile user = getByKey(id);
        if (user == null) {
            return null;
        }
        if (user.getDeletedDate() != null) {
            return null;
        }
        Hibernate.initialize(user.getAuthorities());
        Hibernate.initialize(user.getGroupCollection());
        Hibernate.initialize(user.getLanguage());
        for (Group group : user.getGroupCollection()) {
            Hibernate.initialize(group.getRoleCollection());
        }
        return user;
    }

    @Override
    public UserProfile getUser(String email) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("email", email))
                .add(Restrictions.isNull("deletedDate"));
        UserProfile user = (UserProfile) criteria.uniqueResult();
        if (user != null) {
            Hibernate.initialize(user.getAuthorities());
            Hibernate.initialize(user.getGroupCollection());
            Hibernate.initialize(user.getLanguage());
            Hibernate.initialize(user.getUserPassPurchased());
            for (Group group : user.getGroupCollection()) {
                Hibernate.initialize(group.getRoleCollection());
            }
        }
        return user;
    }

    @Override
    public UserProfile filterByMobileNumber(String mobileNumber) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("mobileNumber", mobileNumber))
                .add(Restrictions.isNull("deletedDate"));
        return (UserProfile) criteria.uniqueResult();
    }

    @Override
    public List<UserProfile> filterUsersByGroup(Long groupId) {
        Criteria criteria = createEntityCriteria()
                .createAlias("groupCollection", "groups")
                .add(Restrictions.eq("groups.id", groupId))
                .add(Restrictions.isNull("deletedDate"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
        return (List<UserProfile>) criteria.list();
    }

    @Override
    public void addUser(UserProfile user) {
        save(user);
    }

    @Override
    public void deleteUser(UserProfile user) {
        delete(user);
    }

}
