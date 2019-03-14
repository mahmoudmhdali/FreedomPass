package com.freedomPass.project.dao;

import com.freedomPass.project.model.OutletOfferType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("outletOfferTypeDao")
public class OutletOfferTypeDaoImpl extends AbstractDao<Long, OutletOfferType> implements OutletOfferTypeDao {

    @Override
    public List<OutletOfferType> getOutletOfferTypes() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<OutletOfferType> outletOfferTypes = (List<OutletOfferType>) criteria.list();
        return outletOfferTypes;
    }

    @Override
    public OutletOfferType getOutletOfferType(Long id) {
        return getByKey(id);
    }

}
