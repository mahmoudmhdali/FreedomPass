package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.UserProfilePasswordValidator;
import com.freedomPass.project.model.UserAttempt;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.WebNotifications;
import java.util.List;

public interface UserService {

    List<UserProfile> getUsers(Long excludeLoggedInUserID);

    ResponseBodyEntity getUser(Long id);

    ResponseBodyEntity getUser(String email);

    List<UserProfile> filterUsersByGroup(Long groupId);

    UserProfile toUser (Long id);
    
    UserProfile toUser (String email);
    
    ResponseBodyEntity addUser(UserProfile user);
    
    ResponseBodyEntity updateUser(UserProfile user);
    
    ResponseBodyEntity updateUserSettings(UserProfile user);
    
    ResponseBodyEntity changeUserPassword(UserProfile user, UserProfilePasswordValidator userProfilePasswordValidator);

    ResponseBodyEntity deleteUser(Long id);

    boolean isUniqueFromOther(UserProfile userProfile);

    void setAccountLocked(String email, boolean locked);

    UserAttempt getUserAttemptCollection(String email);

    ResponseBodyEntity updateUserLanguage(Long languageId);
    
    void addNotification(WebNotifications webNotifcation);

    ResponseBodyEntity getWebNotifications(long userID, boolean all);

    void updateNotSeen(long userID);
    
    ResponseBodyEntity getCountWebNotifications(long userID);
    
}
