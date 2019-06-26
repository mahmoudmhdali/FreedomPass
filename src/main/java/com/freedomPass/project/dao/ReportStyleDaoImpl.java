/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.dao;

import com.freedomPass.project.model.ReportStyle;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository("reportStyleDao")
public class ReportStyleDaoImpl extends AbstractDao<Long, ReportStyle> implements ReportStyleDao {

    @Override
    public List<ReportStyle> getReportStyles() {
        Criteria criteria = createEntityCriteria()
                .addOrder(Order.asc("id"));
        List<ReportStyle> reportStyle = (List<ReportStyle>) criteria.list();
        return reportStyle;
    }

    @Override
    public ReportStyle getReportStyle(Long id) {
        ReportStyle reportStyle = getByKey(id);
        return reportStyle;
    }

}
