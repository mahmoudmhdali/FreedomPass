package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletOfferPurchased;
import java.util.List;

public interface UserOutletOfferPurchasedDao {

    List<UserOutletOfferPurchased> getUserOutletOfferPurchaseds();

    UserOutletOfferPurchased getUserOutletOfferPurchased(Long id);

    UserOutletOfferPurchased getUserOutletOfferPurchasedByOfferIDAndUserID(Long offerID, Long userID);

    List<UserOutletOfferPurchased> getUserOutletOfferPurchasedByUserID(Long userID);

    void addUserOutletOfferPurchased(UserOutletOfferPurchased userOutletOfferPurchased);
}
