package com.freedomPass.project.dao;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.commons.utils.Utils;
import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.model.Language;
import com.freedomPass.project.model.SettingsMapping;
import com.freedomPass.project.service.GetSettingsService;
import com.freedomPass.project.service.SettingsMappingService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties.Settings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository("SettingsDao")
public class SettingsDaoImpl extends AbstractDao<Long, Settings> implements SettingsDao {

    @Autowired
    SettingsMappingService SettingsMappingService;

    @Autowired
    private ContextHolder context;

    @Autowired
    GetSettingsService getSettingsService;

    @Autowired
    LanguageDao languageDAO;

    @Autowired
    @Qualifier("userDao")
    UserDao userDao;

    @SuppressWarnings(
            {"finally", "unchecked"})
    public ResponseBodyEntity AddEditSetting(String Setting, int action_flag, int IsAdmin, String Source, String lang_prefix) {
        ResponseBodyEntity Resp = null;
        try {
            Setting = java.net.URLDecoder.decode(Setting, "UTF-8");
            JSONArray stn = new JSONArray(Setting);
            String QueryText = "";
            String UniqueValueColumnsQuery = "";
            String CombinedUniqueValueColumnsQuery = "";
            String MandatoryMsg = "";
            String NumericMsg = "";
            String AutoIncMsg = "";
            String TableName = "tbl_settings";
            List<String> ListOfUniqueValueColumns = SettingsMappingService.getUniqueValueColumns(1, 0);
            List<String> ListOfCombinedUniqueValueColumns = SettingsMappingService.getUniqueValueColumns(2, 0);
            List<String> ListOfMandatoryColumns = SettingsMappingService.getMandatoryColumns(1, 0);
            List<String> ListOfNumericColumns = SettingsMappingService.getColumnsOfSpecificType("NuMbEr", 0);
            List<String> ListOfAutoIncColumns = SettingsMappingService.getAutoIncColumns(0);
            List<String> JsonKeys = new ArrayList<>();
            List<String> JsonValues = new ArrayList<>();
            List<String> ListOfKeys = new ArrayList<>();
            List<String> ListOfVal = new ArrayList<>();
            List<String> ListOfValues = new ArrayList<>();
            List<String> ListOfWhereKeys = new ArrayList<>();
            List<String> ListOfWhereValues = new ArrayList<>();
            List<String> ListOfUniqueKeys = new ArrayList<>();
            List<String> ListOfUniqueValues = new ArrayList<>();
            List<String> ListOfCombinedUniqueKeys = new ArrayList<>();
            List<String> ListOfCombinedUniqueValues = new ArrayList<>();
            long SETTING_ID = 0;
            // Extracting All Json Keys and Value into 2 List<String> JsonKeys & JsonValues
            for (int i = 0; i < stn.length(); i++) {
                JsonKeys.add(stn.getJSONObject(i).keys().next());
                JSONObject tempJson = stn.getJSONObject(i);
                String Key = tempJson.keys().next();
                Object tempObj = tempJson.get(Key);
                String Value = "";
                if (tempObj instanceof Boolean) {
                    boolean bool = tempJson.getBoolean(Key);
                    if (bool) {
                        Value = "1";
                    } else {
                        Value = "0";
                    }
                } else {
                    Value = String.valueOf(tempObj);
                }
                JsonValues.add(Value);
            }
            // Checking if All Mandatory Fields are sent in the request and if these Fields
            // are not null or empty
            if (action_flag == 1) // INSERT
            {
                if (JsonKeys.containsAll(ListOfMandatoryColumns) == false) {
                    ListOfMandatoryColumns.removeAll(JsonKeys);
                    MandatoryMsg = String.join(", ", ListOfMandatoryColumns) + " missing";
                    Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, MandatoryMsg);
                    Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.MANDATORY_FIELDS_MISSING).setHttpResponseEntityResultDescription(MandatoryMsg).getResponse();
                    return Resp;
                }
                for (int ii = 0; ii < JsonKeys.size(); ii++) {
                    // Check if each Mandatory Field is not null or empty
                    if (ListOfMandatoryColumns.contains(JsonKeys.get(ii))) {
                        if (JsonValues.get(ii).equals("") || JsonValues.get(ii).equals("null") || JsonValues.get(ii).equals(null)) {
                            MandatoryMsg = MandatoryMsg + JsonKeys.get(ii).toString() + ", ";
                        }
                    }
                    // Check if each Numeric Field is not null or empty
                    if (ListOfNumericColumns.contains(JsonKeys.get(ii))) {
                        if (StringUtils.isNumeric(JsonValues.get(ii)) == false && !JsonValues.get(ii).equals("") && !JsonValues.get(ii).equals("null") && !JsonValues.get(ii).equals(null)) {
                            NumericMsg = NumericMsg + JsonKeys.get(ii).toString() + ", ";
                        }
                    }
                }
                // Format Returned MSG
                if (!MandatoryMsg.equals("")) {
                    MandatoryMsg = MandatoryMsg.substring(0, MandatoryMsg.length() - 2) + " empty";
                }
                if (!NumericMsg.equals("")) {
                    NumericMsg = NumericMsg.substring(0, NumericMsg.length() - 2) + " not numeric";
                }
            } else // Update
            {
                // Check if AutoInc Columns are sent in case of Update:
                if (JsonKeys.containsAll(ListOfAutoIncColumns) == false) {
                    AutoIncMsg = String.join(", ", ListOfAutoIncColumns) + " missing";
                    Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, AutoIncMsg);
                    Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.MANDATORY_FIELDS_MISSING).setHttpResponseEntityResultDescription(AutoIncMsg).getResponse();
                    return Resp;
                }
                for (int ii = 0; ii < JsonKeys.size(); ii++) {
                    // Check if each Mandatory Field is not null or empty
                    if (ListOfMandatoryColumns.contains(JsonKeys.get(ii))) {
                        if (JsonValues.get(ii).equals("") || JsonValues.get(ii).equals("null") || JsonValues.get(ii).equals(null)) {
                            MandatoryMsg = MandatoryMsg + JsonKeys.get(ii).toString() + ", ";
                        }
                    }
                    // Check if each Numeric Field is not null or empty
                    if (ListOfNumericColumns.contains(JsonKeys.get(ii))) {
                        if (StringUtils.isNumeric(JsonValues.get(ii)) == false && !JsonValues.get(ii).equals("") && !JsonValues.get(ii).equals("null") && !JsonValues.get(ii).equals(null)) {
                            NumericMsg = NumericMsg + JsonKeys.get(ii).toString() + ", ";
                        }
                    }
                    // Check if each AutoInc Field is not null or empty
                    if (ListOfAutoIncColumns.contains(JsonKeys.get(ii))) {
                        if (JsonValues.get(ii).equals("") || JsonValues.get(ii).equals("null") || JsonValues.get(ii).equals(null)) {
                            AutoIncMsg = AutoIncMsg + JsonKeys.get(ii).toString() + ", ";
                        }
                    }
                }
                // Format Returned MSG
                if (!MandatoryMsg.equals("")) {
                    MandatoryMsg = MandatoryMsg.substring(0, MandatoryMsg.length() - 2) + " empty";
                }
                if (!NumericMsg.equals("")) {
                    NumericMsg = NumericMsg.substring(0, NumericMsg.length() - 2) + " not numeric";
                }
                if (!AutoIncMsg.equals("")) {
                    AutoIncMsg = AutoIncMsg.substring(0, AutoIncMsg.length() - 2) + " empty";
                }
            }
            // if There is Mandatory Fields missing or null or empty || numeric Field is not
            // numeric || autoInc Field missing or null or empty THEN: return error.
            if (!MandatoryMsg.equals("")) {
                Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, MandatoryMsg);
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.MANDATORY_FIELDS_MISSING).setHttpResponseEntityResultDescription(MandatoryMsg).getResponse();
                return Resp;
            }
            if (!NumericMsg.equals("")) {
                Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, NumericMsg);
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.NOT_NUMERIC).setHttpResponseEntityResultDescription(NumericMsg).getResponse();
                return Resp;
            }
            if (!AutoIncMsg.equals("")) {
                Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, AutoIncMsg);
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.MANDATORY_FIELDS_MISSING).setHttpResponseEntityResultDescription(AutoIncMsg).getResponse();
                return Resp;
            }
            String JsonKey = "";
            String JsonValue = "";
            // Loop On JsonKeys & Values
            for (int i = 0; i < stn.length(); i++) {
                JSONObject tempJson = stn.getJSONObject(i);
                JsonKey = tempJson.keys().next();
                Object tempObj = tempJson.get(JsonKey);
                if (tempObj instanceof Boolean) {
                    boolean bool = tempJson.getBoolean(JsonKey);
                    if (bool) {
                        JsonValue = "1";
                    } else {
                        JsonValue = "0";
                    }
                } else {
                    JsonValue = String.valueOf(tempObj);
                }
                // Extract Unique Columns to prevent duplications
                if (ListOfUniqueValueColumns.contains(JsonKey.toUpperCase())) {
                    ListOfUniqueKeys.add("WHEN LOWER(" + JsonKey.toUpperCase() + ") = LOWER(?) THEN '" + JsonKey.toUpperCase() + "'");
                    ListOfUniqueValues.add(JsonValue);
                }
                // Extract Combined Unique Columns to prevent duplications
                if (ListOfCombinedUniqueValueColumns.contains(JsonKey.toUpperCase())) {
                    ListOfCombinedUniqueKeys.add("LOWER(" + JsonKey.toUpperCase() + ") = LOWER(?)");
                    ListOfCombinedUniqueValues.add(JsonValue);
                }
                // Extract Auto Increment Columns: - To remove it in case of insert ; - To add
                // it to the where condition in case of update [Where AutoInc(s) = ?]
                if (ListOfAutoIncColumns.contains(JsonKey) == false) {
                    if (action_flag == 1) {
                        ListOfKeys.add(JsonKey);
                        ListOfVal.add("?");
                    } else {
                        ListOfKeys.add(JsonKey + "= ?");
                    }
                    ListOfValues.add(JsonValue);
                } else if (action_flag != 1) {
                    ListOfWhereKeys.add(JsonKey + " = ?");
                    ListOfWhereValues.add(JsonValue);
                    SETTING_ID = Long.parseLong(JsonValue);
                }
            }
            // Formatting CheckUniqueValues Query, CheckCombinedUniqueValues Query:
            if (ListOfUniqueKeys.size() > 0) {
                UniqueValueColumnsQuery = "SELECT CASE " + String.join(" ", ListOfUniqueKeys) + " END AS UNIQUEFLAG FROM " + TableName + " WHERE CASE " + String.join(" ", ListOfUniqueKeys) + " END IS NOT NULL";
                if (action_flag != 1) {
                    String WhereCond = String.join(" AND ", ListOfWhereKeys);
                    WhereCond = WhereCond.replaceAll("=", "!=");
                    UniqueValueColumnsQuery = UniqueValueColumnsQuery + " AND " + WhereCond;
                }
                Query query = getSession().createSQLQuery(UniqueValueColumnsQuery);
                for (int ii = 0; ii < ListOfUniqueKeys.size(); ii++) {
                    query.setString(ii, ListOfUniqueValues.get(ii));
                }
                for (int ii = 0; ii < ListOfUniqueKeys.size(); ii++) {
                    query.setString(ListOfUniqueKeys.size() + ii, ListOfUniqueValues.get(ii));
                }
                for (int ii = 0; ii < ListOfWhereKeys.size(); ii++) {
                    query.setString(ListOfUniqueKeys.size() * 2 + ii, ListOfWhereValues.get(ii));
                }
                // Check if there is Duplication:
                List<String> UniResp = (List<String>) query.list();
                if (!UniResp.isEmpty()) {
                    String Msg = String.join(", ", UniResp) + " taken. Please try another one.";
                    ResponseBuilder RespB = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR).setHttpResponseEntityResultDescription(Msg);
                    Resp = RespB.getResponse();
                    return Resp;
                }
            }
            // Check if there is Combined Duplication:
            if (ListOfCombinedUniqueKeys.size() > 0) {
                CombinedUniqueValueColumnsQuery = "SELECT SETTING_ID FROM " + TableName + " WHERE " + String.join(" AND ", ListOfCombinedUniqueKeys);
                String WhereCond = String.join(" AND ", ListOfWhereKeys);
                WhereCond = WhereCond.replaceAll("=", "!=");
                if (!WhereCond.equals("")) {
                    CombinedUniqueValueColumnsQuery = CombinedUniqueValueColumnsQuery + " AND " + WhereCond;
                }
                Query query = getSession().createSQLQuery(CombinedUniqueValueColumnsQuery);
                for (int ii = 0; ii < ListOfCombinedUniqueKeys.size(); ii++) {
                    query.setString(ii, ListOfCombinedUniqueValues.get(ii));
                }
                for (int ii = 0; ii < ListOfWhereKeys.size(); ii++) {
                    query.setString(ListOfCombinedUniqueKeys.size() + ii, ListOfWhereValues.get(ii));
                }
                List<String[]> CombinedUniResp = (List<String[]>) query.list();
                if (!CombinedUniResp.isEmpty()) {
                    String Msg = String.join(",", ListOfCombinedUniqueValueColumns) + " are taken. Please try another one.";
                    ResponseBuilder RespB = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR).setHttpResponseEntityResultDescription(Msg);
                    Resp = RespB.getResponse();
                    return Resp;
                }
            }
            // Formatting Insert/Update Queries, Execute Insert/Update Query:
            if (action_flag == 1) {
                QueryText = "INSERT INTO " + TableName + " (" + String.join(",", ListOfKeys) + ") VALUES (" + String.join(",", ListOfVal) + ")";
            } else if (action_flag == 2) {
                QueryText = "UPDATE " + TableName + " SET " + String.join(",", ListOfKeys) + " WHERE " + String.join(",", ListOfWhereKeys);
            }
            SessionFactoryImplementor impl = (SessionFactoryImplementor) sessionFactory;
            @SuppressWarnings("deprecation")
            ConnectionProvider cp = impl.getConnectionProvider();
            Connection conn = cp.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(QueryText, Statement.RETURN_GENERATED_KEYS);
            for (int ii = 0; ii < ListOfValues.size(); ii++) {
                if (!ListOfValues.get(ii).equals("null")) {
                    pstmt.setString(ii + 1, ListOfValues.get(ii));
                } else {
                    // pstmt.setString(ii+1, "");
                    pstmt.setString(ii + 1, null);
                    // pstmt.setNull(ii+1, java.sql.Types.INTEGER);
                }
            }
            for (int ii = 0; ii < ListOfWhereValues.size(); ii++) {
                if (!ListOfWhereValues.get(ii).equals("null")) {
                    pstmt.setString(ListOfValues.size() + ii + 1, ListOfWhereValues.get(ii));
                } else {
                    pstmt.setString(ListOfValues.size() + ii + 1, "");
                }
            }
            pstmt.executeUpdate();
            String dbDriver = context.getEnvironment().getProperty("jdbc.driverClassName");
            if (action_flag == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                String row_id = rs.getString(1);
                rs.close();
                if (dbDriver.contains("OracleDriver")) {
                    String SQLQuery = "SELECT SETTING_ID FROM " + TableName + " WHERE ROWID=?";
                    pstmt = conn.prepareStatement(SQLQuery);
                    pstmt.setString(1, row_id);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        SETTING_ID = rs.getLong("SETTING_ID");
                    }
                } else {
                    SETTING_ID = Long.parseLong(row_id);
                }
            }
            pstmt.close();
            conn.close();
            Resp = ResponseBuilder.getInstance().setHttpStatus(HttpStatus.OK).setHttpResponseEntityResultCode(ResponseCode.SUCCESS).addHttpResponseEntityData("secondLevelFlag", 1)
                    .addHttpResponseEntityData("SettingsMapping", SettingsMappingService.getSettingsDetails(SETTING_ID, "all", IsAdmin, lang_prefix)).getResponse();
        } catch (Exception ex) {
            Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.EXCEPTION_OCCURED).getResponse();
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings(
            {"finally", "resource", "unchecked"})
    public ResponseBodyEntity AddEditSubSetting(String Setting, int action_flag, int IsAdmin, String lang_prefix) {
        ResponseBodyEntity Resp = null;
        try {
            Setting = java.net.URLDecoder.decode(Setting, "UTF-8");
            JSONObject stn = new JSONObject(Setting);
            JSONObject data = null;
            String MandatoryMsg = "";
            // Check if ColumnId is sent
            if (stn.has("columnId") == false) {
                MandatoryMsg = "columnID is Missing";
                Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, MandatoryMsg);
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.MANDATORY_FIELDS_MISSING).setHttpResponseEntityResultDescription(MandatoryMsg).getResponse();
                return Resp;
            }
            // Check if data is sent
            if (stn.has("data") == false) {
                MandatoryMsg = "data is Missing";
                Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, MandatoryMsg);
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.MANDATORY_FIELDS_MISSING).setHttpResponseEntityResultDescription(MandatoryMsg).getResponse();
                return Resp;
            }
            // Check if ColumnId is empty or null
            if (stn.get("columnId").equals("") || stn.get("columnId").equals("null") || stn.get("columnId").equals(null)) {
                MandatoryMsg = "columnID is empty";
                Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, MandatoryMsg);
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.NOT_NUMERIC).setHttpResponseEntityResultDescription(MandatoryMsg).getResponse();
                return Resp;
            }
            // Check if data is empty or null
            if (stn.get("data").equals("") || stn.get("data").equals("null") || stn.get("data").equals(null) || stn.get("data").toString().equals("{}")) {
                MandatoryMsg = "data is empty";
                Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, MandatoryMsg);
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.NOT_NUMERIC).setHttpResponseEntityResultDescription(MandatoryMsg).getResponse();
                return Resp;
            }
            // Check if ColumnId is empty or null
            if (StringUtils.isNumeric(stn.get("columnId").toString()) == false) {
                MandatoryMsg = "columnID is not numeric";
                Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, MandatoryMsg);
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.NOT_NUMERIC).setHttpResponseEntityResultDescription(MandatoryMsg).getResponse();
                return Resp;
            }
            // Get data: ColumnID & data
            int ColumnID = stn.getInt("columnId");
            data = stn.getJSONObject("data");
            String QueryText = "";
            String UniqueValueColumnsQuery = "";
            String CombinedUniqueValueColumnsQuery = "";
            MandatoryMsg = "";
            String NumericMsg = "";
            String AutoIncMsg = "";
            String TableName = "tbl_settings";
            String SubTableName = SettingsMappingService.getSubTableName(ColumnID, "");
            String RelatedColName = SettingsMappingService.getRelatedColName(ColumnID).get(0);
            String RelatedAutoIDColName = SettingsMappingService.getRelatedAutoIDColName(ColumnID).get(0);

