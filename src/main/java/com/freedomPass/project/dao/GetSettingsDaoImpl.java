/**
 *
 */
package com.freedomPass.project.dao;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.commons.utils.Utils;
import com.freedomPass.project.model.SettingsMapping;
import com.freedomPass.project.service.SettingsMappingService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("GetSettingsDao")
public class GetSettingsDaoImpl extends AbstractDao<Long, Object> implements GetSettingsDao {

    @Autowired
    SessionFactory sessionFactory;

    private static SettingsMappingService SettingsMappingService;

    public static SettingsMappingService getSettingsMappingService() {
        return SettingsMappingService;
    }

    @Autowired
    public void setSettingsMappingService(SettingsMappingService settingsMappingService) {
        SettingsMappingService = settingsMappingService;
    }
    private static ContextHolder context;

    public static ContextHolder getContext() {
        return context;
    }

    @Autowired
    public void setContextHolder(ContextHolder Context) {
        context = Context;
    }

//	@Autowired
//	AbstractDao<?, ?> abstractDao;
//	private static AbstractDao AbstractDao;
//	public static AbstractDao getAbstractDao() {return AbstractDao;}
//	@Autowired
//	public void setAbstractDao(AbstractDao abstractDao)
//	{
//		abstractDao = AbstractDao;
//	}
    @SuppressWarnings({"finally"})
    public String getSettings_String(long RequestLevel, String MainParameter, String Parameters, String Conditions, String Values, int IsAdmin) {
        String Resp = null;
        try {
            Session session = getSession();
            String TableName = "";
            String QueryText = "";
            String SelectedParameters = "";
            List<String> ListOfParameters = new ArrayList<String>();
            List<String> ListOfValues = new ArrayList<String>();
            List<String> ListOfConditions = new ArrayList<String>();
            List<String> ListTemp = new ArrayList<>();
            int hidden_flag = 0;
            if (RequestLevel == 1) {
                TableName = "tbl_settings";

            } else if (RequestLevel == 2) {
                TableName = SettingsMappingService.getSubTableName(-1, MainParameter);
            }
            if (Parameters.equals("null")) {
                SelectedParameters = MainParameter.replace("&", ", ");
            } else {
                ListOfParameters = Arrays.asList(Parameters.split("&"));
                ListTemp.addAll(ListOfParameters);
                for (int i = 0; i < ListTemp.size(); i++) {
                    if (ListTemp.get(i).contains("count(".toLowerCase()) == false) {
                        ListTemp.set(i, "LOWER(" + ListTemp.get(i) + ")");
                    }
                }
                SelectedParameters = String.join(", ", ListTemp);
            }
//    		SelectedParameters = "Count(" + SelectedParameters + ")";

            if (!Conditions.equals("null")) {
                ListOfConditions = new ArrayList<String>(Arrays.asList(Conditions.split("&")));
                ListOfValues = new ArrayList<String>(Arrays.asList(Values.split("&")));

                for (int i = 0; i < ListOfConditions.size(); i++) {
                    ListOfConditions.set(i, ListOfConditions.get(i) + "=?");
                }

                QueryText = "SELECT " + SelectedParameters + " FROM " + TableName + " WHERE " + String.join(" AND ", ListOfConditions);
            } else {
                QueryText = "SELECT " + SelectedParameters + " FROM " + TableName;
            }

            List<String> MainParam = new ArrayList<>();
            MainParam.addAll(Arrays.asList(MainParameter.split("&")));

            if (!Parameters.equals("null")) {
                long col_id = SettingsMappingService.getColumnIDFromName(MainParam).get(0);
                QueryText = GetSubLevelQuery(1, col_id, "tbl_settings", QueryText, ListOfParameters, IsAdmin, session);
                if (QueryText.contains("_HIDDEN, ")) {
                    hidden_flag = 1;
                }
            }
            Query query = session.createSQLQuery(QueryText);
            for (int i = 0; i < ListOfValues.size(); i++) {
                if (ListOfValues.get(i).equals("\"\"")) {
                    query.setString(i, "");
                } else {
                    query.setString(i, ListOfValues.get(i));
                }
            }
            Object Response = (Object) query.list().get(0);

            if (SelectedParameters.split(",").length > 1 || hidden_flag == 1) {
                Object[] LObj = (Object[]) Response;
                for (int i = 0; i < LObj.length; i++) {
                    if (Resp == null) {
                        Resp = "";
                    }
                    Resp = Resp + LObj[i] + ", ";
                }
                if (!Resp.equals("")) {
                    Resp = Resp.substring(0, Resp.length() - 2);
                }
            } else {
                Resp = Response.toString();
            }

        } catch (Exception ex) {
            Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }

    }

