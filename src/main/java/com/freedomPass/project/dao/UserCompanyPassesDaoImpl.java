package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserCompanyPasses;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userCompanyPassesDao")
public class UserCompanyPassesDaoImpl extends AbstractDao<Long, UserCompanyPasses> implements UserCompanyPassesDao {

    @Override
    public List<UserCompanyPasses> getUserCompanyPasses() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<UserCompanyPasses> userCompanyPasses = (List<UserCompanyPasses>) criteria.list();
        return userCompanyPasses;
    }

    @Override
    public UserCompanyPasses getUserCompanyPasse(Long id) {
        UserCompanyPasses userCompanyPasse = getByKey(id);
        if (userCompanyPasse == null || userCompanyPasse.getDeletedDate() != null) {
            return null;
        }
        return userCompanyPasse;
    }

}
