package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.UserOffersUsed;
import com.freedomPass.project.model.UserOutletOfferPurchased;
import com.freedomPass.project.model.UserProfile;
import java.util.List;

public interface UserOutletOfferPurchasedService {

    List<UserOutletOfferPurchased> getUserOutletOfferPurchaseds();

    UserOutletOfferPurchased getUserOutletOfferPurchased(Long id);

    UserOutletOfferPurchased getUserOutletOfferPurchasedByOfferIDAndUserID(Long offerID, Long userID);

    ResponseBodyEntity addUserOutletOfferPurchased(Long offerID, UserProfile user, UserProfile outletUser);
    
    List<UserOffersUsed> getUserOfferUsed();

}
