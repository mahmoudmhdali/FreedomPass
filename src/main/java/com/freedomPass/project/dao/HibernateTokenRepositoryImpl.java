
package com.freedomPass.project.dao;

import com.freedomPass.project.model.PersistentLogin;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
@Repository("tokenRepositoryDao")
@Transactional
public class HibernateTokenRepositoryImpl extends AbstractDao<String, PersistentLogin> implements PersistentTokenRepository {

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());
        persist(persistentLogin);

    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            Criteria crit = createEntityCriteria();
            crit.add(Restrictions.eq("series", seriesId));
            PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();

            return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(),
                    persistentLogin.getToken(), persistentLogin.getLastUsed());
        } catch (Exception e) {
            return null;
        }
    } 

    @Override
    public void removeUserTokens(String username) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("username", username));
//        PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();
        List<PersistentLogin> ls = crit.list();
        System.out.println(ls.size());
        if (!ls.isEmpty()) {
            for (PersistentLogin p : ls) {
                System.out.println(p.getUsername());
                delete(p);
            }
        }
//        if (persistentLogin != null) {
//            logger.info("rememberMe was selected");
//            delete(persistentLogin);
//        }

    }

    @Override
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        PersistentLogin persistentLogin = getByKey(seriesId);
        persistentLogin.setToken(tokenValue);
        persistentLogin.setLastUsed(lastUsed);
        update(persistentLogin);
    }

}
