package com.freedomPass.project.dao;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.helpermodel.UserPassPurchasedPagination;
import com.freedomPass.project.model.UserPassPurchased;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userPassPurchasedDao")
public class UserPassPurchasedDaoImpl extends AbstractDao<Long, UserPassPurchased> implements UserPassPurchasedDao {

    @Override
    public List<UserPassPurchased> getUserPassPurchaseds() {
        try {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.isNull("deletedDate"));
            List<UserPassPurchased> userPassPurchased = (List<UserPassPurchased>) criteria.list();
            return userPassPurchased;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserPassPurchasedDao 1 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

    @Override
    public UserPassPurchased getUserPassPurchased(Long id) {
        try {
            UserPassPurchased userPassPurchased = getByKey(id);
            return userPassPurchased;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserPassPurchasedDao 2 on API [" + ex.getMessage() + "]", id, "");
        }
        return null;
    }

    @Override
    public UserPassPurchasedPagination getUserPassPurchasedPagination(int pageNumber, int maxRes) {
        try {
            Criteria criteria = createEntityCriteria();
            criteria.addOrder(Order.asc("createdDate"));
            criteria.add(Restrictions.isNull("deletedDate"));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
            criteria.setProjection(Projections.rowCount());
            Number totalResults = (Number) criteria.uniqueResult();
            criteria.setProjection(null);
            criteria.setResultTransformer(Criteria.ROOT_ENTITY);
            criteria.setFirstResult((pageNumber - 1) * maxRes);
            criteria.setMaxResults(maxRes);
            List<UserPassPurchased> userPassPurchaseds = (List<UserPassPurchased>) criteria.list();
            for (UserPassPurchased userPassPurchased : userPassPurchaseds) {
                Hibernate.initialize(userPassPurchased.getAdminPasses());
                Hibernate.initialize(userPassPurchased.getUserProfileId());
            }
            int currentPage = pageNumber;
            int maxPages = (int) Math.ceil((double) ((double) totalResults.intValue() / (double) maxRes));
            UserPassPurchasedPagination userPassPurchasedPagination = new UserPassPurchasedPagination(maxPages, currentPage, totalResults.intValue(), userPassPurchaseds);
            return userPassPurchasedPagination;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserPassPurchasedDao 3 on API [" + ex.getMessage() + "]", "Page Number: " + pageNumber + ". Max result: " + maxRes, "");
        }
        return null;
    }

    @Override
    public void addUserPassPurchased(UserPassPurchased userPassPurchased) {
        try {
            save(userPassPurchased);
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserPassPurchasedDao 4 on API [" + ex.getMessage() + "]", userPassPurchased, "");
        }
    }

}
