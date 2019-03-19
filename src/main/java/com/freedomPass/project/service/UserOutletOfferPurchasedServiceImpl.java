package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserDao;
import com.freedomPass.project.dao.UserOutletOfferDao;
import com.freedomPass.project.dao.UserOutletOfferPurchasedDao;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserOutletOffer;
import com.freedomPass.project.model.UserOutletOfferPurchased;
import com.freedomPass.project.model.UserPassPurchased;
import com.freedomPass.project.model.UserProfile;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userOutletOfferPurchasedService")
@Transactional
public class UserOutletOfferPurchasedServiceImpl extends AbstractService implements UserOutletOfferPurchasedService {

    @Autowired
    UserOutletOfferPurchasedDao userOutletOfferPurchasedDao;

    @Autowired
    UserOutletOfferDao userOutletOfferDao;

    @Autowired
    UserDao userDao;

    @Override
    public List<UserOutletOfferPurchased> getUserOutletOfferPurchaseds() {
        return userOutletOfferPurchasedDao.getUserOutletOfferPurchaseds();
    }

    @Override
    public UserOutletOfferPurchased getUserOutletOfferPurchased(Long id) {
        return userOutletOfferPurchasedDao.getUserOutletOfferPurchased(id);
    }

    @Override
    public UserOutletOfferPurchased getUserOutletOfferPurchasedByOfferIDAndUserID(Long offerID, Long userID) {
        return userOutletOfferPurchasedDao.getUserOutletOfferPurchasedByOfferIDAndUserID(offerID, userID);
    }

    @Override
    public ResponseBodyEntity addUserOutletOfferPurchased(Long offerID, Long userID) {
        UserProfile loggedInUser = getAuthenticatedUser();
        UserOutletOffer offer = userOutletOfferDao.getUserOutletOffer(offerID);
        UserProfile user = userDao.getUser(userID);
        int offerExist = 0; //0 does not exist, > 1 number of existance

        if (offer == null) {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("Message", "Offer does not exist")
                    .getResponse();
        }

        if (user == null) {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("Message", "User does not exist")
                    .getResponse();
        }

        if (offer.getUserOutletInfo().getUserProfileId().getId().longValue() != loggedInUser.getId().longValue()) {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("Message", "Offer cannot be used by this user")
                    .getResponse();
        }

        for (UserPassPurchased passPurchased : user.getUserPassPurchased()) {
            if (passPurchased.getValidTill().compareTo(new Date()) > 0) {
                for (UserOutletOffer offerPurchased : passPurchased.getAdminPasses().getUserOutletOfferCollection()) {
                    if (offer.getId().longValue() == offerPurchased.getId().longValue()) {
                        if (offerPurchased.getDeletedDate() != null) {
                            if (offerPurchased.getTypeOfUsage() == 1) {
                                return ResponseBuilder.getInstance().
                                        setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                                        .addHttpResponseEntityData("Message", "Offer is removed")
                                        .getResponse();
                            } else {
                                return ResponseBuilder.getInstance().
                                        setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                                        .addHttpResponseEntityData("Message", "Pass is removed")
                                        .getResponse();
                            }
                        } else {
                            offerExist = offerPurchased.getNumberOfUsage();
                        }
                    }
                }
            }
        }

        if (offerExist > 0) {
            UserOutletOfferPurchased userOutletOfferPurchased = getUserOutletOfferPurchasedByOfferIDAndUserID(offerID, userID);
            if (userOutletOfferPurchased == null) {
                userOutletOfferPurchased = new UserOutletOfferPurchased();
                userOutletOfferPurchased.setCounter(1);
                userOutletOfferPurchased.setNextResetDate(new Date());
                userOutletOfferPurchased.setUsedDate(new Date());
                userOutletOfferPurchased.setUserOutletOffer(offer);
                userOutletOfferPurchased.setUserProfileId(user);
                userOutletOfferPurchasedDao.addUserOutletOfferPurchased(userOutletOfferPurchased);
            } else {
                if (offer.getOutletOfferType().getId() == 2) {
                    return ResponseBuilder.getInstance().
                            setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                            .addHttpResponseEntityData("Message", "Pass already used")
                            .getResponse();
                } else {
                    if (userOutletOfferPurchased.getCounter() >= offerExist) {
                        return ResponseBuilder.getInstance().
                                setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                                .addHttpResponseEntityData("Message", "User already reached offer max limit")
                                .getResponse();
                    } else {
                        userOutletOfferPurchased.setCounter(userOutletOfferPurchased.getCounter() + 1);
                    }
                }
            }
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("Message", "Success")
                    .getResponse();
        } else {
            if (offer.getTypeOfUsage() == 1) {
                return ResponseBuilder.getInstance().
                        setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                        .addHttpResponseEntityData("Message", "User does not have access to this offer")
                        .getResponse();
            } else {
                return ResponseBuilder.getInstance().
                        setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                        .addHttpResponseEntityData("Message", "User does not have access to this pass")
                        .getResponse();
            }
        }
    }

}
