package com.freedomPass.project.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.commons.utils.Utils;
import com.freedomPass.project.helpermodel.DropDown;
import com.freedomPass.project.helpermodel.DropDownValues;
import com.freedomPass.project.helpermodel.TwoDStringObject;
import com.freedomPass.project.model.Language;
import com.freedomPass.project.model.SettingsMapping;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import oracle.jdbc.OracleTypes;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@JsonInclude(JsonInclude.Include.ALWAYS)
@Repository("settingsMappingDao")
public class SettingsMappingDaoImpl extends AbstractDao<Long, SettingsMapping> implements SettingsMappingDao {

    @Autowired

    private ContextHolder context;

    @Autowired
    LanguageDao languageDAO;

    @SuppressWarnings("unchecked")
    @Override
    public List<SettingsMapping> getSettingDetails(long setting_id, String CategoryName, int isAdmin, String lang_prefix) {
        String TableName = "tbl_settings";
        String dbDriver = context.getEnvironment().getProperty("jdbc.driverClassName");
        Query query = null;
//    	UserProfile user  = abstractController.getAuthenticatedUser();
//    	Locale locale = new Locale(user.getLanguage().getPrefix().toLowerCase());
        Language l = languageDAO.getLanguageByPrefix(lang_prefix);
        Session session1 = getSession();
        List<SettingsMapping> listOfBundlesMaps = Call_PROC_SELECTSETTINGSMAP(session1, setting_id, new Long(0), TableName, CategoryName, isAdmin, l.getId(), dbDriver);
        for (int i = 0; i < listOfBundlesMaps.size(); i++) {
            if (setting_id != 0) {
                //Initialize
                List<DropDown> DropDownOptions = new ArrayList<DropDown>();
                listOfBundlesMaps.get(i).setfieldOptions(DropDownOptions);

                // Dropdown
                if (listOfBundlesMaps.get(i).getFieldType().toLowerCase().equals("dropdown")) {
                    query = createSqlQuery(listOfBundlesMaps.get(i).getQueryText() + listOfBundlesMaps.get(i).getColumnId()).setResultTransformer(Transformers.aliasToBean(DropDown.class));
                    DropDownOptions = (List<DropDown>) query.list();
                    listOfBundlesMaps.get(i).setfieldOptions(DropDownOptions);
                }
                // Checkbox
                if (listOfBundlesMaps.get(i).getFieldType().toLowerCase().equals("checkbox")) {
                    if (listOfBundlesMaps.get(i).getColumnValue().toLowerCase().equals("1")) {
                        listOfBundlesMaps.get(i).setColumnValue("True");
                    } else if (listOfBundlesMaps.get(i).getColumnValue().toLowerCase().equals("0")) {
                        listOfBundlesMaps.get(i).setColumnValue("False");
                    }
                }
                // Accordion
                if (listOfBundlesMaps.get(i).getFieldType().toLowerCase().equals("accordion") || listOfBundlesMaps.get(i).getFieldType().toLowerCase().equals("datatable")) {
                    try {
                        if (listOfBundlesMaps.get(i).getColumnValue().toLowerCase().equals("1")) {
                            listOfBundlesMaps.get(i).setColumnValue("True");
                        } else if (listOfBundlesMaps.get(i).getColumnValue().toLowerCase().equals("0")) {
                            listOfBundlesMaps.get(i).setColumnValue("False");
                        }
                        String AccQuery = "";
                        if (listOfBundlesMaps.get(i).getQueryText().toUpperCase().contains(" WHERE ")) {
                            if (!listOfBundlesMaps.get(i).getrelatedColName().equals("") && !listOfBundlesMaps.get(i).getrelatedColName().equals("null") && !listOfBundlesMaps.get(i).getrelatedColName().equals(null)) {
                                AccQuery = listOfBundlesMaps.get(i).getQueryText() + " AND " + listOfBundlesMaps.get(i).getrelatedColName() + "=" + Long.toString(setting_id);
                            } else {
                                AccQuery = listOfBundlesMaps.get(i).getQueryText();
                            }

                        } else if (!listOfBundlesMaps.get(i).getrelatedColName().equals("") && !listOfBundlesMaps.get(i).getrelatedColName().equals("null") && !listOfBundlesMaps.get(i).getrelatedColName().equals(null)) {
                            AccQuery = listOfBundlesMaps.get(i).getQueryText() + " WHERE " + listOfBundlesMaps.get(i).getrelatedColName() + "=" + Long.toString(setting_id);
                        } else {
                            AccQuery = listOfBundlesMaps.get(i).getQueryText() + " WHERE 1=1";
                        }
                        String[] AccQueryList = AccQuery.split("WHERE");
//                            AccQuery = listOfBundlesMaps.get(i).getQueryText()  + " WHERE " + listOfBundlesMaps.get(i).getrelatedColName() + "=" + Long.toString(setting_id);
                        Session session = getSession();
                        List<SettingsMapping> listOfSUBBundlesMaps = Call_PROC_SELECTSETTINGSMAP(session, setting_id, listOfBundlesMaps.get(i).getColumnId(), TableName, "all", isAdmin, l.getId(), dbDriver);

                        for (int ii = 0; ii < listOfSUBBundlesMaps.size(); ii++) {
                            List<DropDown> SUBDropDownOptions = new ArrayList<DropDown>();
                            listOfSUBBundlesMaps.get(ii).setfieldOptions(SUBDropDownOptions);
                            // Dropdown
                            if (listOfSUBBundlesMaps.get(ii).getFieldType().toLowerCase().equals("dropdown")) {
                                AccQueryList[0] = AccQueryList[0].replace(listOfSUBBundlesMaps.get(ii).getColumnName(),
                                        listOfSUBBundlesMaps.get(ii).getColumnName() + " AS " + listOfSUBBundlesMaps.get(ii).getColumnName() + "_HIDDEN, (SELECT OPTIONNAME FROM SETTINGS_ALLDROPDOWNS WHERE COLUMNID="
                                        + Long.toString(listOfSUBBundlesMaps.get(ii).getColumnId()) + " AND OPTIONID=" + listOfSUBBundlesMaps.get(ii).getColumnName() + ") AS " + listOfSUBBundlesMaps.get(ii).getColumnName());
                                query = createSqlQuery(listOfSUBBundlesMaps.get(ii).getQueryText() + listOfSUBBundlesMaps.get(ii).getColumnId()).setResultTransformer(Transformers.aliasToBean(DropDown.class));
                                DropDownOptions = (List<DropDown>) query.list();
                                listOfSUBBundlesMaps.get(ii).setfieldOptions(DropDownOptions);
                                AccQuery = AccQueryList[0] + "WHERE" + AccQueryList[1];
                            }
                            // Checkbox
                            if (listOfSUBBundlesMaps.get(ii).getFieldType().toLowerCase().equals("checkbox")) {
                                if (listOfSUBBundlesMaps.get(ii).getColumnValue().toLowerCase().equals("1")) {
                                    listOfSUBBundlesMaps.get(ii).setColumnValue("True");
                                } else if (listOfSUBBundlesMaps.get(ii).getColumnValue().toLowerCase().equals("0")) {
                                    listOfSUBBundlesMaps.get(ii).setColumnValue("False");
                                }
                            }
//                                listOfSUBBundlesMaps.get(ii).setRelatedColumns(null);
                            listOfSUBBundlesMaps.get(ii).setQueryText(null);
                        }

                        String jsonArray = Utils.Call_PROC_SELECTANYQUERY_TOJSON(AccQuery, getSession(), dbDriver);
                        listOfBundlesMaps.get(i).setAccordionOptions(jsonArray);

                        listOfBundlesMaps.get(i).setRelatedFields(listOfSUBBundlesMaps);
                    } catch (Exception ex) {
                        Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                // Initialize
                List<DropDown> DropDownOptions = new ArrayList<DropDown>();
                listOfBundlesMaps.get(i).setfieldOptions(DropDownOptions);
                // Dropdown
                if (listOfBundlesMaps.get(i).getFieldType().toLowerCase().equals("dropdown")) {
                    query = createSqlQuery(listOfBundlesMaps.get(i).getQueryText() + listOfBundlesMaps.get(i).getColumnId()).setResultTransformer(Transformers.aliasToBean(DropDown.class));
                    DropDownOptions = (List<DropDown>) query.list();
                    listOfBundlesMaps.get(i).setfieldOptions(DropDownOptions);
                }
                // Checkbox
                if (listOfBundlesMaps.get(i).getFieldType().toLowerCase().equals("checkbox")) {
                    listOfBundlesMaps.get(i).setColumnValue("False");
                }
                // Accordion
                if (listOfBundlesMaps.get(i).getFieldType().toLowerCase().equals("accordion") || listOfBundlesMaps.get(i).getFieldType().toLowerCase().equals("datatable")) {
                    try {
                        listOfBundlesMaps.get(i).setColumnValue("False");
                        listOfBundlesMaps.get(i).setAccordionOptions("");
                        Session session = getSession();
                        List<SettingsMapping> listOfSUBBundlesMaps = Call_PROC_SELECTSETTINGSMAP(session, setting_id, listOfBundlesMaps.get(i).getColumnId(), TableName, "all", isAdmin, l.getId(), dbDriver);

                        for (int ii = 0; ii < listOfSUBBundlesMaps.size(); ii++) {
                            List<DropDown> SUBDropDownOptions = new ArrayList<DropDown>();
                            listOfSUBBundlesMaps.get(ii).setfieldOptions(SUBDropDownOptions);
                            // Dropdown
                            if (listOfSUBBundlesMaps.get(ii).getFieldType().toLowerCase().equals("dropdown")) {
                                query = createSqlQuery(listOfSUBBundlesMaps.get(ii).getQueryText() + listOfSUBBundlesMaps.get(ii).getColumnId()).setResultTransformer(Transformers.aliasToBean(DropDown.class));
                                DropDownOptions = (List<DropDown>) query.list();
                                listOfSUBBundlesMaps.get(ii).setfieldOptions(DropDownOptions);
                            }
                            // Checkbox
                            if (listOfSUBBundlesMaps.get(ii).getFieldType().toLowerCase().equals("checkbox")) {
                                if (listOfSUBBundlesMaps.get(ii).getColumnValue().toLowerCase().equals("1")) {
                                    listOfSUBBundlesMaps.get(ii).setColumnValue("True");
                                } else if (listOfSUBBundlesMaps.get(ii).getColumnValue().toLowerCase().equals("0")) {
                                    listOfSUBBundlesMaps.get(ii).setColumnValue("False");
                                }
                            }
//                                listOfSUBBundlesMaps.get(ii).setRelatedColumns(null);
                            listOfSUBBundlesMaps.get(ii).setQueryText(null);
                        }

                        listOfBundlesMaps.get(i).setRelatedFields(listOfSUBBundlesMaps);
                    } catch (Exception ex) {
                        Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            listOfBundlesMaps.get(i).setQueryText(null);
        }

        return listOfBundlesMaps;

    }

    @SuppressWarnings({"finally", "unchecked"})
    @Override
    public List<String> getAutoIncColumns(int column_id) {
        List<String> Resp = null;
        try {
            Criteria criteria = createEntityCriteria();
            criteria.setProjection(Projections.property("COLUMNNAME"));
            criteria.add(Restrictions.eq("AUTOINC", 1));
            criteria.add(Restrictions.eq("RELATEDCOLUMNS", column_id));
            criteria.addOrder(Order.asc("COLUMNID"));
            Resp = (List<String>) criteria.list();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    @Override
    public Map<String, String> getAutoIncColumnsLabel(int column_id) {
        Map<String, String> Resp = null;
        try {
            Criteria criteria = createEntityCriteria();
            ProjectionList projList = Projections.projectionList();
            projList.add(Projections.property("COLUMNNAME"), "StringOne");
            projList.add(Projections.property("LABELDISPLAY"), "StringTwo");
            criteria.setProjection(projList);

            criteria.add(Restrictions.eq("AUTOINC", 1));
            criteria.add(Restrictions.eq("RELATEDCOLUMNS", column_id));
            criteria.addOrder(Order.asc("COLUMNID"));
            criteria.setResultTransformer(Transformers.aliasToBean(TwoDStringObject.class));
            List<TwoDStringObject> objects = (List<TwoDStringObject>) criteria.list();
            Resp = objects.stream().collect(Collectors.toMap(TwoDStringObject::getStringOne, TwoDStringObject::getStringTwo));
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    @Override
    public List<String> getUniqueValueColumns(int unique_flag, int column_id) {
        List<String> Resp = null;
        try {
            Criteria criteria = createEntityCriteria();
            criteria.setProjection(Projections.property("COLUMNNAME"));
            criteria.add(Restrictions.eq("UNIQUEVALUE", unique_flag));
            criteria.add(Restrictions.eq("RELATEDCOLUMNS", column_id));
            criteria.addOrder(Order.asc("COLUMNID"));
            Resp = (List<String>) criteria.list();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    @Override
    public Map<String, String> getUniqueValueColumnsLabel(int unique_flag, int column_id) {
        Map<String, String> Resp = null;
        try {
            Criteria criteria = createEntityCriteria();
            ProjectionList projList = Projections.projectionList();
            projList.add(Projections.property("COLUMNNAME"), "StringOne");
            projList.add(Projections.property("LABELDISPLAY"), "StringTwo");
            criteria.setProjection(projList);

            criteria.add(Restrictions.eq("UNIQUEVALUE", unique_flag));
            criteria.add(Restrictions.eq("RELATEDCOLUMNS", column_id));
            criteria.addOrder(Order.asc("COLUMNID"));
            criteria.setResultTransformer(Transformers.aliasToBean(TwoDStringObject.class));
            List<TwoDStringObject> objects = (List<TwoDStringObject>) criteria.list();
            Resp = objects.stream().collect(Collectors.toMap(TwoDStringObject::getStringOne, TwoDStringObject::getStringTwo));
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    @Override
    public List<String> getMandatoryColumns(int mandatory_value, int column_id) {
        List<String> Resp = null;
        try {
            Criteria criteria = createEntityCriteria();
            criteria.setProjection(Projections.property("COLUMNNAME"));
            criteria.add(Restrictions.eq("MANDATORY", mandatory_value));
            criteria.add(Restrictions.eq("RELATEDCOLUMNS", column_id));
            criteria.addOrder(Order.asc("COLUMNID"));
            Resp = (List<String>) criteria.list();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    @Override
    public Map<String, String> getMandatoryColumnsLabel(int mandatory_value, int column_id) {
        Map<String, String> Resp = null;
        try {
            Criteria criteria = createEntityCriteria();
            ProjectionList projList = Projections.projectionList();
            projList.add(Projections.property("COLUMNNAME"), "StringOne");
            projList.add(Projections.property("LABELDISPLAY"), "StringTwo");
            criteria.setProjection(projList);
            criteria.add(Restrictions.eq("MANDATORY", mandatory_value));
            criteria.add(Restrictions.eq("RELATEDCOLUMNS", column_id));
            criteria.addOrder(Order.asc("COLUMNID"));
            criteria.setResultTransformer(Transformers.aliasToBean(TwoDStringObject.class));
            List<TwoDStringObject> objects = (List<TwoDStringObject>) criteria.list();
            Resp = objects.stream().collect(Collectors.toMap(TwoDStringObject::getStringOne, TwoDStringObject::getStringTwo));
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    @Override
    public List<String> getColumnsOfSpecificType(String type, int column_id) {
        List<String> Resp = null;
        try {
            type = type.toLowerCase();
            Criteria criteria = createEntityCriteria();
            criteria.setProjection(Projections.property("COLUMNNAME"));
            criteria.add(Restrictions.eq("FIELDTYPE", type).ignoreCase());
            criteria.add(Restrictions.eq("RELATEDCOLUMNS", column_id));
            criteria.addOrder(Order.asc("COLUMNID"));
            Resp = (List<String>) criteria.list();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    @Override
    public Map<String, String> getColumnsOfSpecificTypeLabel(String type, int column_id) {
        Map<String, String> Resp = null;
        try {
            type = type.toLowerCase();
            Criteria criteria = createEntityCriteria();
            ProjectionList projList = Projections.projectionList();
            projList.add(Projections.property("COLUMNNAME"), "StringOne");
            projList.add(Projections.property("LABELDISPLAY"), "StringTwo");
            criteria.setProjection(projList);

            criteria.add(Restrictions.eq("FIELDTYPE", type).ignoreCase());
            criteria.add(Restrictions.eq("RELATEDCOLUMNS", column_id));
            criteria.addOrder(Order.asc("COLUMNID"));
            criteria.setResultTransformer(Transformers.aliasToBean(TwoDStringObject.class));
            List<TwoDStringObject> objects = (List<TwoDStringObject>) criteria.list();
            Resp = objects.stream().collect(Collectors.toMap(TwoDStringObject::getStringOne, TwoDStringObject::getStringTwo));
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    public String getSubTableName(int column_id, String column_name) {
        List<String> Resp = null;
        try {

            Criteria criteria = createEntityCriteria();
            criteria.setProjection(Projections.property("SUBTABLENAME"));
            if (column_id != -1) {
                criteria.add(Restrictions.eq("COLUMNID", new Long(column_id)));
            } else {
                criteria.add(Restrictions.eq("COLUMNNAME", column_name));
            }
            criteria.addOrder(Order.asc("COLUMNID"));
            Resp = (List<String>) criteria.list();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp.get(0);
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    @Override
    public List<String> getQueryText(long column_id) {
        List<String> Resp = null;
        try {
            Criteria criteria = createEntityCriteria();
            criteria.setProjection(Projections.property("QUERYTEXT"));
            criteria.add(Restrictions.eq("COLUMNID", column_id));
            Resp = (List<String>) criteria.list();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    @Override
    public List<String> getRelatedColName(long column_id) {
        List<String> Resp = null;
        try {
            Criteria criteria = createEntityCriteria();
            criteria.setProjection(Projections.property("RELATEDCOLNAME"));
            criteria.add(Restrictions.eq("COLUMNID", column_id));
            Resp = (List<String>) criteria.list();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    @Override
    public List<DropDownValues> getCategories(long lang_id) {
        List<DropDownValues> Resp = null;
        try {
//    		Criteria criteria = createEntityCriteria();
//    		criteria.add(Restrictions.ne("COLUMNCATEGORY", "CATEGORY_AUTOINC").ignoreCase());
//            criteria.setProjection(Projections.projectionList().add(Projections.distinct(Projections.property("COLUMNCATEGORY"))));
            Query query = createSqlQuery("SELECT NAME As value, (SELECT LABEL FROM TBL_PAGES_LABELS WHERE LABEL_ID=SETT.ID AND LABEL_LEVEL=1 AND PAGE_ID=2 AND LANG_ID=:parameter1) AS relatedField FROM TBL_SETTINGS_CATEGORIES SETT WHERE DISPLAY=1 ORDER BY ID ASC");
            query.setParameter("parameter1", lang_id);
            query.setResultTransformer(Transformers.aliasToBean(DropDownValues.class));
            Resp = (List<DropDownValues>) query.list();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"unchecked", "finally"})
    @Override
    public List<String> getRelatedAutoIDColName(long column_id) {
        List<String> Resp = null;
        try {
            Criteria criteria = createEntityCriteria();
            criteria.setProjection(Projections.property("RELATEDAUTOINCCOLNAME"));
            criteria.add(Restrictions.eq("COLUMNID", column_id));
            Resp = (List<String>) criteria.list();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    public List<String> getColumnNameFromID(List<Long> ListOfColumnsID) {
        List<String> Resp = null;
        try {
            Criteria criteria = createEntityCriteria();
            criteria.setProjection(Projections.property("COLUMNNAME"));
            criteria.add(Restrictions.in("COLUMNID", ListOfColumnsID));
            criteria.add(Restrictions.eq("RELATEDCOLUMNS", 0));
            Resp = (List<String>) criteria.list();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings({"finally", "unchecked"})
    public List<Long> getColumnIDFromName(List<String> ListOfColumnsNames) {
        List<Long> Resp = null;
        try {
            Criteria criteria = createEntityCriteria();
            criteria.setProjection(Projections.property("COLUMNID"));
            criteria.add(Restrictions.in("COLUMNNAME", ListOfColumnsNames));
            criteria.add(Restrictions.eq("RELATEDCOLUMNS", 0));
            Resp = (List<Long>) criteria.list();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }
    }

    public static List<SettingsMapping> Call_PROC_SELECTSETTINGSMAP(Session session, Long setting_id, Long column_id, String TableName, String CategoryName, int isAdmin, long lang_id, String dbDriver) {
        List<SettingsMapping> listOfBundlesMaps = new ArrayList<SettingsMapping>();
        try {

            ResultSet rs = null;
            SessionImpl sessionImpl = (SessionImpl) session;
            Connection connection = sessionImpl.connection();

            if (dbDriver.contains("OracleDriver")) {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SELECTSETTINGSMAP(?,?,?,?,?,?)}");
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.setLong(2, setting_id);
                callableStatement.setLong(3, column_id);
                callableStatement.setString(4, TableName);
                callableStatement.setString(5, CategoryName);
                callableStatement.setString(6, Integer.toString(isAdmin));
                callableStatement.executeQuery();
                rs = (ResultSet) callableStatement.getObject(1);
            } else {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SELECTSETTINGSMAP(?,?,?,?,?,?)}");
                callableStatement.setLong(1, setting_id);
                callableStatement.setLong(2, column_id);
                callableStatement.setString(3, TableName);
                callableStatement.setString(4, CategoryName);
                callableStatement.setString(5, Integer.toString(isAdmin));
                callableStatement.setString(6, Long.toString(lang_id));
                rs = callableStatement.executeQuery();
            }
            while (rs.next()) {
                SettingsMapping stn = new SettingsMapping();
                stn.setCOLUMNID(rs.getLong(1));
                stn.setColumnName(rs.getString(2));
                stn.setColumnDescription(rs.getString(3));
                stn.setLabelDisplay(rs.getString(4));
                stn.setFieldType(rs.getString(5));
                stn.setRelatedColumns(rs.getInt(6));
                stn.setColumnValue(rs.getString(7));
                stn.setQueryText(rs.getString(8));
                stn.setENABLED(rs.getInt(9));
                stn.setEDITABLE(rs.getInt(10));
                stn.setSubTableName(rs.getString(11));
                stn.setAutoInc(rs.getInt(12));
                stn.setUniqueValue(rs.getInt(13));
                stn.setmandatory(rs.getInt(14));
                stn.setrelatedColName(rs.getString(15));
                stn.setrelatedAutoIncColName(rs.getString(16));
                stn.setColumnCategory(rs.getString(17));
                stn.setIsAdmin(rs.getInt(18));

                stn.setfieldOptions(null);
                stn.setRelatedFields(null);
                stn.setAccordionOptions(null);

                listOfBundlesMaps.add(stn);
            }
//            listOfBundlesMaps = (List<BundlesMapping>) callableStatement.getObject(3);
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listOfBundlesMaps;
    }
}
