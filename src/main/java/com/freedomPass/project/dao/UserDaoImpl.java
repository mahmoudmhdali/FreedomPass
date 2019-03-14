package com.freedomPass.project.dao;

import com.freedomPass.project.helpermodel.UsersPagination;
import com.freedomPass.project.model.Group;
import com.freedomPass.project.model.UserPassPurchased;
import com.freedomPass.project.model.UserProfile;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Long, UserProfile> implements UserDao {

    @Override
    public List<UserProfile> getUsers(Long excludeLoggedInUserID, Integer type, Long headID) {
        Criteria criteria = createEntityCriteria();
        if (type == 1) {
            criteria.add(Restrictions.eq("parentId", headID));
        }
        if (type == 2 || type == 3 || type == 4) {
            return null;
        }
        criteria.addOrder(Order.asc("name"));
        criteria.add(Restrictions.ne("id", excludeLoggedInUserID)); // To avoid including logged in user
        criteria.add(Restrictions.ne("type", 99));
        criteria.add(Restrictions.isNull("deletedDate"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
        List<UserProfile> users = (List<UserProfile>) criteria.list();
        for (UserProfile user : users) {
            Hibernate.initialize(user.getGroupCollection());
        }
        return users;
    }

    @Override
    public List<UserProfile> getOutletUsers() {
        Criteria criteria = createEntityCriteria();
        criteria.addOrder(Order.asc("name"));
        criteria.add(Restrictions.eq("type", 2));
        criteria.add(Restrictions.isNull("deletedDate"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
        List<UserProfile> users = (List<UserProfile>) criteria.list();
        for (UserProfile user : users) {
            Hibernate.initialize(user.getGroupCollection());
            Hibernate.initialize(user.getUserOutletInfo());
            Hibernate.initialize(user.getUserOutletInfo().getOutletCategoryCollection());
            Hibernate.initialize(user.getUserOutletInfo().getUserOutletInfoImagesCollection());
            Hibernate.initialize(user.getUserOutletInfo().getUserOutletInfoLocationsCollection());
        }
        return users;
    }

    @Override
    public List<UserProfile> getCompanyUsers() {
        Criteria criteria = createEntityCriteria();
        criteria.addOrder(Order.asc("name"));
        criteria.add(Restrictions.eq("type", 1));
        criteria.add(Restrictions.isNull("deletedDate"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
        List<UserProfile> users = (List<UserProfile>) criteria.list();
        for (UserProfile user : users) {
            Hibernate.initialize(user.getGroupCollection());
            Hibernate.initialize(user.getUserCompanyInfo());
            Hibernate.initialize(user.getUserCompanyInfo().getUserCompanyInfoImagesCollection());
            Hibernate.initialize(user.getUserCompanyInfo().getUserCompanyInfoLocationsCollection());
        }
        return users;
    }

    @Override
    public UsersPagination getUsersPagination(Long excludeLoggedInUserID, Integer type, Long headID, int pageNumber, int maxRes) {
        Criteria criteria = createEntityCriteria();
        if (type == 1) {
            criteria.add(Restrictions.eq("parentId", headID));
        }
        if (type == 2 || type == 3 || type == 4) {
            return null;
        }
        criteria.addOrder(Order.asc("name"));
        criteria.add(Restrictions.ne("id", excludeLoggedInUserID)); // To avoid including logged in user
        criteria.add(Restrictions.ne("type", 99));
        criteria.add(Restrictions.isNull("deletedDate"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
        criteria.setProjection(Projections.rowCount());
        Number totalResults = (Number) criteria.uniqueResult();
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        criteria.setFirstResult((pageNumber - 1) * maxRes);
        criteria.setMaxResults(maxRes);
        List<UserProfile> users = (List<UserProfile>) criteria.list();
        int currentPage = pageNumber;
        int maxPages = (int) Math.ceil((double) ((double) totalResults.intValue() / (double) maxRes));
        UsersPagination usersPagination = new UsersPagination(maxPages, currentPage, totalResults.intValue(), users);
        return usersPagination;
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
        Hibernate.initialize(user.getUserCompanyInfo());
        if (user.getUserCompanyInfo() != null) {
            Hibernate.initialize(user.getUserCompanyInfo().getUserCompanyInfoImagesCollection());
            Hibernate.initialize(user.getUserCompanyInfo().getUserCompanyInfoLocationsCollection());
        } else if (user.getUserOutletInfo() != null) {
            Hibernate.initialize(user.getUserOutletInfo().getUserOutletInfoImagesCollection());
            Hibernate.initialize(user.getUserOutletInfo().getUserOutletInfoLocationsCollection());
        } else {
            Hibernate.initialize(user.getUserPassPurchased());
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
            for (UserPassPurchased userPassPurchased : user.getUserPassPurchased()) {
                Hibernate.initialize(userPassPurchased.getAdminPasses().getUserOutletOfferCollection());
            }
            for (Group group : user.getGroupCollection()) {
                Hibernate.initialize(group.getRoleCollection());
            }
        }
        return user;
    }

    @Override
    public UserProfile getUserByToken(String token) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("resetPasswordToken", token))
                .add(Restrictions.ge("resetPasswordTokenValidity", new Date()))
                .add(Restrictions.isNull("deletedDate"));
        UserProfile user = (UserProfile) criteria.uniqueResult();
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
