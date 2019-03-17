package com.freedomPass.project.service;

import com.freedomPass.project.dao.UserOutletInfoLocationsDao;
import com.freedomPass.project.helpermodel.OutletAndCompanyLocations;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.UserOutletInfoLocations;
import com.freedomPass.project.model.UserProfile;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userOutletInfoLocationsService")
@Transactional
public class UserOutletInfoLocationsServiceImpl extends AbstractService implements UserOutletInfoLocationsService {

    @Autowired
    UserOutletInfoLocationsDao userOutletInfoLocationsDao;

    @Override
    public ResponseBodyEntity editLocations(OutletAndCompanyLocations outletAndCompanyLocations) {
        UserProfile user = getAuthenticatedUser();
        if (user != null) {
            Collection<UserOutletInfoLocations> userOutletInfoLocations = new ArrayList<>();

            if ((outletAndCompanyLocations.getLatitude1() != null && !outletAndCompanyLocations.getLatitude1().equals(""))
                    || (outletAndCompanyLocations.getLongitude1() != null && !outletAndCompanyLocations.getLongitude1().equals(""))) {
                UserOutletInfoLocations userOutletInfoLocation = new UserOutletInfoLocations();
                userOutletInfoLocation.setLatitude(outletAndCompanyLocations.getLatitude1());
                userOutletInfoLocation.setLongitude(outletAndCompanyLocations.getLongitude1());
                userOutletInfoLocation.setLocationIndex(1);
                userOutletInfoLocation.setUserOutletInfo(user.getUserOutletInfo());
                userOutletInfoLocations.add(userOutletInfoLocation);
            }

            if ((outletAndCompanyLocations.getLatitude2() != null && !outletAndCompanyLocations.getLatitude2().equals(""))
                    || (outletAndCompanyLocations.getLongitude2() != null && !outletAndCompanyLocations.getLongitude2().equals(""))) {
                UserOutletInfoLocations userOutletInfoLocation = new UserOutletInfoLocations();
                userOutletInfoLocation.setLatitude(outletAndCompanyLocations.getLatitude2());
                userOutletInfoLocation.setLongitude(outletAndCompanyLocations.getLongitude2());
                userOutletInfoLocation.setLocationIndex(2);
                userOutletInfoLocation.setUserOutletInfo(user.getUserOutletInfo());
                userOutletInfoLocations.add(userOutletInfoLocation);
            }

            if ((outletAndCompanyLocations.getLatitude3() != null && !outletAndCompanyLocations.getLatitude3().equals(""))
                    || (outletAndCompanyLocations.getLongitude3() != null && !outletAndCompanyLocations.getLongitude3().equals(""))) {
                UserOutletInfoLocations userOutletInfoLocation = new UserOutletInfoLocations();
                userOutletInfoLocation.setLatitude(outletAndCompanyLocations.getLatitude3());
                userOutletInfoLocation.setLongitude(outletAndCompanyLocations.getLongitude3());
                userOutletInfoLocation.setLocationIndex(3);
                userOutletInfoLocation.setUserOutletInfo(user.getUserOutletInfo());
                userOutletInfoLocations.add(userOutletInfoLocation);
            }

            if ((outletAndCompanyLocations.getLatitude4() != null && !outletAndCompanyLocations.getLatitude4().equals(""))
                    || (outletAndCompanyLocations.getLongitude4() != null && !outletAndCompanyLocations.getLongitude4().equals(""))) {
                UserOutletInfoLocations userOutletInfoLocation = new UserOutletInfoLocations();
                userOutletInfoLocation.setLatitude(outletAndCompanyLocations.getLatitude4());
                userOutletInfoLocation.setLongitude(outletAndCompanyLocations.getLongitude4());
                userOutletInfoLocation.setLocationIndex(4);
                userOutletInfoLocation.setUserOutletInfo(user.getUserOutletInfo());
                userOutletInfoLocations.add(userOutletInfoLocation);
            }
            userOutletInfoLocationsDao.deleteLocations(user.getUserOutletInfo().getUserOutletInfoLocationsCollection());
            user.getUserOutletInfo().setUserOutletInfoLocationsCollection(userOutletInfoLocations);
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
