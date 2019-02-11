package com.freedomPass.project.dao;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import org.springframework.http.ResponseEntity;

public interface SettingsDao {

    String SelectAllSettings();

    String[] SelectSettings(String[] keys);

    ResponseBodyEntity AddEditSetting(String Setting, int action_flag, int IsAdmin, String Source, String lang_prefix);

    ResponseBodyEntity AddEditSubSetting(String SubSetting, int action_flag, int IsAdmin, String lang_prefix);

    ResponseEntity getSettings(long ResponseFormat, long RequestLevel, String MainParameter, String Parameters, String Conditions, String Values, int IsAdmin);
}
