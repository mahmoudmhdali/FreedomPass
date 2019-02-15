package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletInfo;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userOutletInfoDao")
public class UserOutletInfoDaoImpl extends AbstractDao<Long, UserOutletInfo> implements UserOutletInfoDao {

    @Override
    public List<UserOutletInfo> getUserOutletInfos() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<UserOutletInfo> userOutletInfos = (List<UserOutletInfo>) criteria.list();
        return userOutletInfos;
    }

    @Override
    public UserOutletInfo getUserOutletInfo(Long id) {
        UserOutletInfo userOutletInfo = getByKey(id);
        if (userOutletInfo == null || userOutletInfo.getDeletedDate() != null) {
            return null;
        }
        return userOutletInfo;
    }

}
