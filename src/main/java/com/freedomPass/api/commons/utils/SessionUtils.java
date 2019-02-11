/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.commons.utils;

import com.freedomPass.project.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

@Component
public class SessionUtils {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    public void expireUserSessions(String username) {
        persistentTokenRepository.removeUserTokens(username);
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            UserProfile userDetails = (UserProfile) principal;
            if (userDetails.getUsername().equals(username)) {
                for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                    information.expireNow();
                }
            }
        }
    }
}
