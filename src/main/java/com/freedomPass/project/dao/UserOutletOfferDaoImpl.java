package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletOffer;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userOutletOfferDao")
public class UserOutletOfferDaoImpl extends AbstractDao<Long, UserOutletOffer> implements UserOutletOfferDao {

    @Override
    public List<UserOutletOffer> getUserOutletOffers() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<UserOutletOffer> userOutletOffers = (List<UserOutletOffer>) criteria.list();
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

}
