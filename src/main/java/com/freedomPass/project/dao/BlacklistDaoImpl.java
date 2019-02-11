package com.freedomPass.project.dao;

import com.freedomPass.project.helpermodel.UserProfileCredentials;
import com.freedomPass.project.model.Blacklist;
import com.freedomPass.project.model.UserProfile;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("blacklistDao")
public class BlacklistDaoImpl extends AbstractDao<Long, Blacklist> implements BlacklistDao {

    @Autowired
    @Qualifier("userDao")
    UserDao userDao;

    @SuppressWarnings("unchecked")
    @Override
    public List<Blacklist> getBlacklists(String source) {
        UserProfile LoggedInUser;
        if (source.equals("0")) {
            UserProfileCredentials u = new UserProfileCredentials();
            LoggedInUser = u.getAuthenticatedUser();
        } else {
            LoggedInUser = userDao.getUser(Long.valueOf(2));
        }

        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("bwflag", false))
                .add(Restrictions.eq("userProfileId", LoggedInUser));
        List<Blacklist> blacklists = (List<Blacklist>) criteria.list();

        for (Blacklist blacklist : blacklists) {
            Hibernate.initialize(blacklist.getuserProfileId());
        }
        return blacklists;
    }

    @Override
    public Blacklist getBlacklist(Long id) {
        Blacklist blacklist = getByKey(id);
        if (blacklist == null) {
            return null;
        }

        return blacklist;
    }

    @Override
    public Blacklist filterByMobileNumber(String mobileNumber) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("msisdn", mobileNumber));

        Blacklist blacklist = (Blacklist) criteria.uniqueResult();
        if (blacklist != null) {
            Hibernate.initialize(blacklist.getuserProfileId());
        }

        return blacklist;
    }

    @Override
    public void addBlacklist(Blacklist blacklist) {
        persist(blacklist);
    }

    @Override
    public void deleteBlacklist(Blacklist blacklist) {
        delete(blacklist);
    }
}
