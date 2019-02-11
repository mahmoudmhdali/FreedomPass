package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.DropDownValues;
import com.freedomPass.project.model.SettingsMapping;
import java.util.List;
import java.util.Map;

public interface SettingsMappingService {

    List<SettingsMapping> getSettingsDetails(long setting_id, String CategoryName, int isAdmin, String lang_prefix);

    List<String> getAutoIncColumns(int column_id);

    List<String> getUniqueValueColumns(int unique_flag, int column_id);

    List<String> getMandatoryColumns(int mandatory_value, int column_id);

    List<String> getColumnsOfSpecificType(String type, int column_id);

    String getSubTableName(int columnID, String columnName);

    List<String> getQueryText(long columnID);

    List<String> getRelatedColName(int columnID);

    List<DropDownValues> getCategories(String lang_prefix);

    List<String> getRelatedAutoIDColName(long columnID);

    List<String> getColumnNameFromID(List<Long> ListOfColId);

    List<Long> getColumnIDFromName(List<String> ListOfColName);

    Map<String, String> getMandatoryColumnsLabel(int mandatory_value, int column_id);

    Map<String, String> getUniqueValueColumnsLabel(int unique_flag, int column_id);

    Map<String, String> getColumnsOfSpecificTypeLabel(String type, int column_id);

    Map<String, String> getAutoIncColumnsLabel(int column_id);

}
