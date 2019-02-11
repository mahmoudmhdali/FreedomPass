/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tbl_reports")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @Column(name = "NAME")
    @NotBlank(message = "validation.action.nameRequired")
    private String name;

    @Basic(optional = false)
    @Column(name = "PROC_NAME")
    @NotBlank(message = "validation.action.nameRequired")
    private String procName;

    @Basic(optional = false)
    @Column(name = "LAST_QUERY_CONTAINS_WHERE")
    private boolean lastQueryContainsWhere;

    @Basic(optional = false)
    @Column(name = "LAST_QUERY_CONTAINS_ORDER")
    private boolean lastQueryContainsOrder;

    @Basic(optional = false)
    @Column(name = "LAST_QUERY_CONTAINS_GROUP")
    private boolean lastQueryContainsGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.ALL)
    private List<ReportFilter> reportFilters;

    @JoinTable(name = "TBL_REPORTS_STYLE_JOIN", inverseJoinColumns = {
        @JoinColumn(name = "REPORT_STYLE_ID", referencedColumnName = "ID")}, joinColumns = {
        @JoinColumn(name = "REPORT_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<ReportStyle> reportStyleCollection;

    @JoinTable(name = "TBL_GROUPS_REPORTS", joinColumns = {
        @JoinColumn(name = "REPORT_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Group> groupCollection;

    @Basic(optional = false)
    @Column(name = "CHART_TITLE")
    private String chartTitle;

    @Basic(optional = false)
    @Column(name = "CHART_SUBTITLE")
    private String chartSubTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getLastQueryContainsWhere() {
        return lastQueryContainsWhere;
    }

    public void setLastQueryContainsWhere(boolean lastQueryContainsWhere) {
        this.lastQueryContainsWhere = lastQueryContainsWhere;
    }

    public boolean getLastQueryContainsOrder() {
        return lastQueryContainsOrder;
    }

    public void setLastQueryContainsOrder(boolean lastQueryContainsOrder) {
        this.lastQueryContainsOrder = lastQueryContainsOrder;
    }

    public boolean getLastQueryContainsGroup() {
        return lastQueryContainsGroup;
    }

    public void setLastQueryContainsGroup(boolean lastQueryContainsGroup) {
        this.lastQueryContainsGroup = lastQueryContainsGroup;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartSubTitle(String chartSubTitle) {
        this.chartSubTitle = chartSubTitle;
    }

    public String getChartSubTitle() {
        return chartSubTitle;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getProcName() {
        return procName;
    }

    @XmlTransient
    public void setReportFilters(List<ReportFilter> reportFilters) {
        this.reportFilters = reportFilters;
    }

    public List<ReportFilter> getReportFilters() {
        return reportFilters;
    }

    @XmlTransient
    public Collection<Group> getGroupCollection() {
        return groupCollection;
    }

    public void setGroupCollection(Collection<Group> groupCollection) {
        this.groupCollection = groupCollection;
    }

    @XmlTransient
    public Collection<ReportStyle> getReportStyleCollection() {
        return reportStyleCollection;
    }

    public void setReportStyleCollection(Collection<ReportStyle> reportStyleCollection) {
        this.reportStyleCollection = reportStyleCollection;
    }

    @Override
    public String toString() {
        String groupCollectionString = "";
        if (Hibernate.isInitialized(groupCollection)) {
            groupCollectionString = Objects.toString(groupCollection);
        }
        String reportFiltersString = "";
        if (Hibernate.isInitialized(reportFilters)) {
            reportFiltersString = Objects.toString(reportFilters);
        }
        String reportStyleCollectionString = "";
        if (Hibernate.isInitialized(reportStyleCollection)) {
            reportStyleCollectionString = Objects.toString(reportStyleCollection);
        }
        return "\"com.freedomPass.project.model.Group\" : {\"id\" : \"" + id + "\","
                + "\"name\" : \"" + name + "\","
                + "\"procName\" : \"" + procName + "\","
                + "\"lastQueryContainsWhere\" : \"" + lastQueryContainsWhere + "\","
                + "\"lastQueryContainsOrder\" : \"" + lastQueryContainsOrder + "\","
                + "\"lastQueryContainsGroup\" : \"" + lastQueryContainsGroup + "\","
                + "\"chartSubTitle\" : \"" + chartSubTitle + "\","
                + "\"chartTitle\" : \"" + chartTitle + "\","
                + "\"reportFilters\" : \"" + reportFiltersString + "\","
                + "\"reportStyleCollection\" : \"" + reportStyleCollectionString + "\","
                + "\"groupCollection\" : " + groupCollectionString + "}";
    }
}
