/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.dao;

import com.freedomPass.project.helpermodel.DropDownValues;
import com.freedomPass.project.model.ReportFilter;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("reportFiltersDao")
public class ReportFiltersDaoImpl extends AbstractDao<Long, ReportFilter> implements ReportFiltersDao {

    @Override
    public List<ReportFilter> getFiltersByReportId(Long reportId) {
        Criteria criteria = createEntityCriteria()
                .createAlias("report", "reports")
                .add(Restrictions.eq("reports.id", reportId))
                .addOrder(Order.asc("id"));
        List<ReportFilter> reportfilters = (List<ReportFilter>) criteria.list();
        return reportfilters;
    }
    
    @Override
    public ReportFilter getTypeByNameAndReport(String name, Long reportId){
        Criteria criteria = createEntityCriteria()
                .createAlias("report", "reports")
                .add(Restrictions.eq("reports.id", reportId))
                .add(Restrictions.eq("reportField", name))
                .addOrder(Order.asc("id"));
        ReportFilter reportfilter = (ReportFilter) criteria.uniqueResult();
        return reportfilter;
    }
    
    @Override
    public List<DropDownValues> getDropDownByReportFilterId(Long reportFilterId){
        ReportFilter reportFilter = getByKey(reportFilterId);
        Query query = createSqlQuery(reportFilter.getSelectQuery())
                .setResultTransformer(Transformers.aliasToBean(DropDownValues.class));
        List<DropDownValues> dropDownValues = (List<DropDownValues>) query.list();
        return dropDownValues;
    }

    @Override
    public Integer deleteSelection(List<Long> ids) {
        if (ids.size() == 0) {
            return 0;
        }
        StringBuilder commaSeperatedIds = new StringBuilder();
        for (Long id : ids) {
            if (commaSeperatedIds.length() > 0) {
                commaSeperatedIds.append(",").append(id);
            } else {
                commaSeperatedIds.append(id);
            }
        }
        Integer totalFetchedData;
        totalFetchedData = createSqlQuery("DELETE FROM tbl_reports_filter WHERE"
                + " ID IN (" + commaSeperatedIds + ")").executeUpdate();
        return totalFetchedData;
    }

}
