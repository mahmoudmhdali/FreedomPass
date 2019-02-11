package com.freedomPass.project.service;

import com.freedomPass.api.commons.utils.Utils;
import com.freedomPass.api.engine.SettingsEngine;
import com.freedomPass.project.dao.SettingsDao;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SettingsService")
@Transactional
public class SettingsServiceImpl extends AbstractService implements SettingsService {

    @Autowired
    SettingsDao SettingDao;

    @Autowired
    GetSettingsService getsettingsservice;

    @Autowired
    Utils utils;
    
    @Autowired
    SettingsEngine settingsEngine; 

    public String selectAllSettings() {
        return SettingDao.SelectAllSettings();
    }

    public ResponseBodyEntity addSetting(String Setting, int IsAdmin, String Source, String lang_prefix) {
        return SettingDao.AddEditSetting(Setting, 1, IsAdmin, Source, lang_prefix);
    }

    public ResponseBodyEntity editSetting(String Setting, int IsAdmin, String Source, String lang_prefix) {
        ResponseBodyEntity resp = SettingDao.AddEditSetting(Setting, 2, IsAdmin, Source, lang_prefix);
        settingsEngine.loadSettings();
        return resp;
    }

    public ResponseBodyEntity addSubSetting(String Setting, int IsAdmin, String lang_prefix) {
        return SettingDao.AddEditSubSetting(Setting, 1, IsAdmin, lang_prefix);
    }

    public ResponseBodyEntity editSubSetting(String Setting, int IsAdmin, String lang_prefix) {
        return SettingDao.AddEditSubSetting(Setting, 2, IsAdmin, lang_prefix);
    }

    public ResponseBodyEntity deleteSubSetting(String Setting, int IsAdmin, String lang_prefix) {
        return SettingDao.AddEditSubSetting(Setting, 3, IsAdmin, lang_prefix);
    }

    public ResponseEntity<?> getSettings(Long ResponseFormat, Long RequestLevel, String MainParameter, String Parameters, String Conditions, String Values, int IsAdmin) {
        return SettingDao.getSettings(ResponseFormat, RequestLevel, MainParameter, Parameters, Conditions, Values, IsAdmin);
    }

    public String[] selectSettings(String[] keys) {
        return SettingDao.SelectSettings(keys);
    }
}
