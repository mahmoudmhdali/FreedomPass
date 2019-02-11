/**
 *
 */
package com.freedomPass.project.service;

import com.freedomPass.project.dao.GetSettingsDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("GetSettingsService")
@Transactional
public class GetSettingsServiceImpl implements GetSettingsService {

    @Autowired
    GetSettingsDao GetSettingDao;

    public Object getSettings_String(long requestLevel, String mainParameter, String parameters, String conditions, String values, int isAdmin) {
        return GetSettingDao.getSettings_String(requestLevel, mainParameter, parameters, conditions, values, isAdmin);
    }

    public boolean getSettings_Boolean(long requestLevel, String mainParameter, String parameters, String conditions, String values) {
        return GetSettingDao.getSettings_Boolean(requestLevel, mainParameter, parameters, conditions, values);
    }

    public List<Object> getSettings_List(long requestLevel, String mainParameter, String parameters, String conditions, String values, int isAdmin) {
        return GetSettingDao.getSettings_List(requestLevel, mainParameter, parameters, conditions, values, isAdmin);
    }
}
