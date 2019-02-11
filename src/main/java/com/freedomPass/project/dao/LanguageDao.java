package com.freedomPass.project.dao;

import com.freedomPass.project.model.Language;
import java.util.List;

public interface LanguageDao {

    List<Language> getLanguages();

    Language getLanguage(Long id);

    Language getLanguageByPrefix(String code);
}
