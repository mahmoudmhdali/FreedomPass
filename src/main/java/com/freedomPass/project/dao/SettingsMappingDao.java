package com.freedomPass.project.dao;

import com.freedomPass.project.helpermodel.DropDownValues;
import com.freedomPass.project.model.SettingsMapping;
import java.util.List;
import java.util.Map;

public interface SettingsMappingDao {

    List<SettingsMapping> getSettingDetails(long setting_id, String CategoryName, int isAdmin, String lang_prefix);

    List<String> getAutoIncColumns(int column_id);

    List<String> getUniqueValueColumns(int unique_flag, int column_id);

    List<String> getMandatoryColumns(int mandatory_value, int column_id);

    List<String> getColumnsOfSpecificType(String type, int column_id);

    String getSubTableName(int column_id, String columnName);

    List<String> getQueryText(long column_id);

    List<String> getRelatedColName(long column_id);

    List<DropDownValues> getCategories(long lang_id);

    List<String> getRelatedAutoIDColName(long column_id);

    List<String> getColumnNameFromID(List<Long> ListOfColId);

    List<Long> getColumnIDFromName(List<String> ListOfColNames);

    Map<String, String> getMandatoryColumnsLabel(int mandatory_value, int column_id);

    Map<String, String> getUniqueValueColumnsLabel(int unique_flag, int column_id);

    Map<String, String> getColumnsOfSpecificTypeLabel(String type, int column_id);

    Map<String, String> getAutoIncColumnsLabel(int column_id);

}
