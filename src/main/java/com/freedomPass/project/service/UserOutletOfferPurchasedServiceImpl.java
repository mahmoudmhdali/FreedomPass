package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserOutletOfferPurchasedDao;
import com.freedomPass.project.model.UserOutletOfferPurchased;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userOutletOfferPurchasedService")
@Transactional
public class UserOutletOfferPurchasedServiceImpl extends AbstractService implements UserOutletOfferPurchasedService {

    @Autowired
    UserOutletOfferPurchasedDao userOutletOfferPurchasedDao;

    @Override
    public List<UserOutletOfferPurchased> getUserOutletOfferPurchaseds() {
        return userOutletOfferPurchasedDao.getUserOutletOfferPurchaseds();
    }

    @Override
    public UserOutletOfferPurchased getUserOutletOfferPurchased(Long id) {
        return userOutletOfferPurchasedDao.getUserOutletOfferPurchased(id);
    }

}
