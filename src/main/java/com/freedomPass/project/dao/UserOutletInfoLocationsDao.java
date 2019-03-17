package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserCompanyInfoImages;
import com.freedomPass.project.model.UserCompanyInfoLocations;
import com.freedomPass.project.model.UserOutletInfoLocations;
import java.util.Collection;

public interface UserOutletInfoLocationsDao {

    void deleteLocations(Collection<UserOutletInfoLocations> locations);
}
