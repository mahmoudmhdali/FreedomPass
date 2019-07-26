package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserDao;
import com.freedomPass.project.dao.UserOutletOfferDao;
import com.freedomPass.project.dao.UserOutletOfferPurchasedDao;
import com.freedomPass.project.dao.UserOutletOfferPurchasedHistoryDao;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserOffersUsed;
import com.freedomPass.project.model.UserOutletOffer;
import com.freedomPass.project.model.UserOutletOfferPurchased;
import com.freedomPass.project.model.UserOutletOfferPurchasedHistory;
import com.freedomPass.project.model.UserPassPurchased;
import com.freedomPass.project.model.UserProfile;
import java.util.ArrayList;
import java.util.Calendar;
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
    UserOutletOfferPurchasedHistoryDao userOutletOfferPurchasedHistoryDao;

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
    public ResponseBodyEntity addUserOutletOfferPurchased(Long offerID, UserProfile user, UserProfile outletUser) {
        UserOutletOffer offer = userOutletOfferDao.getUserOutletOffer(offerID);
        int offerExist = 0; //0 does not exist, > 1 number of existance

        if (offer == null || offer.getDeletedDate() != null) {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("Message", "Voucher does not exist")
                    .getResponse();
        }

        if (user == null || user.getDeletedDate() != null) {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("Message", "User does not exist")
                    .getResponse();
        }

        if (offer.getUserOutletInfo().getUserProfileId().getId().longValue() != outletUser.getId().longValue()) {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("Message", "Voucher does not exist.")
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
                                        .addHttpResponseEntityData("Message", "Voucher is removed")
                                        .getResponse();
                            } else {
                                return ResponseBuilder.getInstance().
                                        setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                                        .addHttpResponseEntityData("Message", "Pass is removed")
                                        .getResponse();
                            }
                        } else {
                            offerExist = offerExist + offerPurchased.getNumberOfUsage();
                        }
                    }
                }
            }
        }

        if (offerExist > 0) {
            UserOutletOfferPurchased userOutletOfferPurchased = getUserOutletOfferPurchasedByOfferIDAndUserID(offerID, user.getId());
            if (userOutletOfferPurchased == null) {
                userOutletOfferPurchased = new UserOutletOfferPurchased();
                userOutletOfferPurchased.setCounter(1);
                Calendar c = Calendar.getInstance();
                c.setTime(new Date()); // Now use today date.
                if (offer.getTypeOfUsage() == 1) {
                    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    c.set(Calendar.WEEK_OF_YEAR, c.get(Calendar.WEEK_OF_YEAR) + 1);
                } else if (offer.getTypeOfUsage() == 2) {
                    c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
                    c.set(Calendar.DAY_OF_MONTH, 1);
                } else if (offer.getTypeOfUsage() == 1) {
                    c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
                    c.set(Calendar.MONTH, 1);
                    c.set(Calendar.DAY_OF_MONTH, 1);
                }
                userOutletOfferPurchased.setNextResetDate(c.getTime());
                userOutletOfferPurchased.setUsedDate(new Date());
                userOutletOfferPurchased.setUserOutletOffer(offer);
                userOutletOfferPurchased.setUserProfileId(user);
                userOutletOfferPurchasedDao.addUserOutletOfferPurchased(userOutletOfferPurchased);
                UserOutletOfferPurchasedHistory userOutletOfferPurchasedHistory = new UserOutletOfferPurchasedHistory();
                userOutletOfferPurchasedHistory.setUsedDate(new Date());
                userOutletOfferPurchasedHistory.setUserOutletOffer(offer);
                userOutletOfferPurchasedHistory.setUserProfileId(user);
                userOutletOfferPurchasedHistoryDao.addUserOutletOfferPurchasedHistory(userOutletOfferPurchasedHistory);
            } else {
                if (offer.getOutletOfferType().getId() == 2) {
                    return ResponseBuilder.getInstance().
                            setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                            .addHttpResponseEntityData("Message", "Pass already used")
                            .getResponse();
                } else {
                    if (userOutletOfferPurchased.getCounter() >= offerExist) {
                        if ((new Date()).compareTo(userOutletOfferPurchased.getNextResetDate()) > 0) {
                            userOutletOfferPurchased.setCounter(1);
                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date()); // Now use today date.
                            if (offer.getTypeOfUsage() == 1) {
                                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                                c.set(Calendar.WEEK_OF_YEAR, c.get(Calendar.WEEK_OF_YEAR) + 1);
                            } else if (offer.getTypeOfUsage() == 2) {
                                c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
                                c.set(Calendar.DAY_OF_MONTH, 1);
                            } else if (offer.getTypeOfUsage() == 1) {
                                c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
                                c.set(Calendar.MONTH, 1);
                                c.set(Calendar.DAY_OF_MONTH, 1);
                            }
                            userOutletOfferPurchased.setNextResetDate(c.getTime());
                            userOutletOfferPurchased.setUsedDate(new Date());
                            UserOutletOfferPurchasedHistory userOutletOfferPurchasedHistory = new UserOutletOfferPurchasedHistory();
                            userOutletOfferPurchasedHistory.setUsedDate(new Date());
                            userOutletOfferPurchasedHistory.setUserOutletOffer(offer);
                            userOutletOfferPurchasedHistory.setUserProfileId(user);
                            userOutletOfferPurchasedHistoryDao.addUserOutletOfferPurchasedHistory(userOutletOfferPurchasedHistory);
                        } else {
                            String type = "";
                            if (offer.getTypeOfUsage() == 1) {
                                type = "week";
                            } else if (offer.getTypeOfUsage() == 2) {
                                type = "month";
                            } else if (offer.getTypeOfUsage() == 1) {
                                type = "year";
                            }
                            return ResponseBuilder.getInstance().
                                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                                    .addHttpResponseEntityData("Message", "User already reached voucher max limit. (" + offer.getNumberOfUsage() + " times during this " + type + ")")
                                    .getResponse();
                        }
                    } else {
                        userOutletOfferPurchased.setCounter(userOutletOfferPurchased.getCounter() + 1);
                        UserOutletOfferPurchasedHistory userOutletOfferPurchasedHistory = new UserOutletOfferPurchasedHistory();
                        userOutletOfferPurchasedHistory.setUsedDate(new Date());
                        userOutletOfferPurchasedHistory.setUserOutletOffer(offer);
                        userOutletOfferPurchasedHistory.setUserProfileId(user);
                        userOutletOfferPurchasedHistoryDao.addUserOutletOfferPurchasedHistory(userOutletOfferPurchasedHistory);
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
                        .addHttpResponseEntityData("Message", "User does not have access to this voucher")
                        .getResponse();
            } else {
                return ResponseBuilder.getInstance().
                        setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                        .addHttpResponseEntityData("Message", "User does not have access to this pass")
                        .getResponse();
            }
        }
    }

    @Override
    public List<UserOffersUsed> getUserOfferUsed() {
        UserProfile loggedInUser = getAuthenticatedUser();
        List<UserOffersUsed> userOffersUseds = new ArrayList<>();
        List<UserOutletOfferPurchased> userOutletOfferPurchased = userOutletOfferPurchasedDao.getUserOutletOfferPurchasedByUserID(loggedInUser.getId());
        for (UserOutletOfferPurchased userOutletOfferPurchase : userOutletOfferPurchased) {
            UserOffersUsed userOffersUsed = new UserOffersUsed();
            userOffersUsed.setOfferID(userOutletOfferPurchase.getUserOutletOffer().getId());
            userOffersUsed.setOfferType(userOutletOfferPurchase.getUserOutletOffer().getOutletOfferType().getId());
            userOffersUsed.setMaxNumberOfUsage(userOutletOfferPurchase.getUserOutletOffer().getNumberOfUsage());
            userOffersUsed.setNumberOfUsedTimes(userOutletOfferPurchase.getCounter());
            userOffersUsed.setResetDate(userOutletOfferPurchase.getNextResetDate());
            userOffersUseds.add(userOffersUsed);
        }
        return userOffersUseds;
    }

}
