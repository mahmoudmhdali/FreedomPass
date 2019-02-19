package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserOutletInfoDao;
import com.freedomPass.project.model.UserOutletInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userOutletInfoService")
@Transactional
public class UserOutletInfoServiceImpl extends AbstractService implements UserOutletInfoService {

    @Autowired
    UserOutletInfoDao userOutletInfoDao;

    @Override
    public List<UserOutletInfo> getUserOutletInfos() {
        return userOutletInfoDao.getUserOutletInfos();
    }

    @Override
    public List<UserOutletInfo> getUserOutletInfosByCategory(Long id) {
        return userOutletInfoDao.getUserOutletInfosByCategory(id);
    }

    @Override
    public UserOutletInfo getUserOutletInfo(Long id) {
        return userOutletInfoDao.getUserOutletInfo(id);
    }

}
