/**
 *
 */
package com.freedomPass.project.service;

import java.util.List;

public interface GetSettingsService {

    public Object getSettings_String(long requestLevel, String mainParameter, String parameters, String conditions, String values, int isAdmin);

    public boolean getSettings_Boolean(long requestLevel, String mainParameter, String parameters, String conditions, String values);

    public List<Object> getSettings_List(long requestLevel, String mainParameter, String parameters, String conditions, String values, int isAdmin);

}
