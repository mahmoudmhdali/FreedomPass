package com.freedomPass.project.helpermodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freedomPass.project.model.validation.ValidPassword;
import org.hibernate.validator.constraints.NotBlank;

public class UserProfilePasswordValidator {

    @NotBlank(message = "validation.userProfile.passwordRequired")
    @ValidPassword
    @JsonIgnore
    private String newPassword;

    @NotBlank(message = "validation.userProfile.passwordRequired")
    @ValidPassword
    @JsonIgnore
    private String confirmPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public boolean matchPasswords ()
    {
        return newPassword.equals(confirmPassword);
    }
}
