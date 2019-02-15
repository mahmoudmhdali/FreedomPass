package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserCompanyInfoDao;
import com.freedomPass.project.model.UserCompanyInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userCompanyInfoService")
@Transactional
public class UserCompanyInfoServiceImpl extends AbstractService implements UserCompanyInfoService {

    @Autowired
    UserCompanyInfoDao userCompanyInfoDao;

    @Override
    public List<UserCompanyInfo> getUserCompanyInfos() {
        return userCompanyInfoDao.getUserCompanyInfos();
    }

    @Override
    public UserCompanyInfo getUserCompanyInfo(Long id) {
        return userCompanyInfoDao.getUserCompanyInfo(id);
    }

}
