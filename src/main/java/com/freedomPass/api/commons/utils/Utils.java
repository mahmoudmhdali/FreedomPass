/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.commons.utils;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.project.service.SettingsMappingService;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import oracle.jdbc.OracleTypes;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    public static String getFormattedDateAsString(String format) {
        /* This function just returns the current / instant date in the specified format. If u need specific dates then this function 
        *  should be updated by another parameter representing a given date
         */

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    public static long getTimeDiffSec(Date dateOne, Date dateTwo) {
        //difference in milliseconds
        long diff = dateTwo.getTime() - dateOne.getTime();

        long diffsec = diff / (1000);

        return diffsec;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static Long generateUniqueInteger() {
        AtomicLong TS = new AtomicLong();
        Long micros = System.currentTimeMillis() * 1000;
        for (;;) {
            long value = TS.get();
            if (micros <= value) {
                micros = value + 1;
            }
            if (TS.compareAndSet(value, micros)) {
                return micros;
            }
        }
    }

    public static HashMap<String, String> propertyFileToMap(File file) {
        HashMap<String, String> mp = new HashMap<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.substring(0, 0).contains("#") && !line.isEmpty() && line.split(" = ").length > 1) {
                    mp.put(line.split(" = ")[0], line.split(" = ")[1]);
                }
            }
            scanner.close();
        } catch (IOException ex) {
        }
        return mp;
    }

    private static String quoteIfNeeded(Object o) {
        if (o == null) {
            return "\"N/A\"";
        } else {
            return "\"" + o + "\"";
        }
    }

    public static String convertResultSetIntoJSON(ResultSet rs, Integer rowNumbers) throws SQLException {
        ArrayList<String> colnams = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            colnams.add(rsmd.getColumnName(i + 1));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("{").append(quoteIfNeeded("rowNumber")).append(":").append(quoteIfNeeded(rowNumbers)).append("},");
        while (rs.next()) {
            sb.append("{");
            for (int i = 0; i < colnams.size(); i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(quoteIfNeeded(colnams.get(i)));
                sb.append(":");
                Object o = rs.getObject(i + 1);
                sb.append(quoteIfNeeded(o));
            }
            sb.append("}");
            if (!rs.isLast()) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @SuppressWarnings("finally")
    public static String Call_PROC_SELECTANYQUERY_TOJSON(String QueryStmt, Session session, String dbDriver) throws SQLException, Exception {
        SessionImpl sessionImpl = (SessionImpl) session;
        Connection connection = sessionImpl.connection();
        CallableStatement callableStatement;
        String jsonArray = "";

        ResultSet rs = null;
        if (dbDriver.contains("OracleDriver")) {
            callableStatement = connection.prepareCall("{CALL PROC_SELECTANYQUERY(?,?)}");
            callableStatement.setString(1, QueryStmt);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
            callableStatement.executeQuery();
            rs = (ResultSet) callableStatement.getObject(2);
        } else {
            callableStatement = connection.prepareCall("{CALL PROC_SELECTANYQUERY(?)}");
            callableStatement.setString(1, QueryStmt);
            rs = callableStatement.executeQuery();
        }

        jsonArray = Utils.convertResultSetIntoJSONArray(rs).toString();
        rs.close();
        return jsonArray;

    }

    public static JSONArray convertResultSetIntoJSONArray(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);
                // if value in DB is null, then we set it to default value
                if (columnValue == null) {
                    columnValue = "null";
                }
                /*
                Next if block is a hack. In case when in db we have values like price and price1 there's a bug in jdbc - 
                both this names are getting stored as price in ResulSet. Therefore when we store second column value,
                we overwrite original value of price. To avoid that, i simply add 1 to be consistent with DB.
                 */
                if (obj.has(columnName)) {
                    columnName += "1";
                }
                obj.put(columnName.toUpperCase(), columnValue);
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }

    private static ContextHolder context;
    private static SettingsMappingService SettingsMappingService;

    public static SettingsMappingService getSettingsMappingService() {
        return SettingsMappingService;
    }

    @Autowired
    public void setSettingsMappingService(SettingsMappingService settingsMappingService) {
        SettingsMappingService = settingsMappingService;
    }

    public static ContextHolder getContext() {
        return context;
    }

    @Autowired
    public void setContextHolder(ContextHolder Context) {
        context = Context;
    }
}
