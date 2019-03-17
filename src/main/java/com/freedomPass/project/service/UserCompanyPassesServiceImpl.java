package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserCompanyPassesDao;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserCompanyPassPagination;
import com.freedomPass.project.model.UserCompanyPasses;
import com.freedomPass.project.model.UserProfile;
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
    public List<UserCompanyPasses> getUserCompanyPassesByCompanyUserId(Long id) {
        return userCompanyPassesDao.getUserCompanyPassesByCompanyUserId(id);
    }

    @Override
    public UserCompanyPasses getUserCompanyPasse(Long id) {
        return userCompanyPassesDao.getUserCompanyPasse(id);
    }

    @Override
    public UserCompanyPassPagination getUserCompanyPassesPagination(int pageNumber, int maxRes) {
        return userCompanyPassesDao.getUserCompanyPassesPagination(pageNumber, maxRes);
    }

    @Override
    public ResponseBodyEntity addUserCompanyPasses(UserCompanyPasses userCompanyPasses) {
        userCompanyPasses.setRemainingUsers(userCompanyPasses.getNumberOfUsers());
        userCompanyPassesDao.addUserCompanyPasses(userCompanyPasses);
        return ResponseBuilder.getInstance().
                setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("userCompanyPass", "Company package added successfully")
                .getResponse();
    }

}
