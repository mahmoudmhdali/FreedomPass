package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletOffer;
import java.util.List;

public interface UserOutletOfferDao {

    List<UserOutletOffer> getUserOutletOffers();

    UserOutletOffer getUserOutletOffer(Long id);
}
