package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserPassPurchasedDao;
import com.freedomPass.project.model.UserPassPurchased;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userPassPurchasedService")
@Transactional
public class UserPassPurchasedServiceImpl extends AbstractService implements UserPassPurchasedService {

    @Autowired
    UserPassPurchasedDao userPassPurchasedDao;

    @Override
    public List<UserPassPurchased> getUserPassPurchaseds() {
        return userPassPurchasedDao.getUserPassPurchaseds();
    }

    @Override
    public UserPassPurchased getUserPassPurchased(Long id) {
        return userPassPurchasedDao.getUserPassPurchased(id);
    }

}
