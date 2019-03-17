package com.freedomPass.project.dao;

import com.freedomPass.api.commons.Logger;
import com.freedomPass.project.model.UserCompanyInfoImages;
import com.freedomPass.project.model.UserCompanyInfoLocations;
import com.freedomPass.project.model.UserOutletOfferImages;
import java.util.Collection;
import org.springframework.stereotype.Repository;

@Repository("userCompanyInfoLocationsDao")
public class UserCompanyInfoLocationsDaoImpl extends AbstractDao<Long, UserCompanyInfoLocations> implements UserCompanyInfoLocationsDao {

    @Override
    public void deleteLocations(Collection<UserCompanyInfoLocations> locations) {
        for (UserCompanyInfoLocations location : locations) {
            try {
                delete(location);
            } catch (Exception ex) {
                Logger.ERROR("1- Error UserCompanyInfoLocationsDao 1 on API [" + ex.getMessage() + "]", location, "");
            }
        }
    }
}
