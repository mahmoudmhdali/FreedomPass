package com.freedomPass.project.service;

import com.freedomPass.project.model.UserAttempt;
import java.util.Date;
import java.util.List;

public interface UserAttemptService {

    void updateAttempts(String email);

    void resetAttempts(String email);

    void updateLastModified(String email);

    Date getAccountUnLockedDate(String email);

    List<UserAttempt> getByAccountLocked();
}
