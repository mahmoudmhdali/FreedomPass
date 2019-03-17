package com.freedomPass.project.service;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.dao.UserCompanyInfoLocationsDao;
import com.freedomPass.project.helpermodel.OutletAndCompanyLocations;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserCompanyInfoLocations;
import com.freedomPass.project.model.UserProfile;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userCompanyInfoLocationsService")
@Transactional
public class UserCompanyInfoLocationsServiceImpl extends AbstractService implements UserCompanyInfoLocationsService {

    @Autowired
    UserCompanyInfoLocationsDao userCompanyInfoLocationsDao;

    @Override
    public ResponseBodyEntity editLocations(OutletAndCompanyLocations outletAndCompanyLocations) {
        UserProfile user = getAuthenticatedUser();
        if (user != null) {
            try {
                Collection<UserCompanyInfoLocations> userCompanyInfoLocations = new ArrayList<>();

                if ((outletAndCompanyLocations.getLatitude1() != null && !outletAndCompanyLocations.getLatitude1().equals(""))
                        || (outletAndCompanyLocations.getLongitude1() != null && !outletAndCompanyLocations.getLongitude1().equals(""))) {
                    UserCompanyInfoLocations userCompanyInfoLocation = new UserCompanyInfoLocations();
                    userCompanyInfoLocation.setLatitude(outletAndCompanyLocations.getLatitude1());
                    userCompanyInfoLocation.setLongitude(outletAndCompanyLocations.getLongitude1());
                    userCompanyInfoLocation.setLocationIndex(1);
                    userCompanyInfoLocation.setUserCompanyInfo(user.getUserCompanyInfo());
                    userCompanyInfoLocations.add(userCompanyInfoLocation);
                }

                if ((outletAndCompanyLocations.getLatitude2() != null && !outletAndCompanyLocations.getLatitude2().equals(""))
                        || (outletAndCompanyLocations.getLongitude2() != null && !outletAndCompanyLocations.getLongitude2().equals(""))) {
                    UserCompanyInfoLocations userCompanyInfoLocation = new UserCompanyInfoLocations();
                    userCompanyInfoLocation.setLatitude(outletAndCompanyLocations.getLatitude2());
                    userCompanyInfoLocation.setLongitude(outletAndCompanyLocations.getLongitude2());
                    userCompanyInfoLocation.setLocationIndex(2);
                    userCompanyInfoLocation.setUserCompanyInfo(user.getUserCompanyInfo());
                    userCompanyInfoLocations.add(userCompanyInfoLocation);
                }

                if ((outletAndCompanyLocations.getLatitude3() != null && !outletAndCompanyLocations.getLatitude3().equals(""))
                        || (outletAndCompanyLocations.getLongitude3() != null && !outletAndCompanyLocations.getLongitude3().equals(""))) {
                    UserCompanyInfoLocations userCompanyInfoLocation = new UserCompanyInfoLocations();
                    userCompanyInfoLocation.setLatitude(outletAndCompanyLocations.getLatitude3());
                    userCompanyInfoLocation.setLongitude(outletAndCompanyLocations.getLongitude3());
                    userCompanyInfoLocation.setLocationIndex(3);
                    userCompanyInfoLocation.setUserCompanyInfo(user.getUserCompanyInfo());
                    userCompanyInfoLocations.add(userCompanyInfoLocation);
                }

                if ((outletAndCompanyLocations.getLatitude4() != null && !outletAndCompanyLocations.getLatitude4().equals(""))
                        || (outletAndCompanyLocations.getLongitude4() != null && !outletAndCompanyLocations.getLongitude4().equals(""))) {
                    UserCompanyInfoLocations userCompanyInfoLocation = new UserCompanyInfoLocations();
                    userCompanyInfoLocation.setLatitude(outletAndCompanyLocations.getLatitude4());
                    userCompanyInfoLocation.setLongitude(outletAndCompanyLocations.getLongitude4());
                    userCompanyInfoLocation.setLocationIndex(4);
                    userCompanyInfoLocation.setUserCompanyInfo(user.getUserCompanyInfo());
                    userCompanyInfoLocations.add(userCompanyInfoLocation);
                }
                userCompanyInfoLocationsDao.deleteLocations(user.getUserCompanyInfo().getUserCompanyInfoLocationsCollection());
                user.getUserCompanyInfo().setUserCompanyInfoLocationsCollection(userCompanyInfoLocations);
            } catch (Exception ex) {
                Logger.ERROR("1- Error editLocations 1 on API [" + ex.getMessage() + "]", "", "");
            }
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                    .addHttpResponseEntityData("success", "Success")
                    .getResponse();
        } else {
            return ResponseBuilder.getInstance().
                    setHttpResponseEntityResultCode(ResponseCode.SOURCE_NOT_FOUND)
                    .addHttpResponseEntityData("Message", "User not found")
                    .getResponse();
        }
    }

}
