package com.freedomPass.project.dao;

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
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<UserPassPurchased> userPassPurchased = (List<UserPassPurchased>) criteria.list();
        return userPassPurchased;
    }

    @Override
    public UserPassPurchased getUserPassPurchased(Long id) {
        UserPassPurchased userPassPurchased = getByKey(id);
        return userPassPurchased;
    }

    @Override
    public UserPassPurchasedPagination getUserPassPurchasedPagination(int pageNumber, int maxRes) {
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
    }

    @Override
    public void addUserPassPurchased(UserPassPurchased userPassPurchased) {
        save(userPassPurchased);
    }

}
