package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.UserProfilePasswordValidator;
import com.freedomPass.project.model.UserAttempt;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.WebNotifications;
import java.util.List;

public interface UserService {

    /**
     *
     * @return all users whose accounts are not deleted
     */
    List<UserProfile> getUsers(Long excludeLoggedInUserID);

    /**
     *
     * @param id the id of the user
     * @return {@link com.freedomPass.project.model.UserProfile}
     */
    ResponseBodyEntity getUser(Long id);

    /**
     *
     * @param email the email of the user (email is not case sensitive)
     * @return {@link com.freedomPass.project.model.UserProfile}
     */
    ResponseBodyEntity getUser(String email);

    /**
     * 
     * @param groupId
     * @return 
     */
    List<UserProfile> filterUsersByGroup(Long groupId);

    /**
     * 
     * @param id
     * @return 
     */
    UserProfile toUser (Long id);
    
    /**
     * 
     * @param email
     * @return 
     */
    UserProfile toUser (String email);
    
    /**
     *
     * @param user {@link com.freedomPass.project.model.UserProfile} object with the
     * mandatory parameters
     * @return {@link com.freedomPass.project.helpermodel.ResponseBodyEntity}
     * holding the code, description and data
     */
    ResponseBodyEntity addUser(UserProfile user);
    
    /**
     * 
     * @param user
     * @return 
     */
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
