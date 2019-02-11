package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import org.springframework.http.ResponseEntity;

public interface SettingsService {

    String selectAllSettings();

    ResponseBodyEntity addSetting(String Setting, int IsAdmin, String Source, String lang_prefix);

    ResponseBodyEntity editSetting(String Setting, int IsAdmin, String Source, String lang_prefix);

    ResponseBodyEntity addSubSetting(String Setting, int IsAdmin, String lang_prefix);

    ResponseBodyEntity editSubSetting(String Setting, int IsAdmin, String lang_prefix);

    ResponseBodyEntity deleteSubSetting(String Setting, int IsAdmin, String lang_prefix);

    ResponseEntity getSettings(Long ResponseFormat, Long RequestLevel, String MainParameter, String Parameters, String Conditions, String Values, int IsAdmin);

    String[] selectSettings(String[] keys);

}
