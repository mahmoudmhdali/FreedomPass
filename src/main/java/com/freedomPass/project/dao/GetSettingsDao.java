/**
 *
 */
package com.freedomPass.project.dao;

import java.util.List;

public interface GetSettingsDao {

    public String getSettings_String(long requestLevel, String mainParameter, String parameters, String conditions, String values, int isAdmin);

    public boolean getSettings_Boolean(long requestLevel, String mainParameter, String parameters, String conditions, String values);

    public List<Object> getSettings_List(long requestLevel, String mainParameter, String parameters, String conditions, String values, int isAdmin);
}
