package com.freedomPass.project.dao;

import com.freedomPass.project.model.OutletCategory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("outletCategoryDao")
public class OutletCategoryDaoImpl extends AbstractDao<Long, OutletCategory> implements OutletCategoryDao {

    @Override
    public List<OutletCategory> getOutletCategories() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<OutletCategory> outletCategories = (List<OutletCategory>) criteria.list();
        return outletCategories;
    }

}
