package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.OutletAndCompanyLocations;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;

public interface UserOutletInfoLocationsService {

    ResponseBodyEntity editLocations(OutletAndCompanyLocations outletAndCompanyLocations);

}