    @SuppressWarnings({"finally"})
    public boolean getSettings_Boolean(long RequestLevel, String MainParameter, String Parameters, String Conditions, String Values) {
        boolean Resp = false;
        try {
            Session session = getSession();
            String TableName = "";
            String QueryText = "";
            String SelectedParameters = "";
//    		List<String> ListOfParameters = new ArrayList<String>();
            List<String> ListOfValues = new ArrayList<String>();
            List<String> ListOfConditions = new ArrayList<String>();
            if (RequestLevel == 1) {
                TableName = "tbl_settings";

            } else if (RequestLevel == 2) {
                TableName = SettingsMappingService.getSubTableName(-1, MainParameter);
            }
            if (Parameters.equals("null")) {
                SelectedParameters = MainParameter;
            } else {
                SelectedParameters = Parameters.replace("&", ", ");
            }
            SelectedParameters = "Count(" + SelectedParameters + ")";

            if (!Conditions.equals("null")) {
                ListOfConditions = new ArrayList<String>(Arrays.asList(Conditions.split("&")));
                ListOfValues = new ArrayList<String>(Arrays.asList(Values.split("&")));

                for (int i = 0; i < ListOfConditions.size(); i++) {
                    ListOfConditions.set(i, ListOfConditions.get(i) + "=?");
                }

                QueryText = "SELECT " + SelectedParameters + " FROM " + TableName + " WHERE " + String.join(" AND ", ListOfConditions);
            } else {
                QueryText = "SELECT " + SelectedParameters + " FROM " + TableName;
            }
            Query query = session.createSQLQuery(QueryText);
            for (int i = 0; i < ListOfValues.size(); i++) {
                if (ListOfValues.get(i).equals("\"\"")) {
                    query.setString(i, "");
                } else {
                    query.setString(i, ListOfValues.get(i));
                }
            }

            Object obj = (Object) query.list().get(0);
            String val = obj.toString();
            if (val.equals("0") == false) {
                Resp = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }

    }

    @SuppressWarnings({"finally", "unchecked"})
    public List<Object> getSettings_List(long RequestLevel, String MainParameter, String Parameters, String Conditions, String Values, int IsAdmin) {
        List<Object> Resp = null;
        try {
            Session session = getSession();
            ContextHolder context = Utils.getContext();
            SettingsMappingService SettingsMappingService = Utils.getSettingsMappingService();
            String TableName = "";
            String QueryText = "";
            String SelectedParameters = "";
            List<String> ListOfParametes = new ArrayList<String>();
            List<String> ListOfValues = new ArrayList<String>();
            List<String> ListOfConditions = new ArrayList<String>();
            List<String> ListTemp = new ArrayList<>();
            int hidden_flag = 0;
            if (RequestLevel == 1) {
                TableName = "tbl_settings";

            } else if (RequestLevel == 2) {
                TableName = SettingsMappingService.getSubTableName(-1, MainParameter);
            }
            if (Parameters.equals("null")) {
                SelectedParameters = MainParameter.replace("&", ", ");
            } else {
                ListOfParametes = Arrays.asList(Parameters.split("&"));

                ListTemp.addAll(ListOfParametes);
                for (int i = 0; i < ListTemp.size(); i++) //    			{ListTemp.set(i, "(" + ListTemp.get(i) + ")");}
                {
                    ListTemp.set(i, "LOWER(" + ListTemp.get(i) + ")");
                }
                SelectedParameters = String.join(", ", ListTemp);
            }

            if (!Conditions.equals("null")) {
                ListOfConditions = new ArrayList<String>(Arrays.asList(Conditions.split("&")));
                ListOfValues = new ArrayList<String>(Arrays.asList(Values.split("&")));

                for (int i = 0; i < ListOfConditions.size(); i++) {
                    ListOfConditions.set(i, ListOfConditions.get(i) + "=?");
                }

                QueryText = "SELECT " + SelectedParameters + " FROM " + TableName + " WHERE " + String.join(" AND ", ListOfConditions);
            } else {
                QueryText = "SELECT " + SelectedParameters + " FROM " + TableName;
            }
            List<String> MainParam = new ArrayList<>();
            MainParam.addAll(Arrays.asList(MainParameter.split("&")));

            if (!Parameters.equals("null")) {
                long col_id = SettingsMappingService.getColumnIDFromName(MainParam).get(0);
                QueryText = GetSubLevelQuery(1, col_id, "tbl_settings", QueryText, ListOfParametes, IsAdmin, session);
                if (QueryText.contains("_HIDDEN, ")) {
                    hidden_flag = 1;
                }
            }

            Query query = session.createSQLQuery(QueryText);
            for (int i = 0; i < ListOfValues.size(); i++) {
                if (ListOfValues.get(i).equals("\"\"")) {
                    query.setString(i, "");
                } else {
                    query.setString(i, ListOfValues.get(i));
                }

            }

            Resp = (List<Object>) query.list();

            if (SelectedParameters.split(",").length > 1 || hidden_flag == 1) {
                List<Object> Val = new ArrayList<Object>();
                for (int i = 0; i < Resp.size(); i++) {
                    Object[] Obj = (Object[]) Resp.get(i);
                    String SubVal = "";
                    for (int y = 0; y < Obj.length; y++) {
                        SubVal = SubVal + Obj[y] + ", ";
                    }
                    Val.add(SubVal.substring(0, SubVal.length() - 2));
                }
                Resp = Val;
            }
        } catch (Exception ex) {
            Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return Resp;
        }

    }

    @SuppressWarnings("finally")
    public static String GetSubLevelQuery(long setting_id, long col_id, String TableName, String AccQuery, List<String> ListOfParametes, int IsAdmin, Session session) {

        try {
//    		Session session = abstractDao.getSession();
            String dbDriver = context.getEnvironment().getProperty("jdbc.driverClassName");
//    		Session session = getSession();

            List<SettingsMapping> listOfSUBBundlesMaps = SettingsMappingDaoImpl.Call_PROC_SELECTSETTINGSMAP(session, setting_id, col_id, TableName, "all", IsAdmin, new Long(1), dbDriver);

            for (int ii = 0; ii < listOfSUBBundlesMaps.size(); ii++) {
                // Dropdown
                if (listOfSUBBundlesMaps.get(ii).getFieldType().toLowerCase().equals("dropdown")) {
                    if (ListOfParametes.contains(listOfSUBBundlesMaps.get(ii).getColumnName())) {
                        AccQuery = AccQuery.replace("LOWER(" + listOfSUBBundlesMaps.get(ii).getColumnName() + ")",
                                listOfSUBBundlesMaps.get(ii).getColumnName() + " AS " + listOfSUBBundlesMaps.get(ii).getColumnName() + "_HIDDEN, "
                                + "(SELECT OPTIONNAME FROM SETTINGS_ALLDROPDOWNS WHERE COLUMNID="
                                + Long.toString(listOfSUBBundlesMaps.get(ii).getColumnId()) + " AND OPTIONID=" + listOfSUBBundlesMaps.get(ii).getColumnName() + ") AS "
                                + listOfSUBBundlesMaps.get(ii).getColumnName());
                    }
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return AccQuery;
        }
    }
}
