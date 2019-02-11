/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.converter;

import com.freedomPass.project.model.Language;
import com.freedomPass.project.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LanguageConverter implements Converter<Object, Language> {

    @Autowired
    LanguageService languageService;

    /**
     * Gets Roles by Id
     *
     * @param element
     * @return
     * @see
     * org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public Language convert(Object element) {
        if (element instanceof String) {
            Long id = Long.parseLong((String) element);
            Language language = languageService.getLanguage(id);
            return language;
        }
        return (Language) element;
    }
}
