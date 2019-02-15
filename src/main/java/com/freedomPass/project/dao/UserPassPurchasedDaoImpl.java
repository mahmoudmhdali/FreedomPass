package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserPassPurchased;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userPassPurchasedDao")
public class UserPassPurchasedDaoImpl extends AbstractDao<Long, UserPassPurchased> implements UserPassPurchasedDao {

    @Override
    public List<UserPassPurchased> getUserPassPurchaseds() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<UserPassPurchased> userPassPurchased = (List<UserPassPurchased>) criteria.list();
        return userPassPurchased;
    }

    @Override
    public UserPassPurchased getUserPassPurchased(Long id) {
        UserPassPurchased userPassPurchased = getByKey(id);
        return userPassPurchased;
    }

}
