package com.freedomPass.project.dao;

import com.freedomPass.api.commons.Logger;
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
        try {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.isNull("deletedDate"));
            List<UserOutletOffer> userOutletOffers = (List<UserOutletOffer>) criteria.list();
            for (UserOutletOffer userOutletOffer : userOutletOffers) {
                Hibernate.initialize(userOutletOffer.getOutletOfferType());
                Hibernate.initialize(userOutletOffer.getUserOutletInfo());
                Hibernate.initialize(userOutletOffer.getUserOutletOfferImagesCollection());
            }
            return userOutletOffers;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserOutletOfferDao 1 on API [" + ex.getMessage() + "]", "", "");
        }
        return null;
    }

    @Override
    public UserOutletOffer getUserOutletOffer(Long id) {
        try {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.eq("id", id))
                    .add(Restrictions.isNull("deletedDate"));
            UserOutletOffer userOutletOffer = (UserOutletOffer) criteria.uniqueResult();
            if (userOutletOffer == null || userOutletOffer.getDeletedDate() != null) {
                return null;
            }
            Hibernate.initialize(userOutletOffer.getUserOutletOfferImagesCollection());
            return userOutletOffer;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserOutletInfoImagesDao 2 on API [" + ex.getMessage() + "]", id, "");
        }
        return null;
    }

    @Override
    public UserOutletOffer getUserOutletOfferByName(String name) {
        try {
            Criteria criteria = createEntityCriteria()
                    .add(Restrictions.eq("name", name))
                    .add(Restrictions.isNull("deletedDate"));
            UserOutletOffer userOutletOffer = (UserOutletOffer) criteria.uniqueResult();
            Hibernate.initialize(userOutletOffer.getUserOutletOfferImagesCollection());
            return userOutletOffer;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserOutletOfferDao 3 on API [" + ex.getMessage() + "]", name, "");
        }
        return null;
    }

    @Override
    public List<UserOutletOffer> getUserOutletOffersByType(Long type) {
        try {
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
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserOutletOfferDao 4 on API [" + ex.getMessage() + "]", type, "");
        }
        return null;
    }

    @Override
    public List<UserOutletOffer> getUserOutletOffersByOutletId(Long id) {
        try {
            Criteria criteria = createEntityCriteria()
                    .createAlias("userOutletInfo", "outlet")
                    .add(Restrictions.eq("outlet.id", id))
                    .add(Restrictions.isNull("deletedDate"));
            List<UserOutletOffer> userOutletOffers = (List<UserOutletOffer>) criteria.list();
            for (UserOutletOffer userOutletOffer : userOutletOffers) {
                Hibernate.initialize(userOutletOffer.getUserOutletOfferImagesCollection());
            }
            return userOutletOffers;
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserOutletOfferDao 5 on API [" + ex.getMessage() + "]", id, "");
        }
        return null;
    }

    @Override
    public OffersPagination getOffersPagination(int pageNumber, int maxRes) {
        try {
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
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserOutletOfferDao 6 on API [" + ex.getMessage() + "]", "Page Number: " + pageNumber + ". Max result: " + maxRes, "");
        }
        return null;
    }

    @Override
    public void addUser(UserOutletOffer userOutletOffer) {
        try {
            save(userOutletOffer);
        } catch (Exception ex) {
            Logger.ERROR("1- Error UserOutletOfferDao 7 on API [" + ex.getMessage() + "]", userOutletOffer, "");
        }
    }

}
