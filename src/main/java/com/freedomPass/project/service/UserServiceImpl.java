package com.freedomPass.project.service;

import com.freedomPass.api.commons.utils.QRCodeGenerator;
import com.freedomPass.api.commons.utils.SessionUtils;
import com.freedomPass.api.engine.SettingsEngine;
import com.freedomPass.project.dao.UserDao;
import com.freedomPass.project.dao.WebNotificationsDao;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserProfilePasswordValidator;
import com.freedomPass.project.helpermodel.UsersPagination;
import com.freedomPass.project.model.Group;
import com.freedomPass.project.model.Language;
import com.freedomPass.project.model.UserAttempt;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.model.UserProfileNotificationEvent;
import com.freedomPass.project.model.WebNotifications;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    UserAttemptService userAttemptService;

    @Autowired
    @Qualifier("userDao")
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SessionUtils sessionUtils;

    @Autowired
    GroupService groupService;

    @Autowired
    LanguageService languageService;

    @Autowired
    SettingsEngine settingsEngine;

    @Autowired
    UserProfileNotificationEventService userProfileNotificationEventService;

    @Autowired
    private WebNotificationsDao webNotificationsDao;

    @Autowired
    QRCodeGenerator qRCodeGenerator;

    @Override
    public List<UserProfile> getUsers(Long excludeLoggedInUserID, Integer type, Long headID) {
        return userDao.getUsers(excludeLoggedInUserID, type, headID);
    }

    @Override
    public UsersPagination getUsersPagination(Long excludeLoggedInUserID, Integer type, Long headID, int pageNumber, int maxRes) {
        return userDao.getUsersPagination(excludeLoggedInUserID, type, headID, pageNumber, maxRes);
    }

    @Override
    public ResponseBodyEntity getUser(Long id) {
        UserProfile userProfle = userDao.getUser(id);
        if (userProfle == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("user.notFound", null))
                    .getResponse();
        }
        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("user", userProfle)
                .getResponse();
    }

    @Override
    public ResponseBodyEntity getUser(String email) {
        UserProfile userProfle = userDao.getUser(email.toLowerCase());
        if (userProfle == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("user.notFound", null))
                    .getResponse();
        }
        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("user", userProfle)
                .getResponse();
    }

    @Override
    public List<UserProfile> filterUsersByGroup(Long groupId) {
        return userDao.filterUsersByGroup(groupId);
    }

    @Override
    public UserProfile toUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public UserProfile toUser(String email) {
        return userDao.getUser(email);
    }

    @Override
    public ResponseBodyEntity addUser(UserProfile user) {
        UserProfile loggedInUser = this.getAuthenticatedUser();
        user.setCountry(1);
        user.setEnabled(true);
        if (loggedInUser != null) {
            if (loggedInUser.getType() != 0 && loggedInUser.getType() != 1 && loggedInUser.getType() != 99) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.UNAUTHORIZED_USER_ACTION)
                        .setHttpResponseEntityResultDescription("Access denied for this resource. Contact your service provider for more help")
                        .getResponse();
            }
            if (loggedInUser.getType() == 1) {
                user.setType(3);
                user.setParentId(loggedInUser.getId());
            }
            if ((loggedInUser.getType() == 0 || loggedInUser.getType() == 99) && user.getType() == null) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("type", "Type is required")
                        .getResponse();
            }
            if (loggedInUser.getType() == 0 && user.getType() != 0 && user.getType() != 1 && user.getType() != 2 && user.getType() != 3) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("type", "Type not exist")
                        .getResponse();
            }
        } else {
            user.setType(4);
        }

        if (userDao.getUser(user.getEmail()) != null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("email", this.getMessageBasedOnLanguage("user.emailTaken", null))
                    .getResponse();
        }

        Long msisdnLength = (Long) settingsEngine.getFirstLevelSetting("MSISDN_LENGTH");
        if (user.getMobileNumber().length() != msisdnLength) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("mobileNumber", this.getMessageBasedOnLanguage("user.msisdnLength", new Object[]{msisdnLength}))
                    .getResponse();
        }

        // Check if mobile number is unique
        if (userDao.filterByMobileNumber(user.getMobileNumber()) != null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("mobileNumber", this.getMessageBasedOnLanguage("user.msisdnTaken", null))
                    .getResponse();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().trim().toLowerCase());
        user.setName(user.getName().trim().toLowerCase());

        /* Set UserLoginAttemps */
        UserAttempt userAttempt = new UserAttempt();
        userAttempt.setAttempt(0);
        userAttempt.setLastModified(new Date());
        user.setUserAttempt(userAttempt);
        userAttempt.setUserProfileId(user);

        user.setUserProfileNotificationEventCollection(null);
        user.setLanguage(languageService.getLanguage(Long.parseLong("1")));

        Collection<Group> groupCollection = new ArrayList<>();
        if (null != user.getType()) {
            switch (user.getType()) {
                case 0: {
                    Group group = groupService.toGroupForAdd(8L);
                    groupCollection.add(group);
                    user.setJobTitle("System User");
                    user.setUserOutletInfo(null);
                    user.setUserCompanyInfo(null);
                    break;
                }
                case 1: {
                    Group group = groupService.toGroupForAdd(4L);
                    groupCollection.add(group);
                    user.setJobTitle("Company Admin");
                    if (user.getUserCompanyInfo() == null) {
                        return ResponseBuilder.getInstance()
                                .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                                .addHttpResponseEntityData("userCompanyInfo.info", "Info is required")
                                .getResponse();
                    }
                    user.getUserCompanyInfo().setUserProfileId(user);
                    user.setUserOutletInfo(null);
                    break;
                }
                case 2: {
                    Group group = groupService.toGroupForAdd(5L);
                    groupCollection.add(group);
                    user.setJobTitle("Outlet Admin");
                    if (user.getUserOutletInfo() == null) {
                        return ResponseBuilder.getInstance()
                                .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                                .addHttpResponseEntityData("userOutletInfo.info", "Info is required")
                                .getResponse();
                    }
                    user.getUserOutletInfo().setUserProfileId(user);
                    user.setUserCompanyInfo(null);
                    break;
                }
                case 3: {
                    Group group = groupService.toGroupForAdd(6L);
                    groupCollection.add(group);
                    user.setJobTitle("Company User");
                    user.setUserOutletInfo(null);
                    user.setUserCompanyInfo(null);
                    break;
                }
                case 4: {
                    Group group = groupService.toGroupForAdd(7L);
                    groupCollection.add(group);
                    user.setJobTitle("User");
                    user.setUserOutletInfo(null);
                    user.setUserCompanyInfo(null);
                    break;
                }
                case 99: {
                    Group group = groupService.toGroupForAdd(3L);
                    groupCollection.add(group);
                    user.setJobTitle("Our System User");
                    user.setUserOutletInfo(null);
                    user.setUserCompanyInfo(null);
                    break;
                }
                default:
                    break;
            }
        }
        user.setGroupCollection(groupCollection);
        userDao.addUser(user);
        try {
            qRCodeGenerator.generateQRCodeImage(user.getId().toString(), 350, 350);
            user.setQrCodePath("/QRCodes/" + user.getId().toString() + ".png");
        } catch (WriterException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseBuilder.getInstance().
                setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("user", user)
                .getResponse();
    }

    @Override
    public ResponseBodyEntity updateUser(UserProfile user) {
        UserProfile persistantUser = userDao.getUser(user.getId());

        if (persistantUser == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("user.unknownOrDeleted", null))
                    .getResponse();
        }

        // Check if user email is unique
        if (!persistantUser.getEmail().toLowerCase().equals(user.getEmail().toLowerCase())) {
            if (userDao.getUser(user.getEmail()) != null) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("email", this.getMessageBasedOnLanguage("user.emailTaken", null))
                        .getResponse();
            }
        }

        Long msisdnLength = (Long) settingsEngine.getFirstLevelSetting("MSISDN_LENGTH");
        if (user.getMobileNumber().length() != msisdnLength) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("mobileNumber", this.getMessageBasedOnLanguage("user.msisdnLength", new Object[]{msisdnLength}))
                    .getResponse();
        }

        // Check if mobile number is unique
        if (persistantUser.getMobileNumber() != null && !persistantUser.getMobileNumber().equals(user.getMobileNumber())) {
            if (userDao.filterByMobileNumber(user.getMobileNumber()) != null) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("mobileNumber", this.getMessageBasedOnLanguage("user.msisdnTaken", null))
                        .getResponse();
            }
        }

        // Forbid login user from changing his joined groups
        if (Objects.equals(((UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(), persistantUser.getId())) {
            if (!(user.getGroupCollection().containsAll(persistantUser.getGroupCollection())
                    && persistantUser.getGroupCollection().containsAll(user.getGroupCollection()))) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.ALERT)
                        .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("user.changeGroupSet", null))
                        .addHttpResponseEntityData("user", this.getMessageBasedOnLanguage("user.changeGroupSet", null))
                        .getResponse();
            }
        }

        userProfileNotificationEventService.deleteByUser(user);

        persistantUser.setEmail(user.getEmail().toLowerCase());
        persistantUser.setName(user.getName());
        persistantUser.setJobTitle(user.getJobTitle());
        persistantUser.setMobileNumber(user.getMobileNumber());
        persistantUser.setGroupCollection(user.getGroupCollection());
        persistantUser.setEnabled(user.isEnabled());
        for (UserProfileNotificationEvent userProfileNotificationEvent : user.getUserProfileNotificationEventCollection()) {
            userProfileNotificationEvent.setUserProfile(user);
            userProfileNotificationEvent.setEnabled(true);
        }
        persistantUser.setUserProfileNotificationEventCollection(user.getUserProfileNotificationEventCollection());

        if (persistantUser.isEnabled() && persistantUser.getDeletedDate() == null) {
            List<UserProfileNotificationEvent> userProfileNotifications = persistantUser.getUserProfileNotificationEventCollection();
            List<String> notifications = new ArrayList<>();
            for (UserProfileNotificationEvent notification : userProfileNotifications) {
                notifications.add(notification.getNotificationEvents().getName().toLowerCase());
            }
            notificationEngine.updateUserNotification(persistantUser.getId(), persistantUser.getLanguage().getId(), notifications);
        } else {
            notificationEngine.deleteUser(persistantUser.getId());
        }

        HashMap<String, String> userLanguageMap = new HashMap<>();
        userLanguageMap.put("$USER_NAME$", user.getName());
        this.addNotification("UPDATE_USER", userLanguageMap, "/users");

        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("user", user)
                .getResponse();
    }

    @Override
    public ResponseBodyEntity deleteUser(Long id) {

        UserProfile persistantUser = userDao.getUser(id);
        if (persistantUser == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("user.notFound", null))
                    .getResponse();
        }

        // Forbid login user from deleting his account
        if (Objects.equals(((UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(), persistantUser.getId())) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ALERT)
                    .addHttpResponseEntityData("user", this.getMessageBasedOnLanguage("user.cantDelete", null))
                    .getResponse();
        }

        // Prohibit deletion of default users 
        if (id == 1 || id == 2) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ALERT)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("user.defaultUsers", null))
                    .getResponse();
        }

        if (persistantUser.getDeletedDate() != null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("user.alreadyDeleted", null))
                    .getResponse();
        }
        persistantUser.setDeletedDate(new Date());
        notificationEngine.deleteUser(persistantUser.getId());

        HashMap<String, String> userLanguageMap = new HashMap<>();
        userLanguageMap.put("$USER_NAME$", persistantUser.getName());
        this.addNotification("DELETE_USER", userLanguageMap, null);

        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("user.deletedSuccess", null))
                .getResponse();
    }

    @Override
    public void setAccountLocked(String email, boolean locked) {
        UserProfile user = userDao.getUser(email.toLowerCase());
        if (user != null) {
            user.setAccountLocked(locked);
        }
    }

    @Override
    public UserAttempt getUserAttemptCollection(String email) {
        UserAttempt userAttempt = null;
        UserProfile user = userDao.getUser(email.toLowerCase());
        if (user != null) {
            userAttempt = user.getUserAttempt();
        }
        return userAttempt;
    }

    @Override
    public boolean isUniqueFromOther(UserProfile userProfile) {
        UserProfile user = userDao.getUser(userProfile.getEmail().toLowerCase());
        if (user == null) {
            return true;
        } else if (!user.getId().equals(userProfile.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public ResponseBodyEntity updateUserLanguage(Long languageId) {
        UserProfile persistantUser = getAuthenticatedUser();
        Language language = languageService.getLanguage(languageId);
        persistantUser.setLanguage(language);
        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("user", persistantUser)
                .getResponse();
    }

    @Override
    public ResponseBodyEntity updateUserSettings(UserProfile user) {
        UserProfile persistantUser = userDao.getUser(getAuthenticatedUser().getId());

        if (persistantUser == null) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.ENTITY_NOT_FOUND)
                    .setHttpResponseEntityResultDescription(this.getMessageBasedOnLanguage("user.unknownOrDeleted", null))
                    .getResponse();
        }

        Long msisdnLength = (Long) settingsEngine.getFirstLevelSetting("MSISDN_LENGTH");
        if (user.getMobileNumber().length() != msisdnLength) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("mobileNumber", this.getMessageBasedOnLanguage("user.msisdnLength", new Object[]{msisdnLength}))
                    .getResponse();
        }

        // Check if mobile number is unique
        if (persistantUser.getMobileNumber() != null && !persistantUser.getMobileNumber().equals(user.getMobileNumber())) {
            if (userDao.filterByMobileNumber(user.getMobileNumber()) != null) {
                return ResponseBuilder.getInstance()
                        .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                        .addHttpResponseEntityData("mobileNumber", this.getMessageBasedOnLanguage("user.msisdnTaken", null))
                        .getResponse();
            }
        }

        persistantUser.setName(user.getName());
        persistantUser.setMobileNumber(user.getMobileNumber());
        persistantUser.setLanguage(user.getLanguage());

        return ResponseBuilder.getInstance()
                .setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("user", persistantUser)
                .getResponse();
    }

    @Override
    public ResponseBodyEntity changeUserPassword(UserProfile user, UserProfilePasswordValidator userProfilePasswordValidator) {

        UserProfile persisteUser = userService.toUser(getAuthenticatedUser().getId());
        // Check if mobile number is unique
        if (!passwordEncoder.matches(user.getPassword(), persisteUser.getPassword())) {
            return ResponseBuilder.getInstance()
                    .setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR)
                    .addHttpResponseEntityData("password", this.getMessageBasedOnLanguage("user.incorrectPassword", null))
                    .getResponse();
        }

        persisteUser.setPassword(passwordEncoder.encode(userProfilePasswordValidator.getNewPassword()));

        return ResponseBuilder.getInstance().
                setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("user", persisteUser)
                .getResponse();
    }

    @Override
    public void addNotification(WebNotifications webNotifcation) {
        webNotificationsDao.add(webNotifcation);
    }

    @Override
    public ResponseBodyEntity getWebNotifications(long userID, boolean all) {
        return ResponseBuilder.getInstance().
                setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("notifications", webNotificationsDao.getAll(userID, all))
                .getResponse();
    }

    @Override
    public ResponseBodyEntity getCountWebNotifications(long userID) {
        return ResponseBuilder.getInstance().
                setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
                .addHttpResponseEntityData("notifications", webNotificationsDao.getCountAll(userID))
                .getResponse();
    }

    @Override
    public void updateNotSeen(long userID) {
        webNotificationsDao.updateNotSeen(userID);
    }
}
