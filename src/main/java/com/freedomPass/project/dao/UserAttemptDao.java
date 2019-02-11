
package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserAttempt;
import java.util.List;

public interface UserAttemptDao {

    void initAttempt(UserAttempt userAttempt);

    UserAttempt findById(Long userId);

    List<UserAttempt> getByAccountLocked();
}
