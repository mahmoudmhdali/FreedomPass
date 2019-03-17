package com.freedomPass.project.dao;

import com.freedomPass.api.commons.Logger;
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
        try {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.isNull("deletedDate"));
            List<UserCompanyPasses> userCompanyPasses = (List<UserCompanyPasses>) criteria.list();
            return userCompanyPasses;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserCompanyPassesDao 1 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

    @Override
    public UserCompanyPasses getUserCompanyPasse(Long id) {
        try {
            UserCompanyPasses userCompanyPasse = getByKey(id);
            if (userCompanyPasse == null || userCompanyPasse.getDeletedDate() != null) {
                return null;
            }
            Hibernate.initialize(userCompanyPasse.getUserCompanyInfo().getUserProfileId());
            return userCompanyPasse;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserCompanyPassesDao 2 on API [" + ex.getMessage() + "]", id, "");
        }
        return null;
    }

    @Override
    public List<UserCompanyPasses> getUserCompanyPassesByCompanyUserId(Long id) {
        try {
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
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserCompanyPassesDao 3 on API [" + ex.getMessage() + "]", id, "");
        }
        return null;
    }

    @Override
    public UserCompanyPassPagination getUserCompanyPassesPagination(int pageNumber, int maxRes) {
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
            List<UserCompanyPasses> userCompanyPasses = (List<UserCompanyPasses>) criteria.list();
            for (UserCompanyPasses userCompanyPass : userCompanyPasses) {
                Hibernate.initialize(userCompanyPass.getAdminPasses());
                Hibernate.initialize(userCompanyPass.getUserCompanyInfo().getUserProfileId());
            }
            int currentPage = pageNumber;
            int maxPages = (int) Math.ceil((double) ((double) totalResults.intValue() / (double) maxRes));
            UserCompanyPassPagination userCompanyPassPagination = new UserCompanyPassPagination(maxPages, currentPage, totalResults.intValue(), userCompanyPasses);
            return userCompanyPassPagination;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserCompanyPassesDao 4 on API [" + ex.getMessage() + "]", "Page Number: " + pageNumber + ". Max response: " + maxRes, "");
        }
        return null;
    }

    @Override
    public void addUserCompanyPasses(UserCompanyPasses userCompanyPass) {
        try {
            save(userCompanyPass);
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserCompanyPassesDao 5 on API [" + ex.getMessage() + "]", userCompanyPass, "");
        }
    }

}
