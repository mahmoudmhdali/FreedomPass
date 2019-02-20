package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletOffer;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userOutletOfferDao")
public class UserOutletOfferDaoImpl extends AbstractDao<Long, UserOutletOffer> implements UserOutletOfferDao {

    @Override
    public List<UserOutletOffer> getUserOutletOffers() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<UserOutletOffer> userOutletOffers = (List<UserOutletOffer>) criteria.list();
        for (UserOutletOffer userOutletOffer : userOutletOffers) {
            Hibernate.initialize(userOutletOffer.getOutletOfferType());
            Hibernate.initialize(userOutletOffer.getUserOutletInfo());
        }
        return userOutletOffers;
    }

    @Override
    public UserOutletOffer getUserOutletOffer(Long id) {
        UserOutletOffer userOutletOffer = getByKey(id);
        if (userOutletOffer == null || userOutletOffer.getDeletedDate() != null) {
            return null;
        }
        return userOutletOffer;
    }

    @Override
    public List<UserOutletOffer> getUserOutletOffersByType(Long type) {
        Criteria criteria = createEntityCriteria()
                .createAlias("outletOfferType", "type")
                .add(Restrictions.eq("type.id", type))
                .add(Restrictions.isNull("deletedDate"));
        List<UserOutletOffer> userOutletOffers = (List<UserOutletOffer>) criteria.list();
        for (UserOutletOffer userOutletOffer : userOutletOffers) {
            Hibernate.initialize(userOutletOffer.getOutletOfferType());
            Hibernate.initialize(userOutletOffer.getUserOutletInfo());
        }
        return userOutletOffers;
    }

    @Override
    public List<UserOutletOffer> getUserOutletOffersByOutletId(Long id) {
        Criteria criteria = createEntityCriteria()
                .createAlias("userOutletInfo", "outlet")
                .add(Restrictions.eq("outlet.id", id))
                .add(Restrictions.isNull("deletedDate"));
        List<UserOutletOffer> userOutletOffers = (List<UserOutletOffer>) criteria.list();
        return userOutletOffers;
    }

}