//			List<String> ListOfUniqueValueColumns = SettingsMappingService.getUniqueValueColumns(1, ColumnID);
            Map<String, String> ListOfUniqueValuesColumnsLabel = SettingsMappingService.getUniqueValueColumnsLabel(1, ColumnID);
            List<String> ListOfUniqueValueColumns = new ArrayList<>(ListOfUniqueValuesColumnsLabel.keySet());

//			List<String> ListOfCombinedUniqueValueColumns = SettingsMappingService.getUniqueValueColumns(2, ColumnID);
            Map<String, String> ListOfCombinedUniqueValueColumnsLabel = SettingsMappingService.getUniqueValueColumnsLabel(2, ColumnID);
            List<String> ListOfCombinedUniqueValueColumns = new ArrayList<>(ListOfCombinedUniqueValueColumnsLabel.keySet());

//			List<String> ListOfMandatoryColumns = SettingsMappingService.getMandatoryColumns(1, ColumnID);
            Map<String, String> ListOfMandatoryColumnsLabel = SettingsMappingService.getMandatoryColumnsLabel(1, ColumnID);
            List<String> ListOfMandatoryColumns = new ArrayList<>(ListOfMandatoryColumnsLabel.keySet());
//			List<String> ListOfNumericColumns = SettingsMappingService.getColumnsOfSpecificType("NuMbEr", ColumnID);
            Map<String, String> ListOfNumericColumnsLabel = SettingsMappingService.getColumnsOfSpecificTypeLabel("NuMbEr", ColumnID);
            List<String> ListOfNumericColumns = new ArrayList<>(ListOfNumericColumnsLabel.keySet());

