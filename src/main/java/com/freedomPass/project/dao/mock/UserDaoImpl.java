package com.freedomPass.project.dao.mock;

import com.freedomPass.project.dao.UserDao;
import com.freedomPass.project.model.UserProfile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("userDaoMock")
public class UserDaoImpl implements UserDao {

    @Override
    public List<UserProfile> getUsers(Long excludeLoggedInUserID) {
        List<UserProfile> userProfiles = new ArrayList<>();
        for (int numOfProfiles = 1; numOfProfiles <= 2; numOfProfiles++) {
            UserProfile user = new UserProfile();
            user.setAccountLocked(true);
            user.setAccountNonExpired(true);
            user.setCreatedDate(new Date());
            user.setCredentialExpired(true);
            user.setEmail("user-0" + numOfProfiles + "@domain.com");
            user.setEnabled(true);
            user.setName("user-" + numOfProfiles);
            userProfiles.add(user);
        }
        return userProfiles;
    }

    @Override
    public UserProfile getUser(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserProfile getUser(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserProfile filterByMobileNumber(String msisdn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserProfile> filterUsersByGroup(Long groupId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addUser(UserProfile user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteUser(UserProfile user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
