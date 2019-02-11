package com.freedomPass.project.service;

import com.freedomPass.api.commons.utils.Utils;
import com.freedomPass.api.engine.SettingsEngine;
import com.freedomPass.api.engine.UsersEngine;
import com.freedomPass.project.dao.UserAttemptDao;
import com.freedomPass.project.model.UserAttempt;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userAttemptService")
@Transactional
public class UserAttemptServiceImpl extends AbstractService implements UserAttemptService {

    @Autowired
    UserService userService;

    @Autowired
    UserAttemptDao userAttempDao;

    @Autowired
    SettingsEngine settingsEngine;

    @Autowired
    UsersEngine usersEngine;

    @Override
    public void updateAttempts(String email) {
        Long numberOfSecBetweenAttempts = (Long) settingsEngine.getFirstLevelSetting("NUMBER_OF_SECONDS_BETWEEN_ATTEMPTS");
        Long numberOfAttempts = (Long) settingsEngine.getFirstLevelSetting("NUMBER_OF_ATTEMPTS_PER_LOGIN_FAIL");

        UserAttempt userAttempt = userService.getUserAttemptCollection(email);
        if (userAttempt != null) {
            if (Utils.getTimeDiffSec(userAttempt.getLastModified(), new Date()) < numberOfSecBetweenAttempts) {
                userAttempt.setAttempt(userAttempt.getAttempt() + 1);
            } else {
                userAttempt.setAttempt(1);
            }

            userAttempt.setLastModified(new Date());
            if (userAttempt.getAttempt() >= numberOfAttempts) {
                userAttempt.setAttempt(0);
                userService.setAccountLocked(email, false);
                usersEngine.addNewLockedAccount(userAttempt);
            }
        }
    }

    @Override
    public void resetAttempts(String email) {
        UserAttempt userAttempt = userService.getUserAttemptCollection(email);
        if (userAttempt != null) {
            userAttempt.setAttempt(0);
        }
    }

    @Override
    public void updateLastModified(String email) {
        UserAttempt userAttempt = userService.getUserAttemptCollection(email);
        if (userAttempt != null) {
            userAttempt.setLastModified(new Date());
        }
    }

    @Override
    public Date getAccountUnLockedDate(String email) {
        return userService.getUserAttemptCollection(email).getLastModified();
    }

    @Override
    public List<UserAttempt> getByAccountLocked() {
        return userAttempDao.getByAccountLocked();
    }

}
