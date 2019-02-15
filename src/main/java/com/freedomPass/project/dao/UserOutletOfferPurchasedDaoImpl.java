package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletOfferPurchased;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userOutletOfferPurchasedDao")
public class UserOutletOfferPurchasedDaoImpl extends AbstractDao<Long, UserOutletOfferPurchased> implements UserOutletOfferPurchasedDao {

    @Override
    public List<UserOutletOfferPurchased> getUserOutletOfferPurchaseds() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<UserOutletOfferPurchased> userOutletOfferPurchased = (List<UserOutletOfferPurchased>) criteria.list();
        return userOutletOfferPurchased;
    }

    @Override
    public UserOutletOfferPurchased getUserOutletOfferPurchased(Long id) {
        UserOutletOfferPurchased userOutletOfferPurchased = getByKey(id);
        return userOutletOfferPurchased;
    }

}