//			List<String> ListOfAutoIncColumns = SettingsMappingService.getAutoIncColumns(ColumnID);
            Map<String, String> ListOfAutoIncColumnsLabel = SettingsMappingService.getAutoIncColumnsLabel(ColumnID);
            List<String> ListOfAutoIncColumns = new ArrayList<>(ListOfAutoIncColumnsLabel.keySet());
            List<String> JsonKeys = new ArrayList<>();
            List<String> JsonValues = new ArrayList<>();
            List<String> ListOfKeys = new ArrayList<>();
            List<String> ListOfVal = new ArrayList<>();
            List<String> ListOfDelKeys = new ArrayList<>();
            List<String> ListOfDelValues = new ArrayList<>();
            List<String> ListOfValues = new ArrayList<>();
            List<String> ListOfWhereKeys = new ArrayList<>();
            List<String> ListOfWhereValues = new ArrayList<>();
            List<String> ListOfUniqueKeys = new ArrayList<>();
            List<String> ListOfUniqueValues = new ArrayList<>();
            List<String> ListOfCombinedUniqueKeys = new ArrayList<>();
            List<String> ListOfCombinedUniqueValues = new ArrayList<>();
            long auto_id = 0;
            // Extracting All Json Keys and Value into 2 List<String> JsonKeys & JsonValues
            Iterator<String> ItKeys = data.keys();
            while (ItKeys.hasNext()) {
                String Key = ItKeys.next();
                JsonKeys.add(Key);
                Object tempObj = data.get(Key);
                String Value = "";
                if (tempObj instanceof Boolean) {
                    boolean bool = data.getBoolean(Key);
                    if (bool) {
                        Value = "1";
                    } else {
                        Value = "0";
                    }
                } else {
                    Value = String.valueOf(tempObj);
                }
                JsonValues.add(Value);
            }
            // Checking if All Mandatory Fields are sent in the request and if these Fields
            // are not null or empty
            if (action_flag == 1) // INSERT
            {
                if (JsonKeys.containsAll(ListOfMandatoryColumns) == false) {
                    ListOfMandatoryColumns.removeAll(JsonKeys);
                    ListOfMandatoryColumnsLabel.keySet().removeAll(JsonKeys);
                    MandatoryMsg = String.join(", ", new ArrayList<>(ListOfMandatoryColumnsLabel.values())) + " are missing";
                    Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, MandatoryMsg);
                    Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.MANDATORY_FIELDS_MISSING).setHttpResponseEntityResultDescription(MandatoryMsg).getResponse();
                    return Resp;
                }
                for (int ii = 0; ii < JsonKeys.size(); ii++) {
                    // Check if each Mandatory Field is not null or empty
                    if (ListOfMandatoryColumns.contains(JsonKeys.get(ii))) {
                        if (JsonValues.get(ii).equals("") || JsonValues.get(ii).equals("null") || JsonValues.get(ii).equals(null)) {
                            MandatoryMsg = MandatoryMsg + ListOfMandatoryColumnsLabel.get(JsonKeys.get(ii).toString()) + ", ";
                        }
                    }
                    // Check if each Numeric Field is not null or empty
                    if (ListOfNumericColumns.contains(JsonKeys.get(ii))) {
                        if (StringUtils.isNumeric(JsonValues.get(ii)) == false && !JsonValues.get(ii).equals("") && !JsonValues.get(ii).equals("null") && !JsonValues.get(ii).equals(null)) {
                            NumericMsg = NumericMsg + ListOfNumericColumnsLabel.get(JsonKeys.get(ii).toString()) + ", ";
                        }
                    }
                }
                // Format Returned MSG
                if (!MandatoryMsg.equals("")) {
                    if (StringUtils.countMatches(".", MandatoryMsg) > 0) {
                        MandatoryMsg = MandatoryMsg.substring(0, MandatoryMsg.length() - 2) + " are empty";
                    } else {
                        MandatoryMsg = MandatoryMsg.substring(0, MandatoryMsg.length() - 2) + " is empty";
                    }
                }
                if (!NumericMsg.equals("")) {
                    if (StringUtils.countMatches(".", NumericMsg) > 0) {
                        NumericMsg = NumericMsg.substring(0, NumericMsg.length() - 2) + " are not numeric";
                    } else {
                        NumericMsg = NumericMsg.substring(0, NumericMsg.length() - 2) + " is not numeric";
                    }
                }
            } else if (action_flag == 2) {
                // Check if AutoInc Columns are sent in case of Update:
                if (JsonKeys.containsAll(ListOfAutoIncColumns) == false) {
                    AutoIncMsg = String.join(", ", new ArrayList<String>(ListOfAutoIncColumnsLabel.values())) + " is missing";
                    Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, AutoIncMsg);
                    Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.MANDATORY_FIELDS_MISSING).setHttpResponseEntityResultDescription(AutoIncMsg).getResponse();
                    return Resp;
                }
                for (int ii = 0; ii < JsonKeys.size(); ii++) {
                    // Check if each Mandatory Field is not null or empty
                    if (ListOfMandatoryColumns.contains(JsonKeys.get(ii))) {
                        if (JsonValues.get(ii).equals("") || JsonValues.get(ii).equals("null") || JsonValues.get(ii).equals(null)) {
                            MandatoryMsg = MandatoryMsg + ListOfMandatoryColumnsLabel.get(JsonKeys.get(ii).toString()) + ", ";
                        }
                    }
                    // Check if each Numeric Field is not null or empty
                    if (ListOfNumericColumns.contains(JsonKeys.get(ii))) {
                        if (StringUtils.isNumeric(JsonValues.get(ii)) == false && !JsonValues.get(ii).equals("") && !JsonValues.get(ii).equals("null") && !JsonValues.get(ii).equals(null)) {
                            NumericMsg = NumericMsg + ListOfNumericColumnsLabel.get(JsonKeys.get(ii).toString()) + ", ";
                        }
                    }
                    // Check if each AutoInc Field is not null or empty
                    if (ListOfAutoIncColumns.contains(JsonKeys.get(ii))) {
                        if (JsonValues.get(ii).equals("") || JsonValues.get(ii).equals("null") || JsonValues.get(ii).equals(null)) {
                            AutoIncMsg = AutoIncMsg + ListOfAutoIncColumnsLabel.get(JsonKeys.get(ii).toString()) + ", ";
                        }
                    }
                }
                // Format Returned MSG
                if (!MandatoryMsg.equals("")) {
                    if (StringUtils.countMatches(".", MandatoryMsg) > 0) {
                        MandatoryMsg = MandatoryMsg.substring(0, MandatoryMsg.length() - 2) + " are empty";
                    } else {
                        {
                            MandatoryMsg = MandatoryMsg.substring(0, MandatoryMsg.length() - 2) + " is empty";
                        }
                    }

                }
                if (!NumericMsg.equals("")) {
                    if (StringUtils.countMatches(".", NumericMsg) > 0) {
                        NumericMsg = NumericMsg.substring(0, NumericMsg.length() - 2) + " are not numeric";
                    } else {
                        NumericMsg = NumericMsg.substring(0, NumericMsg.length() - 2) + " is not numeric";
                    }

                }
                if (!AutoIncMsg.equals("")) {
                    if (StringUtils.countMatches(".", AutoIncMsg) > 0) {
                        AutoIncMsg = AutoIncMsg.substring(0, AutoIncMsg.length() - 2) + " are empty";
                    } else {
                        AutoIncMsg = AutoIncMsg.substring(0, AutoIncMsg.length() - 2) + " is empty";
                    }
                }
            } else {
                // Check if Mandatory Column is missing
                if (JsonKeys.containsAll(ListOfAutoIncColumns) == false) {
                    String Msg = String.join(", ", new ArrayList<String>(ListOfAutoIncColumnsLabel.values())) + " missing.";
                    Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, Msg);
                    Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.MANDATORY_FIELDS_MISSING).setHttpResponseEntityResultDescription(Msg).getResponse();
                    return Resp;
                }
                for (int ii = 0; ii < JsonKeys.size(); ii++) {
                    // Check if each Numeric Field is not null or empty
                    if (ListOfAutoIncColumns.contains(JsonKeys.get(ii))) {
                        if (JsonValues.get(ii).equals("") || JsonValues.get(ii).equals("null") || JsonValues.get(ii).equals(null)) {
                            AutoIncMsg = AutoIncMsg + ListOfAutoIncColumnsLabel.get(JsonKeys.get(ii).toString()) + ", ";
                        } else {
                            ListOfDelKeys.add(JsonKeys.get(ii) + "= ?");
                            ListOfDelValues.add(JsonValues.get(ii));
                        }
                    }
                }
                // Format Returned MSG
                if (!AutoIncMsg.equals("")) {
                    if (StringUtils.countMatches(".", AutoIncMsg) > 0) {
                        AutoIncMsg = AutoIncMsg.substring(0, AutoIncMsg.length() - 2) + " are empty";
                    } else {
                        {
                            AutoIncMsg = AutoIncMsg.substring(0, AutoIncMsg.length() - 2) + " is empty";
                        }
                    }
                }
            }
            // if There is Mandatory Fields missing or null or empty || numeric Field is not
            // numeric || autoInc Field missing or null or empty THEN: return error.
            if (!MandatoryMsg.equals("")) {
                Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, MandatoryMsg);
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.MANDATORY_FIELDS_MISSING).setHttpResponseEntityResultDescription(MandatoryMsg).getResponse();
                return Resp;
            }
            if (!NumericMsg.equals("")) {
                Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, NumericMsg);
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.NOT_NUMERIC).setHttpResponseEntityResultDescription(NumericMsg).getResponse();
                return Resp;
            }
            if (!AutoIncMsg.equals("")) {
                Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, AutoIncMsg);
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.MANDATORY_FIELDS_MISSING).setHttpResponseEntityResultDescription(AutoIncMsg).getResponse();
                return Resp;
            }
            if (action_flag == 3) {
                QueryText = "DELETE FROM " + SubTableName + " WHERE " + String.join(" AND ", ListOfDelKeys);
                Query query = getSession().createSQLQuery(QueryText);
                // ValidationCountOfValues
                for (int ii = 0; ii < ListOfDelKeys.size(); ii++) {
                    query.setString(ii, ListOfDelValues.get(ii));
                }
                query.executeUpdate();
                Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.SUCCESS).getResponse();
                return Resp;
            }
            String JsonKey = "";
            String JsonValue = "";
            // Loop On JsonKeys & Values
            ItKeys = data.keys();
            while (ItKeys.hasNext()) {
                JsonKey = ItKeys.next();
                Object tempObj = data.get(JsonKey);
                JsonValue = "";
                if (tempObj instanceof Boolean) {
                    boolean bool = data.getBoolean(JsonKey);
                    if (bool) {
                        JsonValue = "1";
                    } else {
                        JsonValue = "0";
                    }
                } else {
                    JsonValue = String.valueOf(tempObj);
                }
                JsonValues.add(JsonValue);
                // Extract Unique Columns to prevent duplications
                if (ListOfUniqueValueColumns.contains(JsonKey.toUpperCase())) {
                    ListOfUniqueKeys.add("WHEN LOWER(" + JsonKey.toUpperCase() + ") = LOWER(?) THEN '" + JsonKey.toUpperCase() + "'");
                    ListOfUniqueValues.add(JsonValue);
                }
                // Extract Unique Columns to prevent duplications
                if (ListOfCombinedUniqueValueColumns.contains(JsonKey.toUpperCase())) {
                    ListOfCombinedUniqueKeys.add("LOWER(" + JsonKey.toUpperCase() + ") = LOWER(?)");
                    ListOfCombinedUniqueValues.add(JsonValue);
                }
                // Extract Auto Increment Columns: - To remove it in case of insert ; - To add
                // it to the where condition in case of update [Where AutoInc(s) = ?]
                if (ListOfAutoIncColumns.contains(JsonKey) == false) {
                    if (action_flag == 1) {
                        ListOfKeys.add(JsonKey);
                        ListOfVal.add("?");
                    } else {
                        ListOfKeys.add(JsonKey + "= ?");
                    }
                    ListOfValues.add(JsonValue);
                } else if (action_flag != 1) {
                    ListOfWhereKeys.add(JsonKey + " = ?");
                    ListOfWhereValues.add(JsonValue);
                    auto_id = Long.parseLong(JsonValue);
                }
            }
            // Formatting Insert/Update Queries, CheckUniqueValues Query,
            // CheckCombinedUniqueValues Query:
            if (ListOfUniqueKeys.size() > 0) {
                UniqueValueColumnsQuery = "SELECT CONCAT( CASE " + String.join(" ELSE '' END, ', ', CASE ", ListOfUniqueKeys) + " ELSE '' END) AS UNIQUEFLAG FROM " + SubTableName + " WHERE CASE " + String.join(" ", ListOfUniqueKeys) + " END IS NOT NULL";
                if (action_flag != 1) {
                    String WhereCond = String.join(" AND ", ListOfWhereKeys);
                    WhereCond = WhereCond.replaceAll("=", "!=");
                    UniqueValueColumnsQuery = UniqueValueColumnsQuery + " AND " + WhereCond;
                }
                Query query = getSession().createSQLQuery(UniqueValueColumnsQuery);
                // ValidationCountOfValues
                for (int ii = 0; ii < ListOfUniqueKeys.size(); ii++) {
                    query.setString(ii, ListOfUniqueValues.get(ii));
                }
                for (int ii = 0; ii < ListOfUniqueKeys.size(); ii++) {
                    query.setString(ListOfUniqueKeys.size() + ii, ListOfUniqueValues.get(ii));
                }
                for (int ii = 0; ii < ListOfWhereKeys.size(); ii++) {
                    query.setString(ListOfUniqueKeys.size() * 2 + ii, ListOfWhereValues.get(ii));
                }
                // Check if there is Duplication:
                List<String> UniResp = (List<String>) query.list();
                if (!UniResp.isEmpty()) {
                    String Msg = "";
                    if (UniResp.size() > 1) {
                        Msg = "The values of ";
                    } else {
                        Msg = "The value of ";
                    }
                    for (String vvv : UniResp) {
                        String[] vv = vvv.split(",");
                        for (String v : vv) {
                            if (!v.trim().equals("")) {
                                if (ListOfUniqueValuesColumnsLabel.get(v.trim()) != null) {
                                    Msg = Msg + ListOfUniqueValuesColumnsLabel.get(v.trim()).toString() + ", ";
                                    ListOfUniqueValuesColumnsLabel.remove(v.trim());
                                }
                            }
                        }
                    }

                    Msg = Msg.substring(0, Msg.length() - 2) + " already exist.";
                    ResponseBuilder RespB = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR).setHttpResponseEntityResultDescription(Msg);
                    Resp = RespB.getResponse();
                    return Resp;
                }
            }
            // Check if there is Combined Duplication:
            if (ListOfCombinedUniqueKeys.size() > 0) {
                CombinedUniqueValueColumnsQuery = "SELECT " + RelatedAutoIDColName + " FROM " + SubTableName + " WHERE " + String.join(" AND ", ListOfCombinedUniqueKeys);
                String WhereCond = String.join(" AND ", ListOfWhereKeys);
                WhereCond = WhereCond.replaceAll("=", "!=");
                if (!WhereCond.equals("")) {
                    CombinedUniqueValueColumnsQuery = CombinedUniqueValueColumnsQuery + " AND " + WhereCond;
                }
                Query query = getSession().createSQLQuery(CombinedUniqueValueColumnsQuery);
                // ValidationCountOfValues
                for (int ii = 0; ii < ListOfCombinedUniqueKeys.size(); ii++) {
                    query.setString(ii, ListOfCombinedUniqueValues.get(ii));
                }
                for (int ii = 0; ii < ListOfWhereKeys.size(); ii++) {
                    query.setString(ListOfCombinedUniqueKeys.size() + ii, ListOfWhereValues.get(ii));
                }
                List<String> CombinedUniResp = (List<String>) query.list();
                if (!CombinedUniResp.isEmpty()) {
                    String Msg = "The Combination of " + String.join(", ", new ArrayList<String>(ListOfCombinedUniqueValueColumnsLabel.values())) + " already exist.";
                    ResponseBuilder RespB = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR).setHttpResponseEntityResultDescription(Msg);
                    Resp = RespB.getResponse();
                    return Resp;
                }
            }
            if (action_flag == 1) {
                QueryText = "INSERT INTO " + SubTableName + " (" + String.join(",", ListOfKeys) + ") VALUES (" + String.join(",", ListOfVal) + ")";
            } else if (action_flag == 2) {
                QueryText = "UPDATE " + SubTableName + " SET " + String.join(",", ListOfKeys) + " WHERE " + String.join(",", ListOfWhereKeys);
            }
            // Execute Insert/Update Query:
            SessionFactoryImplementor impl = (SessionFactoryImplementor) sessionFactory;
            @SuppressWarnings("deprecation")
            ConnectionProvider cp = impl.getConnectionProvider();
            Connection conn = cp.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(QueryText, Statement.RETURN_GENERATED_KEYS);
            for (int ii = 0; ii < ListOfValues.size(); ii++) {
                if (!ListOfValues.get(ii).equals("null")) {
                    pstmt.setString(ii + 1, ListOfValues.get(ii));
                } else {
                    pstmt.setString(ii + 1, "");
                }
            }
            for (int ii = 0; ii < ListOfWhereValues.size(); ii++) {
                if (!ListOfWhereValues.get(ii).equals("null")) {
                    pstmt.setString(ListOfValues.size() + ii + 1, ListOfWhereValues.get(ii));
                } else {
                    pstmt.setString(ListOfValues.size() + ii + 1, "");
                }
            }
            pstmt.executeUpdate();
            String JsonAddedRow = "";
            String dbDriver = context.getEnvironment().getProperty("jdbc.driverClassName");
            long SETTING_ID = 0;
            if (action_flag == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                String row_id = rs.getString(1);
                rs.close();
                String SQLQuery = "";
                if (!RelatedColName.equals("") && !RelatedColName.equals("null") && !RelatedColName.equals(null)) {
                    if (dbDriver.contains("OracleDriver")) {
                        SQLQuery = "SELECT " + RelatedColName + " FROM " + SubTableName + " WHERE ROWID=?";
                    } else {
                        SQLQuery = "SELECT " + RelatedColName + " FROM " + SubTableName + " WHERE " + RelatedAutoIDColName + "=?";
                    }
                    pstmt = conn.prepareStatement(SQLQuery);
                    pstmt.setString(1, row_id);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        SETTING_ID = rs.getLong(RelatedColName);
                    }
                    String SettingsQuery = SettingsMappingService.getQueryText(ColumnID).get(0);
                    String AccQuery = GetSubLevelQuery(SETTING_ID, ColumnID, TableName, SettingsQuery, IsAdmin, lang_prefix);
                    if (dbDriver.contains("OracleDriver")) {
                        JsonAddedRow = Utils.Call_PROC_SELECTANYQUERY_TOJSON(AccQuery + " WHERE " + RelatedColName + "=" + SETTING_ID + " AND ROWID='" + row_id + "'", getSession(), dbDriver);
                    } else {
                        JsonAddedRow = Utils.Call_PROC_SELECTANYQUERY_TOJSON(AccQuery + " WHERE " + RelatedColName + "=" + SETTING_ID + " AND " + RelatedAutoIDColName + "='" + row_id + "'", getSession(), dbDriver);
                    }
                } else {
                    String SettingsQuery = SettingsMappingService.getQueryText(ColumnID).get(0);
                    String AccQuery = GetSubLevelQuery(1, ColumnID, TableName, SettingsQuery, IsAdmin, lang_prefix);
                    if (dbDriver.contains("OracleDriver")) {
                        JsonAddedRow = Utils.Call_PROC_SELECTANYQUERY_TOJSON(AccQuery + " WHERE ROWID='" + row_id + "'", getSession(), dbDriver);
                    } else {
                        JsonAddedRow = Utils.Call_PROC_SELECTANYQUERY_TOJSON(AccQuery + " WHERE " + RelatedAutoIDColName + "='" + row_id + "'", getSession(), dbDriver);
                    }
                }
            } else if (action_flag == 2) {
                if (!RelatedColName.equals("") && !RelatedColName.equals("null") && !RelatedColName.equals(null)) {
                    ResultSet rs = null;
                    String SQLQuery = "SELECT " + RelatedColName + " FROM " + SubTableName + " WHERE " + RelatedAutoIDColName + "=?";
                    pstmt = conn.prepareStatement(SQLQuery);
                    pstmt.setLong(1, auto_id);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        SETTING_ID = rs.getLong(RelatedColName);
                    }
                    if (SETTING_ID == 0) {
                        String Msg = "Invalid Settings";
                        ResponseBuilder RespB = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.NOT_FOUND_PARAMETERS).setHttpResponseEntityResultDescription(Msg);
                        Resp = RespB.getResponse();
                        return Resp;
                    }
                    String SettingsQuery = SettingsMappingService.getQueryText(ColumnID).get(0);
                    String AccQuery = GetSubLevelQuery(SETTING_ID, ColumnID, TableName, SettingsQuery, IsAdmin, lang_prefix);
                    JsonAddedRow = Utils.Call_PROC_SELECTANYQUERY_TOJSON(AccQuery + " WHERE " + RelatedColName + "=" + SETTING_ID + " AND " + RelatedAutoIDColName + "='" + auto_id + "'", getSession(), dbDriver);
                } else {
                    String SettingsQuery = SettingsMappingService.getQueryText(ColumnID).get(0);
                    String AccQuery = GetSubLevelQuery(1, ColumnID, TableName, SettingsQuery, IsAdmin, lang_prefix);
                    JsonAddedRow = Utils.Call_PROC_SELECTANYQUERY_TOJSON(AccQuery + " WHERE " + RelatedAutoIDColName + "='" + auto_id + "'", getSession(), dbDriver);
                }
            }
            pstmt.close();
            conn.close();
            Resp = ResponseBuilder.getInstance().setHttpStatus(HttpStatus.OK).setHttpResponseEntityResultCode(ResponseCode.SUCCESS).addHttpResponseEntityData("accordionOptions", JsonAddedRow).getResponse();
        } catch (Exception ex) {
            Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            Resp = ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.EXCEPTION_OCCURED).getResponse();
        } finally {
            return Resp;
        }
    }

    @SuppressWarnings("finally")
    public String GetSubLevelQuery(long setting_id, long col_id, String TableName, String AccQuery, int IsAdmin, String lang_prefix) {
        try {
            String dbDriver = context.getEnvironment().getProperty("jdbc.driverClassName");
            Language l = languageDAO.getLanguageByPrefix(lang_prefix);
            Session session = getSession();
            List<SettingsMapping> listOfSUBBundlesMaps = SettingsMappingDaoImpl.Call_PROC_SELECTSETTINGSMAP(session, setting_id, col_id, TableName, "all", IsAdmin, l.getId(), dbDriver);
            for (int ii = 0; ii < listOfSUBBundlesMaps.size(); ii++) {
                // Dropdown
                if (listOfSUBBundlesMaps.get(ii).getFieldType().toLowerCase().equals("dropdown")) {
                    AccQuery = AccQuery.replace(listOfSUBBundlesMaps.get(ii).getColumnName(),
                            listOfSUBBundlesMaps.get(ii).getColumnName() + " AS " + listOfSUBBundlesMaps.get(ii).getColumnName() + "_HIDDEN, " + "(SELECT OPTIONNAME FROM SETTINGS_ALLDROPDOWNS WHERE COLUMNID="
                            + Long.toString(listOfSUBBundlesMaps.get(ii).getColumnId()) + " AND OPTIONID=" + listOfSUBBundlesMaps.get(ii).getColumnName() + ") AS " + listOfSUBBundlesMaps.get(ii).getColumnName());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return AccQuery;
        }
    }

    @SuppressWarnings("finally")
    public String SelectAllSettings() {
        String Resp = "";
        try {
            String TableName = "tbl_settings";
            String dbDriver = context.getEnvironment().getProperty("jdbc.driverClassName");
            String jsonArray = Utils.Call_PROC_SELECTANYQUERY_TOJSON("SELECT * from " + TableName + " ORDER BY SETTING_ID ASC", getSession(), dbDriver);
            Resp = jsonArray;
        } catch (Exception ex) {
        } finally {
            return Resp;
        }
    }

    public String[] SelectSettings(String[] keys) {
        String[] finalString = new String[keys.length];
        // String s ="";
        // String b ="";
        for (int i = 0; i < keys.length; i++) {
            if (!keys[i].equals("")) {
                String value = getSettingsService.getSettings_List(1, keys[i], "null", "null", "null", 1).get(0).toString();
                // if(value.equals("1"))
                // {
                // b = "true";
                // }
                // else
                // {
                // b = "false";
                // }
                finalString[i] = value;
            }
        }
        return finalString;
    }

    public ResponseEntity<?> getSettings(long ResponseFormat, long RequestLevel, String MainParameter, String Parameters, String Conditions, String Values, int IsAdmin) {
        if (ResponseFormat == 1) {
            return ResponseBuilder.getInstance().setHttpStatus(HttpStatus.OK).addHttpResponseEntityData("settings", getSettingsService.getSettings_String(RequestLevel, MainParameter, Parameters, Conditions, Values, IsAdmin))
                    .returnClientResponse();
        } else if (ResponseFormat == 2) {
            return ResponseBuilder.getInstance().setHttpStatus(HttpStatus.OK).addHttpResponseEntityData("settings", getSettingsService.getSettings_Boolean(RequestLevel, MainParameter, Parameters, Conditions, Values)).returnClientResponse();
        } else if (ResponseFormat == 3) {
            return ResponseBuilder.getInstance().setHttpStatus(HttpStatus.OK).addHttpResponseEntityData("settings", getSettingsService.getSettings_List(RequestLevel, MainParameter, Parameters, Conditions, Values, IsAdmin))
                    .returnClientResponse();
        } else {
            return ResponseBuilder.getInstance().setHttpStatus(HttpStatus.OK).setHttpResponseEntityResultCode(ResponseCode.PARAMETERS_VALIDATION_ERROR).returnClientResponse();
        }
    }
    // }
    // @SuppressWarnings({ "finally", "unchecked" })
    // public ResponseBodyEntity getSettings(long ResponseFormat, long RequestLevel,
    // String MainParameter, String Parameters, String Conditions, String Values)
    // {
    // ResponseBodyEntity Resp = null;
    // try
    // {
    // String TableName = "";
    // String QueryText= "";
    // String SelectedParameters ="";
    // List<String> ListOfParameters = new ArrayList<String>();
    // List<String> ListOfValues = new ArrayList<String>();
    // List<String> ListOfConditions = new ArrayList<String>();
    // if (RequestLevel == 1)
    // {
    // TableName = "tbl_settings";
    //
    // }
    // else if (RequestLevel == 2)
    // {
    // TableName = SettingsMappingService.getSubTableName(-1, MainParameter);
    // }
    // if (Parameters.equals("null"))
    // {
    // SelectedParameters = MainParameter;
    // }
    // else
    // {
    // SelectedParameters = Parameters.replace("&", ", ");
    // }
    // if (ResponseFormat == 1 || ResponseFormat == 2)
    // {
    // SelectedParameters = "Count(" + SelectedParameters + ")";
    // }
    //
    // if (!Conditions.equals("null"))
    // {
    // ListOfConditions = new
    // ArrayList<String>(Arrays.asList(Conditions.split("&")));
    // ListOfValues = new ArrayList<String>(Arrays.asList(Values.split("&")));
    //
    // for (int i=0; i < ListOfConditions.size(); i++)
    // {ListOfConditions.set(i, ListOfConditions.get(i) + "=?");}
    //
    //
    // QueryText = "SELECT " + SelectedParameters + " FROM " + TableName + " WHERE "
    // + String.join(" AND ", ListOfConditions) ;
    // }
    // else
    // {
    // QueryText = "SELECT " + SelectedParameters + " FROM " + TableName;
    // }
    //
    // Query query = getSession().createSQLQuery(QueryText);
    // for (int i = 0 ; i < ListOfValues.size() ; i++)
    // {
    // query.setString(i,ListOfValues.get(i));
    // }
    // if (ResponseFormat == 1) //Count
    // {
    // Object Response = (Object) query.list().get(0);
    // Resp = ResponseBuilder.getInstance()
    // .setHttpStatus(HttpStatus.OK).setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
    // .addHttpResponseEntityData("settings", Response).getResponse();
    // }
    // else if (ResponseFormat == 2) //Boolean
    // {
    //
    // boolean Response = false;
    // Object obj = (Object) query.list().get(0);
    // String val = obj.toString();
    // if(val.equals("0") == false)
    // {
    // Response = true;
    // }
    // Resp = ResponseBuilder.getInstance()
    // .setHttpStatus(HttpStatus.OK).setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
    // .addHttpResponseEntityData("settings", Response).getResponse();
    // }
    // else if (ResponseFormat == 3) //List Of String
    // {
    // List<Object> Response = (List<Object>) query.list();
    // Resp = ResponseBuilder.getInstance()
    // .setHttpStatus(HttpStatus.OK).setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
    // .addHttpResponseEntityData("settings", Response).getResponse();
    // }
    // else if (ResponseFormat == 4) // List Of List Of String
    // {
    // List<ArrayList<Object>> Response = (List<ArrayList<Object>>) query.list();
    // Resp = ResponseBuilder.getInstance()
    // .setHttpStatus(HttpStatus.OK).setHttpResponseEntityResultCode(ResponseCode.SUCCESS)
    // .addHttpResponseEntityData("settings", Response).getResponse();
    // }
    //
    //
    //
    // }
    // catch(Exception ex)
    // {
    // Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null,
    // ex);
    // Resp =
    // ResponseBuilder.getInstance().setHttpResponseEntityResultCode(ResponseCode.EXCEPTION_OCCURED).getResponse();
    // }
    // finally
    // {
    // return Resp;
    // }
    //
    // }
    //
}
// @SuppressWarnings("finally")
// public String Call_PROC_SELECTANYQUERY_TOJSON(String QueryStmt) {
// Session session = getSession();
// SessionImpl sessionImpl = (SessionImpl) session;
// Connection connection = sessionImpl.connection();
// CallableStatement callableStatement;
// String jsonArray= "";
// try {
// callableStatement = connection.prepareCall("{CALL
// PROC_SELECTANYQUERY(?,?)}");
// callableStatement.setString(1, QueryStmt);
// callableStatement.registerOutParameter(2,OracleTypes.CURSOR);
// callableStatement.executeQuery();
// ResultSet rs = (ResultSet)callableStatement.getObject(2);
// jsonArray= Utils.convertResultSetIntoJSONArray(rs).toString();
// rs.close();
// } catch (Exception ex) {
// Logger.getLogger(SettingsMappingDaoImpl.class.getName()).log(Level.SEVERE,
// null, ex);
//
// }
// finally
// {
// return jsonArray;
// }
//
// }
// @SuppressWarnings("finally")
// public boolean ValidateSettingParametersIfExist(int settingID, String
// ColumnName)
// {
// boolean ValidResp = false;
// try
// {
// Criteria criteria = createEntityCriteria()
// .setProjection(Projections.property(ColumnName))
// .add(Restrictions.eq("SETTING_ID", settingID));
//
// List<String> Resp = (List<String>) criteria.list();
// if(Resp.size() > 0)
// {
// ValidResp = true;
// }
// }
//
// catch (Exception ex)
// {
// Logger.getLogger(SettingsDaoImpl.class.getName()).log(Level.SEVERE, null,
// ex);
// }
// finally
// {
// return ValidResp;
// }
// }
// public List<String[]> Call_PROC_SELECTANYQUERY(String QueryStmt) throws
// SQLException, Exception {
// List<String[]> Resp = new ArrayList<String[]>();
// Session session = getSession();
// SessionImpl sessionImpl = (SessionImpl) session;
// Connection connection = sessionImpl.connection();
// CallableStatement callableStatement = connection.prepareCall("{CALL
// PROC_SELECTANYQUERY(?,?)}");
// callableStatement.setString(1, QueryStmt);
// callableStatement.registerOutParameter(2,OracleTypes.CURSOR);
// callableStatement.executeQuery();
// ResultSet rs = (ResultSet)callableStatement.getObject(2);
// int total_rows = rs.getMetaData().getColumnCount();
// while (rs.next())
// {
// String[] temp = new String[total_rows];
// for(int i = 0; i < total_rows; i++)
// {
// temp[i] = rs.getString(i+1);
// }
// Resp.add(temp);
// }
//
// rs.close();
// return Resp;
// }
