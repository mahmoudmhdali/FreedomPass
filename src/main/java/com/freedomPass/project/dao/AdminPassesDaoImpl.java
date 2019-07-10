package com.freedomPass.project.dao;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.helpermodel.AdminPassesPagination;
import com.freedomPass.project.model.AdminPasses;
import com.freedomPass.project.model.UserOutletOffer;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("adminPassesDao")
public class AdminPassesDaoImpl extends AbstractDao<Long, AdminPasses> implements AdminPassesDao {

    @Override
    public List<AdminPasses> getAdminPasses() {
        try {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.isNull("deletedDate"));
            List<AdminPasses> adminPasses = (List<AdminPasses>) criteria.list();
            for (AdminPasses adminPasse : adminPasses) {
                Hibernate.initialize(adminPasse.getUserOutletOfferCollection());
                for (UserOutletOffer userOutletOffer : adminPasse.getUserOutletOfferCollection()) {
                    Hibernate.initialize(userOutletOffer.getOutletOfferType());
                }
            }
            return adminPasses;
        } catch (Exception ex) {
            Logger.ERROR("1- Error AdminPassesDao 1 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

    @Override
    public List<AdminPasses> getAdminPassesForUsers() {
        try {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.eq("corporateOnly", false))
                    .add(Restrictions.isNull("deletedDate"));
            List<AdminPasses> adminPasses = (List<AdminPasses>) criteria.list();
            for (AdminPasses adminPasse : adminPasses) {
                Hibernate.initialize(adminPasse.getUserOutletOfferCollection());
                for (UserOutletOffer userOutletOffer : adminPasse.getUserOutletOfferCollection()) {
                    Hibernate.initialize(userOutletOffer.getOutletOfferType());
                }
            }
            return adminPasses;
        } catch (Exception ex) {
            Logger.ERROR("1- Error AdminPassesDao 1 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

    @Override
    public List<AdminPasses> getAdminPassesByOfferID(Long offerID) {
        List<AdminPasses> filteredAdminPasses = new ArrayList<>();
        try {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.isNull("deletedDate"));
            List<AdminPasses> adminPasses = (List<AdminPasses>) criteria.list();
            boolean passAdded = false;
            for (AdminPasses adminPasse : adminPasses) {
                passAdded = false;
                Hibernate.initialize(adminPasse.getUserOutletOfferCollection());
                for (UserOutletOffer userOutletOffer : adminPasse.getUserOutletOfferCollection()) {
                    Hibernate.initialize(userOutletOffer.getOutletOfferType());
                    if (!passAdded
                            && userOutletOffer.getId().longValue() == offerID.longValue()
                            && !adminPasse.getCorporateOnly()) {
                        filteredAdminPasses.add(adminPasse);
                        passAdded = true;
                    }
                }
            }
            return filteredAdminPasses;
        } catch (Exception ex) {
            Logger.ERROR("1- Error AdminPassesDao 1 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

    @Override
    public AdminPassesPagination getAdminPassesPagination(int pageNumber, int maxRes) {
        try {
            Criteria criteria = createEntityCriteria();
            criteria.addOrder(Order.asc("name"));
            criteria.add(Restrictions.isNull("deletedDate"));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
            criteria.setProjection(Projections.rowCount());
            Number totalResults = (Number) criteria.uniqueResult();
            criteria.setProjection(null);
            criteria.setResultTransformer(Criteria.ROOT_ENTITY);
            criteria.setFirstResult((pageNumber - 1) * maxRes);
            criteria.setMaxResults(maxRes);
            List<AdminPasses> passes = (List<AdminPasses>) criteria.list();
            for (AdminPasses adminPasse : passes) {
                Hibernate.initialize(adminPasse.getUserOutletOfferCollection());
                for (UserOutletOffer userOutletOffer : adminPasse.getUserOutletOfferCollection()) {
                    Hibernate.initialize(userOutletOffer.getOutletOfferType());
                }
            }
            int currentPage = pageNumber;
            int maxPages = (int) Math.ceil((double) ((double) totalResults.intValue() / (double) maxRes));
            AdminPassesPagination adminPassesPagination = new AdminPassesPagination(maxPages, currentPage, totalResults.intValue(), passes);
            return adminPassesPagination;
        } catch (Exception ex) {
            Logger.ERROR("1- Error AdminPassesDao 2 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

    @Override
    public AdminPasses getAdminPasse(Long id) {
        try {
            AdminPasses adminPasses = getByKey(id);
            if (adminPasses == null || adminPasses.getDeletedDate() != null) {
                return null;
            }
            return adminPasses;
        } catch (Exception ex) {
            Logger.ERROR("1- Error AdminPassesDao 3 on API [" + ex.getMessage() + "]", id, "");
        }
        return null;
    }

    @Override
    public void addAdminPasse(AdminPasses adminPasse) {
        try {
            save(adminPasse);
        } catch (Exception ex) {
            Logger.ERROR("1- Error AdminPassesDao 4 on API [" + ex.getMessage() + "]", adminPasse, "");
        }
    }

}
