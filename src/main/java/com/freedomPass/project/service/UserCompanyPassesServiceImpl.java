package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserCompanyPassesDao;
import com.freedomPass.project.model.UserCompanyPasses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userCompanyPassesService")
@Transactional
public class UserCompanyPassesServiceImpl extends AbstractService implements UserCompanyPassesService {

    @Autowired
    UserCompanyPassesDao userCompanyPassesDao;

    @Override
    public List<UserCompanyPasses> getUserCompanyPasses() {
        return userCompanyPassesDao.getUserCompanyPasses();
    }

    @Override
    public UserCompanyPasses getUserCompanyPasse(Long id) {
        return userCompanyPassesDao.getUserCompanyPasse(id);
    }

}
