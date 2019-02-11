/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.service;

import com.freedomPass.project.dao.LanguageDao;
import com.freedomPass.project.dao.SettingsMappingDao;
import com.freedomPass.project.helpermodel.DropDownValues;
import com.freedomPass.project.model.Language;
import com.freedomPass.project.model.SettingsMapping;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("settingsMappingService")
@Transactional
public class SettingsMappingServiceImpl extends AbstractService implements SettingsMappingService {

    @Autowired
    SettingsMappingDao SettingsMappingDao;

    @Autowired
    LanguageDao languageDao;

    @Override
    public List<SettingsMapping> getSettingsDetails(long setting_id, String CategoryName, int isAdmin, String lang_prefix) {

        List<SettingsMapping> bndl = SettingsMappingDao.getSettingDetails(setting_id, CategoryName, isAdmin, lang_prefix);

        return bndl;
    }

    @Override
    public List<String> getAutoIncColumns(int column_id) {

        List<String> AutoIncColumnNames = SettingsMappingDao.getAutoIncColumns(column_id);
        return AutoIncColumnNames;
    }

    @Override
    public Map<String, String> getAutoIncColumnsLabel(int column_id) {

        Map<String, String> AutoIncColumnNames = SettingsMappingDao.getAutoIncColumnsLabel(column_id);
        return AutoIncColumnNames;
    }

    @Override
    public List<String> getUniqueValueColumns(int unique_flag, int column_id) {
        List<String> UniqueValueColumns = SettingsMappingDao.getUniqueValueColumns(unique_flag, column_id);
        return UniqueValueColumns;
    }

    @Override
    public Map<String, String> getUniqueValueColumnsLabel(int unique_flag, int column_id) {
        Map<String, String> UniqueValueColumns = SettingsMappingDao.getUniqueValueColumnsLabel(unique_flag, column_id);
        return UniqueValueColumns;
    }

    @Override
    public List<String> getMandatoryColumns(int mandatory_value, int column_id) {
        List<String> MandatoryValueColumns = SettingsMappingDao.getMandatoryColumns(mandatory_value, column_id);
        return MandatoryValueColumns;
    }

    @Override
    public Map<String, String> getMandatoryColumnsLabel(int mandatory_value, int column_id) {
        Map<String, String> MandatoryValueColumns = SettingsMappingDao.getMandatoryColumnsLabel(mandatory_value, column_id);
        return MandatoryValueColumns;
    }

    @Override
    public List<String> getColumnsOfSpecificType(String type, int column_id) {
        List<String> SpecificTypeColumns = SettingsMappingDao.getColumnsOfSpecificType(type, column_id);
        return SpecificTypeColumns;
    }

    @Override
    public Map<String, String> getColumnsOfSpecificTypeLabel(String type, int column_id) {
        Map<String, String> SpecificTypeColumns = SettingsMappingDao.getColumnsOfSpecificTypeLabel(type, column_id);
        return SpecificTypeColumns;
    }

    @Override
    public String getSubTableName(int column_id, String columnName) {
        String SubTableName = SettingsMappingDao.getSubTableName(column_id, columnName);
        return SubTableName;
    }

    @Override
    public List<String> getQueryText(long columnID) {
        List<String> QueryText = SettingsMappingDao.getQueryText(columnID);
        return QueryText;
    }

    @Override
    public List<String> getRelatedColName(int columnID) {
        List<String> RelatedColName = SettingsMappingDao.getRelatedColName(columnID);
        return RelatedColName;
    }

    @Override
    public List<DropDownValues> getCategories(String lang_prefix) {
        Language l = languageDao.getLanguageByPrefix(lang_prefix);
        List<DropDownValues> Categories = SettingsMappingDao.getCategories(l.getId());
        return Categories;
    }

    @Override
    public List<String> getRelatedAutoIDColName(long columnID) {
        List<String> RelatedAutoIncColName = SettingsMappingDao.getRelatedAutoIDColName(columnID);
        return RelatedAutoIncColName;
    }

    @Override
    public List<String> getColumnNameFromID(List<Long> ListOfColId) {
        List<String> ListOfColNames = SettingsMappingDao.getColumnNameFromID(ListOfColId);
        return ListOfColNames;
    }

    @Override
    public List<Long> getColumnIDFromName(List<String> ListOfColName) {
        List<Long> ListOfID = SettingsMappingDao.getColumnIDFromName(ListOfColName);
        return ListOfID;
    }

}
