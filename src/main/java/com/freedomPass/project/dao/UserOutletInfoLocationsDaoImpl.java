package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletInfoImages;
import com.freedomPass.project.model.UserOutletInfoLocations;
import com.freedomPass.project.model.UserOutletOfferImages;
import java.util.Collection;
import org.springframework.stereotype.Repository;

@Repository("userOutletInfoLocationsDao")
public class UserOutletInfoLocationsDaoImpl extends AbstractDao<Long, UserOutletInfoLocations> implements UserOutletInfoLocationsDao {

    @Override
    public void deleteLocations(Collection<UserOutletInfoLocations> locations) {
        for (UserOutletInfoLocations location : locations) {
            delete(location);
        }
    }
}
