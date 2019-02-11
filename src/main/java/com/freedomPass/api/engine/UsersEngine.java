package com.freedomPass.api.engine;

import com.freedomPass.project.model.UserAttempt;
import com.freedomPass.project.service.UserAttemptService;
import com.freedomPass.project.service.UserService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UsersEngine {

    public boolean initialized = false;

    private List<UserAttempt> lockedUsersList = new ArrayList<>();

    private List<UserAttempt> usersToRemoveFromList = new ArrayList<>();

    @Autowired
    UserAttemptService userAttemptService;

    @Autowired
    UserService userService;

    @Autowired
    SettingsEngine settingsEngine;

    private final Calendar calendar = Calendar.getInstance();

    @Scheduled(fixedDelay = 86400000, initialDelay = 30000)
    public synchronized void init() {
        if (!initialized) {
            this.loadLockedUsers();
        }
    }

    public void loadLockedUsers() {
        lockedUsersList.clear();
        lockedUsersList = userAttemptService.getByAccountLocked();
        initialized = true;
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 35000)
    public synchronized void unlockUsers() {
        long lockAccountDuration = (Long) settingsEngine.getFirstLevelSetting("LOCK_ACCOUNT_DURATION");
        for (UserAttempt userAttempt : lockedUsersList) {
            calendar.setTime(userAttempt.getLastModified());
            calendar.add(Calendar.MINUTE, (int) lockAccountDuration);
            if (new Date().compareTo(calendar.getTime()) >= 0) {
                userService.setAccountLocked(userAttempt.getUserProfileId().getEmail(), true);
                usersToRemoveFromList.add(userAttempt);
            }
        }
        for (UserAttempt userAttempt : usersToRemoveFromList) {
            lockedUsersList.remove(userAttempt);
        }
        usersToRemoveFromList.clear();
    }
    
    public void addNewLockedAccount(UserAttempt userAttempt) {
        lockedUsersList.add(userAttempt);
    }
    
    public List<UserAttempt> getLockedAccounts() {
        return lockedUsersList;
    }

}
