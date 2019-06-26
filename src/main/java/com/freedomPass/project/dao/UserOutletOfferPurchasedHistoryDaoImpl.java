package com.freedomPass.project.dao;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.model.UserOutletOfferPurchased;
import com.freedomPass.project.model.UserOutletOfferPurchasedHistory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userOutletOfferPurchasedHistoryDao")
public class UserOutletOfferPurchasedHistoryDaoImpl extends AbstractDao<Long, UserOutletOfferPurchasedHistory> implements UserOutletOfferPurchasedHistoryDao {

    @Override
    public void addUserOutletOfferPurchasedHistory(UserOutletOfferPurchasedHistory userOutletOfferPurchasedHistory) {
        try {
            save(userOutletOfferPurchasedHistory);
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserOutletPurchasedDao 4 on API [" + ex.getMessage() + "]", userOutletOfferPurchasedHistory, "");
        }
    }

}
