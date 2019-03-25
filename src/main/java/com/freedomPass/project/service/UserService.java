package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.UserProfilePasswordValidator;
import com.freedomPass.project.helpermodel.UsersPagination;
import com.freedomPass.project.model.UserAttempt;
import com.freedomPass.project.model.UserCompanyInfo;
import com.freedomPass.project.model.UserOutletInfo;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.WebNotifications;
import java.util.List;
import javax.mail.internet.AddressException;

public interface UserService {

    List<UserProfile> getUsers(Long excludeLoggedInUserID, Integer type, Long headID);

    List<UserProfile> getOutletUsers();

    List<UserProfile> getCompanyUsers();

    UsersPagination getUsersPagination(Long excludeLoggedInUserID, Integer type, Long headID, int pageNumber, int maxRes, int usersType);

    ResponseBodyEntity getUser(Long id);

    ResponseBodyEntity getUser(String email);

    ResponseBodyEntity getUserByToken(String token);
    
    void sendEmailAndUpdateToken(String email) throws AddressException;

    List<UserProfile> filterUsersByGroup(Long groupId);

    UserProfile toUser (Long id);
    
    UserProfile toUser (String email);
    
    ResponseBodyEntity addUser(UserProfile user, UserCompanyInfo userCompanyInfo, UserOutletInfo userOutletInfo) throws AddressException;
    
    ResponseBodyEntity updateUser(UserProfile user, UserCompanyInfo userCompanyInfo, UserOutletInfo userOutletInfo);
    
    ResponseBodyEntity updateUserSettings(UserProfile user);
    
    ResponseBodyEntity changeUserPassword(UserProfile user, UserProfilePasswordValidator userProfilePasswordValidator);
    
    ResponseBodyEntity changeUserPasswordByToken(String token, UserProfilePasswordValidator userProfilePasswordValidator);

    ResponseBodyEntity deleteUser(Long id);

    boolean isUniqueFromOther(UserProfile userProfile);

    void setAccountLocked(String email, boolean locked);

    UserAttempt getUserAttemptCollection(String email);

    ResponseBodyEntity updateUserLanguage(Long languageId);
    
    void addNotification(WebNotifications webNotifcation);

    ResponseBodyEntity getWebNotifications(long userID, boolean all);

    void updateNotSeen(long userID);
    
    ResponseBodyEntity getCountWebNotifications(long userID);

    List<UserProfile> getUserOutletInfosByCategory(Long id);
    
}
