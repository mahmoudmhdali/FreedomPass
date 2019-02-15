package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletOfferPurchased;
import java.util.List;

public interface UserOutletOfferPurchasedDao {

    List<UserOutletOfferPurchased> getUserOutletOfferPurchaseds();

    UserOutletOfferPurchased getUserOutletOfferPurchased(Long id);
}
