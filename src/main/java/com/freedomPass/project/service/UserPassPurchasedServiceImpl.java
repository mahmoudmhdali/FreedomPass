package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserPassPurchasedDao;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserPassPurchasedPagination;
import com.freedomPass.project.model.UserCompanyPasses;
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

    @Autowired
    UserCompanyPassesService userCompanyPassesService;

    @Override
    public List<UserPassPurchased> getUserPassPurchaseds() {
        return userPassPurchasedDao.getUserPassPurchaseds();
    }

    @Override
    public UserPassPurchased getUserPassPurchased(Long id) {
        return userPassPurchasedDao.getUserPassPurchased(id);
    }

    @Override
    public UserPassPurchasedPagination getUserPassPurchasedPagination(int pageNumber, int maxRes) {
        return userPassPurchasedDao.getUserPassPurchasedPagination(pageNumber, maxRes);
    }

    @Override
    public ResponseBodyEntity addUserPassPurchased(UserPassPurchased userPassPurchased, Long packageId) {
        UserCompanyPasses userCompanyPass = userCompanyPassesService.getUserCompanyPasse(packageId);
        userPassPurchasedDao.addUserPassPurchased(userPassPurchased);
        int currentUsers = userCompanyPass.getRemainingUsers();
        userCompanyPass.setRemainingUsers(currentUsers - 1);
        return ResponseBuilder.getInstance().
                setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("remainingUsers", currentUsers - 1)
                .getResponse();
    }

    @Override
    public ResponseBodyEntity editUserPassPurchased(UserPassPurchased userPassPurchased) {
        UserPassPurchased persistantUserPassPurchased = userPassPurchasedDao.getUserPassPurchased(userPassPurchased.getId());
        if (persistantUserPassPurchased != null) {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("Message", "User Pass updated succesfully")
                    .getResponse();
        } else {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SOURCE_NOT_FOUND)
                    .addHttpResponseEntityData("Message", "User Pass not found")
                    .getResponse();
        }
    }

}
