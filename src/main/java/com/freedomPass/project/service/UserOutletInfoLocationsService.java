package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.OutletAndCompanyLocations;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.model.UserOutletInfoImages;
import com.freedomPass.project.model.UserOutletInfoLocations;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserOutletInfoLocationsService {

    ResponseBodyEntity editLocations(OutletAndCompanyLocations outletAndCompanyLocations);

}
