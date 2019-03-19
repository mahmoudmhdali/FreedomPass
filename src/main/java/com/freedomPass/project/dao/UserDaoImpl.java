package com.freedomPass.project.dao;

import com.freedomPass.api.commons.Logger;
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
        try {
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
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserDao 1 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

    @Override
    public List<UserProfile> getOutletUsers() {
        try {
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
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserDao 2 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

    @Override
    public List<UserProfile> getCompanyUsers() {
        try {
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
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserDao 3 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

    @Override
    public UsersPagination getUsersPagination(Long excludeLoggedInUserID, Integer type, Long headID, int pageNumber, int maxRes) {
        try {
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
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserDao 4 on API [" + ex.getMessage() + "]", "Exclued: " + excludeLoggedInUserID + ". Type: " + type + ". Head: " + headID + ". Page number: " + pageNumber + ". Max result: " + maxRes, "");
        }
        return null;
    }

    @Override
    public UserProfile getUser(Long id) {
        try {
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
            Hibernate.initialize(user.getUserOutletInfo());
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
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserDao 5 on API [" + ex.getMessage() + "]", id, "");
        }
        return null;
    }

    @Override
    public UserProfile getUser(String email) {
        try {
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
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserDao 6 on API [" + ex.getMessage() + "]", email, "");
        }
        return null;
    }

    @Override
    public UserProfile getUserByToken(String token) {
        try {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.eq("resetPasswordToken", token))
                    .add(Restrictions.ge("resetPasswordTokenValidity", new Date()))
                    .add(Restrictions.isNull("deletedDate"));
            UserProfile user = (UserProfile) criteria.uniqueResult();
            return user;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserDao 7 on API [" + ex.getMessage() + "]", token, "");
        }
        return null;
    }

    @Override
    public UserProfile filterByMobileNumber(String mobileNumber) {
        try {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.eq("mobileNumber", mobileNumber))
                    .add(Restrictions.isNull("deletedDate"));
            return (UserProfile) criteria.uniqueResult();
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserDao 8 on API [" + ex.getMessage() + "]", mobileNumber, "");
        }
        return null;
    }

    @Override
    public List<UserProfile> filterUsersByGroup(Long groupId) {
        try {
            Criteria criteria = createEntityCriteria()
                    .createAlias("groupCollection", "groups")
                    .add(Restrictions.eq("groups.id", groupId))
                    .add(Restrictions.isNull("deletedDate"))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
            return (List<UserProfile>) criteria.list();
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserDao 9 on API [" + ex.getMessage() + "]", groupId, "");
        }
        return null;
    }

    @Override
    public void addUser(UserProfile user) {
        try {
            save(user);
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserDao 10 on API [" + ex.getMessage() + "]", user, "");
        }
    }

    @Override
    public void deleteUser(UserProfile user) {
        try {
            delete(user);
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserDao 11 on API [" + ex.getMessage() + "]", user, "");
        }
    }

}
