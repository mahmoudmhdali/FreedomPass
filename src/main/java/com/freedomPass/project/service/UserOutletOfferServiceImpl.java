package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserOutletOfferDao;
import com.freedomPass.project.model.UserOutletOffer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userOutletOfferService")
@Transactional
public class UserOutletOfferServiceImpl extends AbstractService implements UserOutletOfferService {

    @Autowired
    UserOutletOfferDao userOutletOfferDao;

    @Override
    public List<UserOutletOffer> getUserOutletOffers() {
        return userOutletOfferDao.getUserOutletOffers();
    }

    @Override
    public UserOutletOffer getUserOutletOffer(Long id) {
        return userOutletOfferDao.getUserOutletOffer(id);
    }

}
