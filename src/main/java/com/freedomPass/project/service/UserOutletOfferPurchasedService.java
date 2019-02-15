package com.freedomPass.project.service;

import com.freedomPass.project.model.UserOutletOfferPurchased;
import java.util.List;

public interface UserOutletOfferPurchasedService {

    List<UserOutletOfferPurchased> getUserOutletOfferPurchaseds();

    UserOutletOfferPurchased getUserOutletOfferPurchased(Long id);

}
