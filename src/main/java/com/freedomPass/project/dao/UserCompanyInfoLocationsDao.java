package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserCompanyInfoImages;
import com.freedomPass.project.model.UserCompanyInfoLocations;
import java.util.Collection;

public interface UserCompanyInfoLocationsDao {

    void deleteLocations(Collection<UserCompanyInfoLocations> locations);
}
