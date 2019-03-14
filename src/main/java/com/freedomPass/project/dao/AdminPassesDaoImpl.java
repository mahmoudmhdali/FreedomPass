package com.freedomPass.project.dao;

import com.freedomPass.project.helpermodel.AdminPassesPagination;
import com.freedomPass.project.model.AdminPasses;
import com.freedomPass.project.model.UserOutletOffer;
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
    }

    @Override
    public AdminPassesPagination getAdminPassesPagination(int pageNumber, int maxRes) {
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
    }

    @Override
    public AdminPasses getAdminPasse(Long id) {
        AdminPasses adminPasses = getByKey(id);
        if (adminPasses == null || adminPasses.getDeletedDate() != null) {
            return null;
        }
        return adminPasses;
    }

    @Override
    public void addAdminPasse(AdminPasses adminPasse) {
        save(adminPasse);
    }

}
