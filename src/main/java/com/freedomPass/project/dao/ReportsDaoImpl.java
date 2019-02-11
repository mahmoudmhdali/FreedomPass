/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.dao;

import com.freedomPass.api.commons.ContextHolder;
import com.freedomPass.api.commons.Logger;
import com.freedomPass.api.commons.utils.Utils;
import com.freedomPass.api.io.NetworkFileManager;
import com.freedomPass.project.model.Group;
import com.freedomPass.project.model.Report;
import com.freedomPass.project.model.ReportFilter;
import com.freedomPass.project.model.UserProfile;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("reportsDao")
public class ReportsDaoImpl extends AbstractDao<Long, Report> implements ReportsDao {

    @Autowired
    ReportFiltersDao reportFiltersDao;

    @Autowired
    NetworkFileManager networkFileManager;

    @Autowired
    ContextHolder context;

    @Override
    public List<Report> getReports() {
        Criteria criteria = createEntityCriteria()
                .addOrder(Order.asc("id"));
        List<Report> reports = (List<Report>) criteria.list();
        for (Report report : reports) {
            Hibernate.initialize(report.getReportFilters());
            Hibernate.initialize(report.getReportStyleCollection());
        }
        return reports;
    }

    @Override
    public List<Report> getReportsBasedOnRoles(Long id) {
        Criteria criteria = createEntityCriteria()
                .addOrder(Order.asc("name"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Report> reports = (List<Report>) criteria.list();
        List<Report> returnedReports = new ArrayList<Report>();
        for (Report report : reports) {
            boolean visibleReport = false;
            Hibernate.initialize(report.getReportFilters());
            Hibernate.initialize(report.getReportStyleCollection());
            Hibernate.initialize(report.getGroupCollection());
            for (Group group : report.getGroupCollection()) {
                Hibernate.initialize(group.getUserProfileCollection());
                for (UserProfile user : group.getUserProfileCollection()) {
                    if (user.getId() == id) {
                        visibleReport = true;
                        break;
                    }
                }
                if (visibleReport) {
                    break;
                }
            }
            if (visibleReport) {
                returnedReports.add(report);
            }
        }
        return returnedReports;
    }

    @Override
    public Report getReport(Long id) {
        Report report = getByKey(id);
        Hibernate.initialize(report.getReportFilters());
        Hibernate.initialize(report.getReportStyleCollection());
        return report;
    }

    @Override
    public Report addReport(Report report) {
        persist(report);
        return report;
    }

    @Override
    public String callStoredProcedure(Long procID, Integer pageNumber, Integer offset, Integer withCounter, Map<String, String[]> filters, Integer withLimit) throws SQLException, Exception {
        String sqlQuery = "";
        try {
            Report report = getByKey(procID);
            sqlQuery = getFinalQuery(filters, report.getId(), report.getProcName());
            Session session = getSession();
            SessionImpl sessionImpl = (SessionImpl) session;
            Connection connection = sessionImpl.connection();
            CallableStatement callableStatement = connection.prepareCall("CALL reportsProcedure(?,?,?,?,?, ?)");
            callableStatement.setString(1, sqlQuery);
            callableStatement.setInt(2, withCounter);
            callableStatement.setInt(3, pageNumber);
            callableStatement.setInt(4, offset);
            callableStatement.setInt(5, withLimit);
            callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
            ResultSet resultset = callableStatement.executeQuery();
            Integer rowCount = 0;
            if (withCounter == 1) {
                rowCount = callableStatement.getInt(6);
            }
            String jsonArray = Utils.convertResultSetIntoJSON(resultset, rowCount);
            resultset.close();
            return jsonArray;
        } catch (Exception ex) {
            Logger.ERROR(ex.toString(), sqlQuery, "");
        }
        return "";
    }

    @Override
    public String exportToExcel(Long procID, Map<String, String[]> filters) throws ParseException, SQLException {
        try {
            Report report = getByKey(procID);
            String sqlQuery = getFinalQuery(filters, report.getId(), report.getProcName());
            String finalSqlQuery = getQueryWithUnionForExcel(sqlQuery);
            String localMySQLPath = context.getEnvironment().getProperty("shared.mysql.local_dump_folder");
            if (localMySQLPath == null) {
                localMySQLPath = context.getCatalina().getCatalinaWorkInstanceDir() + "\\MySQLDumps\\";
            }
            double randNumber = (Math.random() * ((9999 - 1111) + 1)) + 1111;
            String fileName = "" + System.currentTimeMillis() % 1000 + "" + randNumber;
            Session session = getSession();
            SessionImpl sessionImpl = (SessionImpl) session;
            Connection connection = sessionImpl.connection();
            CallableStatement callableStatement = connection.prepareCall(" " + finalSqlQuery + "\n"
                    + " INTO OUTFILE '" + localMySQLPath.replace("\\", "\\\\") + "\\\\" + fileName + ".csv'\n"
                    + " FIELDS TERMINATED BY ','\n"
                    + " OPTIONALLY ENCLOSED BY '\"'\n"
                    + " LINES TERMINATED BY '\\n'");
            ResultSet resultset = callableStatement.executeQuery();
            resultset.close();
            if (!localMySQLPath.equals(context.getCatalina().getCatalinaWorkInstanceDir() + "\\MySQLDumps\\")) {
                networkFileManager.downloadFileFromMySQLNetworkNode("" + fileName + ".csv", "", context.getCatalina().getCatalinaWorkInstanceDir() + "/MySQLDumps/", true);
            }
            return context.getCatalina().getCatalinaWorkInstanceDir() + "/MySQLDumps/" + fileName + ".csv";
        } catch (Exception ex) {
            Logger.ERROR(ex.toString(), "", "");
            return "";
        }
    }

//    private String getQueryWithUnionForExcel(String sqlQuery) {
//        try {
//            String finalSqlQuery = "";
//            String[] querySplit = sqlQuery.split("(?i)from");
//            String[] selectFields = querySplit[0].split("(?i)select")[1].split(",");
//            List<String> fieldList = new ArrayList<>();
//            if (selectFields.length == 1 && selectFields[0].contains("*")) {
//                finalSqlQuery = String.join(" FROM ", querySplit);
//            } else {
//                for (String field : selectFields) {
//                    if (field.toLowerCase().contains(" as ")) {
//                        fieldList.add(field.split("(?i) as ")[1]);
//                    } else {
//                        fieldList.add(field);
//                    }
//                }
//                String[] finalQuerySplit = Arrays.copyOfRange(querySplit, 1, querySplit.length);
//                finalSqlQuery = "SELECT " + String.join(", ", fieldList) + " UNION ALL " + String.join(" FROM ", querySplit);
//            }
//            return finalSqlQuery;
//        } catch (Exception ex) {
//            Logger.ERROR(ex.toString(), "", "");
//        }
//        return "";
//    }
    private String getQueryWithUnionForExcel(String sqlQuery) {
        String finalSqlQuery = "";
        try {
            String[] querySplit = sqlQuery.split("(?i)from");
            String[] selectFields = querySplit[0].split("(?i)select")[1].split(",");
            List<String> fieldList = new ArrayList<>();
            if (selectFields.length == 1 && selectFields[0].contains("*")) {
                finalSqlQuery = String.join(" FROM ", querySplit);
            } else {
                String[] commaSplit = sqlQuery.split(",");
                for (int i = 0; i < commaSplit.length; i++) {
                    String bits[] = commaSplit[i].split("(?i) as ");
                    if (i == commaSplit.length - 1) {

                        fieldList.add(bits[bits.length - 1].split("(?i)from")[0]);
                    } else {
                        fieldList.add(bits[bits.length - 1]);
                    }
                }
                finalSqlQuery = "SELECT " + String.join(", ", fieldList) + " UNION ALL " + sqlQuery;
            }
            return finalSqlQuery;
        } catch (Exception ex) {
            Logger.ERROR(ex.toString(), finalSqlQuery, "");
        }
        return "";
    }

    private String getFinalQuery(Map<String, String[]> filters, Long reportID, String query) throws ParseException {
        try {
            HashMap<Integer, String> indexOfFilters = new HashMap<Integer, String>();
            for (Map.Entry<String, String[]> entry : filters.entrySet()) {
                String fieldName = entry.getKey();
                String fieldValue = entry.getValue()[0].replace("'", "\\'");
                Integer arrayLength = fieldName.split("-").length;
                boolean dummyField = false;
                if (arrayLength > 1 && (fieldName.split("-")[1].equals("NUMBER") || fieldName.split("-")[1].equals("TO"))) {
                    dummyField = true;
                }
                if (!dummyField) {
                    ReportFilter reportFilter = reportFiltersDao.getTypeByNameAndReport(fieldName, reportID);
                    if (reportFilter.getFieldType().equals("TEXT")) {
                        if (fieldValue.equals("")) {
                            this.setValueInHashmap(indexOfFilters, reportFilter.getFieldIndex(), " LIKE " + reportFilter.getReportField());
                        } else {
                            this.setValueInHashmap(indexOfFilters, reportFilter.getFieldIndex(), " LIKE " + "'%" + fieldValue + "%'");
                        }
                    } else if (reportFilter.getFieldType().equals("NUMBER")) {
                        String operation = filters.get(fieldName + "-NUMBER")[0];
                        if (operation.equals("")) {
                            operation = "=";
                        }
                        if (fieldValue.equals("")) {
                            this.setValueInHashmap(indexOfFilters, reportFilter.getFieldIndex(), " = " + reportFilter.getReportField());
                        } else {
                            this.setValueInHashmap(indexOfFilters, reportFilter.getFieldIndex(), operation + " " + fieldValue);
                        }
                    } else if (reportFilter.getFieldType().equals("DROPDOWN")) {
                        if (fieldValue.equals("")) {
                            this.setValueInHashmap(indexOfFilters, reportFilter.getFieldIndex(), " = " + reportFilter.getReportField());
                        } else {
                            this.setValueInHashmap(indexOfFilters, reportFilter.getFieldIndex(), " = " + "'" + fieldValue + "'");
                        }
                    } else if (reportFilter.getFieldType().equals("DATE")) {
                        String fromValue = fieldValue;
                        String toValue = filters.get(fieldName + "-TO")[0];
                        if (!fromValue.equals("") && !toValue.equals("")) {
                            this.setValueInHashmap(indexOfFilters, reportFilter.getFieldIndex(), " between '" + convertDateToQuery(new Date(fromValue), false) + "' and '" + convertDateToQuery(new Date(toValue), true) + "'");
                        } else if (!fromValue.equals("")) {
                            this.setValueInHashmap(indexOfFilters, reportFilter.getFieldIndex(), " >= '" + convertDateToQuery(new Date(fromValue), false) + "'");
                        } else if (!toValue.equals("")) {
                            this.setValueInHashmap(indexOfFilters, reportFilter.getFieldIndex(), " <= '" + convertDateToQuery(new Date(toValue), true) + "'");
                        } else {
                            this.setValueInHashmap(indexOfFilters, reportFilter.getFieldIndex(), " = " + reportFilter.getReportField());
                        }
                    }
                }
            }
            Map<Integer, String> treeMap = new TreeMap<Integer, String>(indexOfFilters);
            for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
                query = query.replaceFirst("\\?", entry.getValue());
            }
            return query;
        } catch (Exception ex) {
            Logger.ERROR(ex.toString(), query, "");
        }
        return "";
    }

    private static String convertDateToQuery(Date date, boolean isTo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (isTo) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        int month = calendar.get(Calendar.MONTH) + 1; //based on zero
        return calendar.get(Calendar.YEAR) + "-" + month + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    private HashMap<Integer, String> setValueInHashmap(HashMap<Integer, String> indexOfFilters, String fieldIndexes, String fieldValue) {
        for (String fieldIndex : fieldIndexes.split(",")) {
            indexOfFilters.put(Integer.parseInt(fieldIndex), fieldValue);
        }
        return indexOfFilters;
    }

    @Override
    public void deleteReport(Report report) {
        delete(report);
    }
}
