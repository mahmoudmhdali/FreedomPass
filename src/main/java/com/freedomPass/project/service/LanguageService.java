package com.freedomPass.project.service;

import com.freedomPass.project.model.Language;
import java.util.List;

public interface LanguageService {

    List<Language> getLanguages();

    Language getLanguage(Long id);

    Language getLanguageByPrefix(String code);

}
