package com.freedomPass.project.dao;

import com.freedomPass.project.helpermodel.UserCompanyPassPagination;
import com.freedomPass.project.model.UserCompanyPasses;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userCompanyPassesDao")
public class UserCompanyPassesDaoImpl extends AbstractDao<Long, UserCompanyPasses> implements UserCompanyPassesDao {

    @Override
    public List<UserCompanyPasses> getUserCompanyPasses() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<UserCompanyPasses> userCompanyPasses = (List<UserCompanyPasses>) criteria.list();
        return userCompanyPasses;
    }

    @Override
    public UserCompanyPasses getUserCompanyPasse(Long id) {
        UserCompanyPasses userCompanyPasse = getByKey(id);
        if (userCompanyPasse == null || userCompanyPasse.getDeletedDate() != null) {
            return null;
        }
        Hibernate.initialize(userCompanyPasse.getUserCompanyInfo().getUserProfileId());
        return userCompanyPasse;
    }

    @Override
    public List<UserCompanyPasses> getUserCompanyPassesByCompanyUserId(Long id) {
        Criteria criteria = createEntityCriteria()
                .createAlias("userCompanyInfo", "userCompanyInfoAlias")
                .add(Restrictions.eq("userCompanyInfoAlias.id", id))
                .add(Restrictions.isNull("deletedDate"));
        List<UserCompanyPasses> userCompanyPasses = (List<UserCompanyPasses>) criteria.list();
        for (UserCompanyPasses userCompanyPass : userCompanyPasses) {
            Hibernate.initialize(userCompanyPass.getAdminPasses());
            Hibernate.initialize(userCompanyPass.getUserCompanyInfo().getUserProfileId());
        }
        return userCompanyPasses;
    }

    @Override
    public UserCompanyPassPagination getUserCompanyPassesPagination(int pageNumber, int maxRes) {
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
        List<UserCompanyPasses> userCompanyPasses = (List<UserCompanyPasses>) criteria.list();
        for (UserCompanyPasses userCompanyPass : userCompanyPasses) {
            Hibernate.initialize(userCompanyPass.getAdminPasses());
            Hibernate.initialize(userCompanyPass.getUserCompanyInfo().getUserProfileId());
        }
        int currentPage = pageNumber;
        int maxPages = (int) Math.ceil((double) ((double) totalResults.intValue() / (double) maxRes));
        UserCompanyPassPagination userCompanyPassPagination = new UserCompanyPassPagination(maxPages, currentPage, totalResults.intValue(), userCompanyPasses);
        return userCompanyPassPagination;
    }

    @Override
    public void addUserCompanyPasses(UserCompanyPasses userCompanyPass) {
        save(userCompanyPass);
    }

}
