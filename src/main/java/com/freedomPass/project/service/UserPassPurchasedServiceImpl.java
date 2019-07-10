package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserPassPurchasedDao;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserPassPurchasedPagination;
import com.freedomPass.project.model.UserCompanyPasses;
import com.freedomPass.project.model.UserPassPurchased;
import com.freedomPass.project.model.UserProfile;
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

    @Autowired
    UserService userService;

    @Override
    public List<UserPassPurchased> getUserPassPurchaseds() {
        return userPassPurchasedDao.getUserPassPurchaseds();
    }

    @Override
    public List<UserPassPurchased> getUserPassPurchasedsGifted(Long userID, boolean isGifted, Long headID) {

        return userPassPurchasedDao.getUserPassPurchasedsGifted(userID, isGifted, headID);
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
    public ResponseBodyEntity addUserPassPurchased(UserPassPurchased userPassPurchased, Long packageId, boolean isGifted) {
        userPassPurchasedDao.addUserPassPurchased(userPassPurchased);
        if (isGifted) {
            UserCompanyPasses userCompanyPass = userCompanyPassesService.getUserCompanyPasse(packageId);
            int currentUsers = userCompanyPass.getRemainingUsers();
            userCompanyPass.setRemainingUsers(currentUsers - 1);
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("remainingUsers", currentUsers - 1)
                    .getResponse();
        } else {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("success", "Success")
                    .getResponse();
        }
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

    @Override
    public ResponseBodyEntity transferUserPassPurchased(Long fromUserID, Long toUserID, Long packageID) {
        UserProfile user = getAuthenticatedUser();
        UserPassPurchased persistantUserPassPurchased = userPassPurchasedDao.getUserPassPurchased(packageID);
        if (persistantUserPassPurchased == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("package", "Package does not exist")
                    .getResponse();
        } else {
            UserProfile toUser = userService.toUser(toUserID);
            if (toUser == null) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("userTo", "To User does not exist")
                        .getResponse();
            } else {
                UserProfile fromUser = userService.toUser(fromUserID);
                if (fromUser == null) {
                    return ResponseBuilder.getInstance()
                            .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                            .addHttpResponseEntityData("user", "From User does not exist")
                            .getResponse();
                } else {

                    if (toUser.getId().longValue() == fromUser.getId().longValue()) {
                        return ResponseBuilder.getInstance()
                                .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                                .addHttpResponseEntityData("user", "From User cannot be same as To User")
                                .getResponse();
                    } else {
                        Long fromUserCorpID = fromUser.getParentId();
                        Long toUserCorpID = toUser.getParentId();
                        Long corpID = user.getId();
                        if (corpID.longValue() == toUserCorpID.longValue() && corpID.longValue() == fromUserCorpID.longValue()) {
                            persistantUserPassPurchased.setUserProfileId(toUser);
                            return ResponseBuilder.getInstance().
                                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                                    .addHttpResponseEntityData("Success", "Success Transfer User")
                                    .getResponse();
                        } else {
                            return ResponseBuilder.getInstance()
                                    .setHttpResponseEntityResultCode(ResponseCode.UNAUTHORIZED_USER_ACTION)
                                    .addHttpResponseEntityData("user", "Access Denied")
                                    .getResponse();
                        }
                    }

                }
            }
        }
    }
}
