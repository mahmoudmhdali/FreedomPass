package com.freedomPass.project.dao;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.commons.utils.Utils;
import com.freedomPass.project.helpermodel.UserProfileCredentials;
import com.freedomPass.project.model.DashBoard;
import com.freedomPass.project.model.PagesLabels;
import com.freedomPass.project.model.UserProfile;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("dashboardDao")
public class DashBoardDaoImpl extends AbstractDao<Long, DashBoard> implements DashBoardDao {

    @Autowired
    ContextHolder context;

    @Autowired
    PagesLabelsDao pageslabelsDao;

    @Override
    public List<DashBoard> getCounters() throws Exception {
        UserProfileCredentials u = new UserProfileCredentials();
        UserProfile user = u.getAuthenticatedUser();
        Criteria criteria = createEntityCriteria()
                .addOrder(Order.asc("itemType"))
                .add(Restrictions.eq("enabled", 1));
        String dbDriver = context.getEnvironment().getProperty("jdbc.driverClassName");
        List<DashBoard> dashboard = (List<DashBoard>) criteria.list();

        for (DashBoard d : dashboard) {
            String jsonArray = Utils.Call_PROC_SELECTANYQUERY_TOJSON(d.getquery(), getSession(), dbDriver);

            List<PagesLabels> pageslabels = pageslabelsDao.getLabels(1L, d.getId(), user.getLanguage().getId());
            if (pageslabels != null) {
                for (PagesLabels pl : pageslabels) {
                    if (pl.getlabellevel() == 1) {
                        d.settitle(pl.getlabel());
                    } else if (pl.getlabellevel() == 2) {
                        d.setsubTitle(pl.getlabel());
                    } else if (pl.getlabellevel() == 3) {
                        jsonArray = jsonArray.replace(pl.getindexlegend(), pl.getlabel());

                    }
                }

            } else {
                d.settitle("");
                d.setsubTitle("");
            }
            d.setCounterValueBackend(jsonArray);

        }

        return dashboard;
    }

    @Override
    public List<DashBoard> getCountersByType(Long id) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("itemType", id.toString()));
        List<DashBoard> d = (List<DashBoard>) criteria.list();
        if (d == null) {
            return null;
        }
        return d;
    }

    @Override
    public DashBoard getCountersByKey(String key) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("itemkey", key));
        DashBoard d = (DashBoard) criteria.uniqueResult();
        if (d == null) {
            return null;
        }
        return d;
    }

}
