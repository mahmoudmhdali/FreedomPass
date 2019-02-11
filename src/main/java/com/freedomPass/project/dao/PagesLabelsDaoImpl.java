package com.freedomPass.project.dao;

import com.freedomPass.project.model.PagesLabels;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("pagesLabelsDao")
public class PagesLabelsDaoImpl extends AbstractDao<Long, PagesLabels> implements PagesLabelsDao {

    @Autowired
    LanguageDao languageDao;

    @Override
    public List<PagesLabels> getLabels(Long pageid, Long labelid, Long langid) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("pageid", pageid))
                .add(Restrictions.eq("labelid", labelid))
                .add(Restrictions.eq("language", languageDao.getLanguage(langid)));

        List<PagesLabels> pl = (List<PagesLabels>) criteria.list();

        return pl;
    }

}
