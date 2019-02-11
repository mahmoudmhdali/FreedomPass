package com.freedomPass.project.helpermodel;

import com.freedomPass.project.model.UserProfile;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserProfileCredentials {

    private String email;

    private String password;
    
    private boolean rememberMe;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public UserProfile getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) { // Access is done from anynomous user (no login was made)
            if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                UserProfile userProfile = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return userProfile;
            }
        }
        return null;
    }

}
