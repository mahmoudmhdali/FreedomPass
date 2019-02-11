package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserAttempt;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userAttepmtDao")
public class UserAttepmtDaoImpl extends AbstractDao<Long, UserAttempt> implements UserAttemptDao {

    @Override
    public void initAttempt(UserAttempt userAttempt) {
        persist(userAttempt);
    }

    @Override
    public UserAttempt findById(Long userId) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("userProfileId", userId));
        UserAttempt userAttempt = (UserAttempt) criteria.uniqueResult();
        return userAttempt;
    }

    @Override
    public List<UserAttempt> getByAccountLocked() {
        Criteria criteria = createEntityCriteria()
                .createAlias("userProfileId", "user")
                .add(Restrictions.eq("user.accountNonLocked", false));
        List<UserAttempt> userAttempt = (List<UserAttempt>) criteria.list();
        return userAttempt;
    }

}
