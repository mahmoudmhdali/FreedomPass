/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_reports_filter")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ReportFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.citerneApp.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @Column(name = "REPORT_FIELD")
    private String reportField;

    @Basic(optional = false)
    @Column(name = "FIELD_INDEX")
    private String fieldIndex;

    @Basic(optional = false)
    @Column(name = "FIELD_TYPE")
    private String fieldType;

    @Basic(optional = false)
    @Column(name = "REQUIRED")
    private boolean required;

    @Basic(optional = false)
    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @JoinColumn(name = "REPORT_ID", referencedColumnName = "ID", nullable = false)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Report report;

    @Basic(optional = false)
    @Column(name = "SELECT_QUERY")
    private String selectQuery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReportField(String reportField) {
        this.reportField = reportField;
    }

    public String getReportField() {
        return reportField;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setSelectQuery(String selectQuery) {
        this.selectQuery = selectQuery;
    }

    public String getSelectQuery() {
        return selectQuery;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFieldIndex() {
        return fieldIndex;
    }

    public void setFieldIndex(String fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @XmlTransient
    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public String toString() {
        String reportString = "";
        if (Hibernate.isInitialized(report)) {
            reportString = Objects.toString(report);
        }
        return "\"com.citerneApp.project.model.ReportStyle\" : {\"id\" : \"" + id + "\","
                + "\"displayName\" : \"" + displayName + "\","
                + "\"selectQuery\" : \"" + selectQuery + "\","
                + "\"reportField\" : \"" + reportField + "\","
                + "\"fieldIndex\" : \"" + fieldIndex + "\","
                + "\"fieldType\" : \"" + fieldType + "\","
                + "\"required\" : \"" + required + "\","
                + "\"report\" : " + reportString + "}";
    }
}
