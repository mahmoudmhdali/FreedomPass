package com.freedomPass.project.dao;

import com.freedomPass.project.helpermodel.OffersPagination;
import com.freedomPass.project.model.UserOutletOffer;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userOutletOfferDao")
public class UserOutletOfferDaoImpl extends AbstractDao<Long, UserOutletOffer> implements UserOutletOfferDao {

    @Override
    public List<UserOutletOffer> getUserOutletOffers() {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.isNull("deletedDate"));
        List<UserOutletOffer> userOutletOffers = (List<UserOutletOffer>) criteria.list();
        for (UserOutletOffer userOutletOffer : userOutletOffers) {
            Hibernate.initialize(userOutletOffer.getOutletOfferType());
            Hibernate.initialize(userOutletOffer.getUserOutletInfo());
            Hibernate.initialize(userOutletOffer.getUserOutletOfferImagesCollection());
        }
        return userOutletOffers;
    }

    @Override
    public UserOutletOffer getUserOutletOffer(Long id) {
        UserOutletOffer userOutletOffer = getByKey(id);
        if (userOutletOffer == null || userOutletOffer.getDeletedDate() != null) {
            return null;
        }
        Hibernate.initialize(userOutletOffer.getUserOutletOfferImagesCollection());
        return userOutletOffer;
    }

    @Override
    public UserOutletOffer getUserOutletOfferByName(String name) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("name", name))
                .add(Restrictions.isNull("deletedDate"));
        UserOutletOffer userOutletOffer = (UserOutletOffer) criteria.uniqueResult();
        Hibernate.initialize(userOutletOffer.getUserOutletOfferImagesCollection());
        return userOutletOffer;
    }

    @Override
    public List<UserOutletOffer> getUserOutletOffersByType(Long type) {
        Criteria criteria = createEntityCriteria()
                .createAlias("outletOfferType", "type")
                .add(Restrictions.eq("type.id", type))
                .add(Restrictions.isNull("deletedDate"));
        List<UserOutletOffer> userOutletOffers = (List<UserOutletOffer>) criteria.list();
        for (UserOutletOffer userOutletOffer : userOutletOffers) {
            Hibernate.initialize(userOutletOffer.getOutletOfferType());
            Hibernate.initialize(userOutletOffer.getUserOutletInfo());
            Hibernate.initialize(userOutletOffer.getUserOutletOfferImagesCollection());
        }
        return userOutletOffers;
    }

    @Override
    public List<UserOutletOffer> getUserOutletOffersByOutletId(Long id) {
        Criteria criteria = createEntityCriteria()
                .createAlias("userOutletInfo", "outlet")
                .add(Restrictions.eq("outlet.id", id))
                .add(Restrictions.isNull("deletedDate"));
        List<UserOutletOffer> userOutletOffers = (List<UserOutletOffer>) criteria.list();
        for (UserOutletOffer userOutletOffer : userOutletOffers) {
            Hibernate.initialize(userOutletOffer.getUserOutletOfferImagesCollection());
        }
        return userOutletOffers;
    }

    @Override
    public OffersPagination getOffersPagination(int pageNumber, int maxRes) {
        Criteria criteria = createEntityCriteria();
        criteria.addOrder(Order.asc("name"));
        criteria.add(Restrictions.isNull("deletedDate"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  // To avoid duplicates.
        criteria.setProjection(Projections.rowCount());
        Number totalResults = (Number) criteria.uniqueResult();
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        criteria.setFirstResult((pageNumber - 1) * maxRes);
        criteria.setMaxResults(maxRes);
        List<UserOutletOffer> offers = (List<UserOutletOffer>) criteria.list();
        for (UserOutletOffer userOutletOffer : offers) {
            Hibernate.initialize(userOutletOffer.getUserOutletOfferImagesCollection());
        }
        int currentPage = pageNumber;
        int maxPages = (int) Math.ceil((double) ((double) totalResults.intValue() / (double) maxRes));
        OffersPagination offersPagination = new OffersPagination(maxPages, currentPage, totalResults.intValue(), offers);
        return offersPagination;
    }

    @Override
    public void addUser(UserOutletOffer userOutletOffer) {
        save(userOutletOffer);
    }

}
