package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserCompanyInfo;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userCompanyInfoDao")
public class UserCompanyInfoDaoImpl extends AbstractDao<Long, UserCompanyInfo> implements UserCompanyInfoDao {

    @Override
    public List<UserCompanyInfo> getUserCompanyInfos() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<UserCompanyInfo> userCompanyInfos = (List<UserCompanyInfo>) criteria.list();
        return userCompanyInfos;
    }

    @Override
    public UserCompanyInfo getUserCompanyInfo(Long id) {
        UserCompanyInfo userCompanyInfo = getByKey(id);
        if (userCompanyInfo == null || userCompanyInfo.getDeletedDate() != null) {
            return null;
        }
        return userCompanyInfo;
    }

}
