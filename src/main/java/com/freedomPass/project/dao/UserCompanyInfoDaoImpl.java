package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserCompanyInfo;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userCompanyInfoDao")
public class UserCompanyInfoDaoImpl extends AbstractDao<Long, UserCompanyInfo> implements UserCompanyInfoDao {

    @Override
    public List<UserCompanyInfo> getUserCompanyInfos() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<UserCompanyInfo> userCompanyInfos = (List<UserCompanyInfo>) criteria.list();
        for (UserCompanyInfo userCompanyInfo : userCompanyInfos) {
            Hibernate.initialize(userCompanyInfo.getUserCompanyInfoImagesCollection());
            Hibernate.initialize(userCompanyInfo.getUserCompanyInfoLocationsCollection());
        }
        return userCompanyInfos;
    }

    @Override
    public UserCompanyInfo getUserCompanyInfo(Long id) {
        UserCompanyInfo userCompanyInfo = getByKey(id);
        if (userCompanyInfo == null || userCompanyInfo.getDeletedDate() != null) {
            return null;
        }
        Hibernate.initialize(userCompanyInfo.getUserCompanyInfoImagesCollection());
        Hibernate.initialize(userCompanyInfo.getUserCompanyInfoLocationsCollection());
        return userCompanyInfo;
    }

}
