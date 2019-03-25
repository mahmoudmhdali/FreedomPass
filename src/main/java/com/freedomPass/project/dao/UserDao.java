package com.freedomPass.project.dao;

import com.freedomPass.project.helpermodel.UsersPagination;
import com.freedomPass.project.model.UserProfile;
import java.util.List;

public interface UserDao {

    List<UserProfile> getUsers(Long excludeLoggedInUserID, Integer type, Long headID);

    List<UserProfile> getOutletUsers();

    List<UserProfile> getCompanyUsers();

    UsersPagination getUsersPagination(Long excludeLoggedInUserID, Integer type, Long headID, int pageNumber, int maxRes, int usersType);

    UserProfile getUser(Long id);

    UserProfile getUserByToken(String token);

    UserProfile getUser(String email);

    UserProfile filterByMobileNumber(String mobileNumber);

    List<UserProfile> filterUsersByGroup(Long groupId);

    void addUser(UserProfile user);

    void deleteUser(UserProfile user);

    List<UserProfile> getUserOutletInfosByCategory(Long id);
}
