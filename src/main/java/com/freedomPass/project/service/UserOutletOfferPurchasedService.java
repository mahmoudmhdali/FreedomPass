package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.model.UserOutletOfferPurchased;
import java.util.List;

public interface UserOutletOfferPurchasedService {

    List<UserOutletOfferPurchased> getUserOutletOfferPurchaseds();

    UserOutletOfferPurchased getUserOutletOfferPurchased(Long id);

    UserOutletOfferPurchased getUserOutletOfferPurchasedByOfferIDAndUserID(Long offerID, Long userID);

    ResponseBodyEntity addUserOutletOfferPurchased(Long offerID, Long userID);

}
