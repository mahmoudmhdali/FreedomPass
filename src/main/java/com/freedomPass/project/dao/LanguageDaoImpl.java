package com.freedomPass.project.dao;

import com.freedomPass.project.model.Language;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("languageDao")
public class LanguageDaoImpl extends AbstractDao<Long, Language> implements LanguageDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Language> getLanguages() {
        Criteria criteria = createEntityCriteria();
        List<Language> languages = (List<Language>) criteria.list();
        return languages;
    }

    @Override
    public Language getLanguage(Long id) {
        return getByKey(id);
    }

    @Override
    public Language getLanguageByPrefix(String code) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("prefix", code));
        Language l = (Language) criteria.uniqueResult();
        return l;
    }

}
