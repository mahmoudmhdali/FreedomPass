package com.freedomPass.project.dao;

import com.freedomPass.project.model.AdminPasses;
import com.freedomPass.project.model.UserOutletOffer;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
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
    public AdminPasses getAdminPasse(Long id) {
        AdminPasses adminPasses = getByKey(id);
        if (adminPasses == null || adminPasses.getDeletedDate() != null) {
            return null;
        }
        return adminPasses;
    }

}
