package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.OutletAndCompanyLocations;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.model.UserCompanyInfoImages;
import com.freedomPass.project.model.UserCompanyInfoLocations;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserCompanyInfoLocationsService {

    ResponseBodyEntity editLocations(OutletAndCompanyLocations outletAndCompanyLocations);

}
