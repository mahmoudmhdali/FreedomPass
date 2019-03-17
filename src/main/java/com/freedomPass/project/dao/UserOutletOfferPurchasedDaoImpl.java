package com.freedomPass.project.dao;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.model.UserOutletOfferPurchased;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userOutletOfferPurchasedDao")
public class UserOutletOfferPurchasedDaoImpl extends AbstractDao<Long, UserOutletOfferPurchased> implements UserOutletOfferPurchasedDao {

    @Override
    public List<UserOutletOfferPurchased> getUserOutletOfferPurchaseds() {
        try {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.isNull("deletedDate"));
            List<UserOutletOfferPurchased> userOutletOfferPurchased = (List<UserOutletOfferPurchased>) criteria.list();
            return userOutletOfferPurchased;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserOutletPurchasedDao 1 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

    @Override
    public UserOutletOfferPurchased getUserOutletOfferPurchased(Long id) {
        try {
            UserOutletOfferPurchased userOutletOfferPurchased = getByKey(id);
            return userOutletOfferPurchased;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserOutletPurchasedDao 2 on API [" + ex.getMessage() + "]", id, "");
        }
        return null;
    }

}
