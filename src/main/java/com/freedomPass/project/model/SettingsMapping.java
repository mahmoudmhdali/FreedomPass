/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.project.model;

import com.freedomPass.project.helpermodel.DropDown;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@JsonInclude(JsonInclude.Include.ALWAYS)
@Entity
@Table(name = "tbl_settings_mapping")
@XmlRootElement
public class SettingsMapping implements Serializable {

    private static final long serialVersionUID = 7686183869226625109L;

    @Column(name = "COLUMNID", nullable = false)
    @Id
    @Basic(optional = false)
    private Long COLUMNID;

    @Column(name = "COLUMNNAME", nullable = false)
    @Basic(optional = false)
    private String COLUMNNAME;

    @Column(name = "COLUMNDESCRIPTION", nullable = false)
    @Basic(optional = false)
    private String COLUMNDESCRIPTION;

    @Column(name = "LABELDISPLAY", nullable = false)
    @Basic(optional = false)
    private String LABELDISPLAY;

    @Column(name = "FIELDTYPE", nullable = false)
    @Basic(optional = false)
    private String FIELDTYPE;

    @Column(name = "QUERYTEXT", nullable = true)
    @Basic(optional = false)
    private String QUERYTEXT;
    
    @Column(name = "RELATEDCOLUMNS", nullable = true) //, columnDefinition = "Number default 0"
    @Basic(optional = false)
    private int RELATEDCOLUMNS;

    @Column(name = "COLUMNVALUE", nullable = true)
    @Basic(optional = false)
    private String COLUMNVALUE;

    @Column(name = "ENABLED", nullable = true)
    @Basic(optional = false)
    private int ENABLED;

    @Column(name = "EDITABLE", nullable = true)
    @Basic(optional = false)
    private int EDITABLE;

    @Column(name = "SUBTABLENAME", nullable = true)
    @Basic(optional = false)
    private String SUBTABLENAME;

    @Column(name = "AUTOINC", nullable = false)
    @Basic(optional = false)
    private int AUTOINC;

    @Column(name = "UNIQUEVALUE", nullable = false)
    @Basic(optional = false)
    private int UNIQUEVALUE;

    @Column(name = "MANDATORY", nullable = false)
    @Basic(optional = false)
    private int MANDATORY;

    @Column(name = "RELATEDCOLNAME", nullable = false)
    @Basic(optional = false)
    private String RELATEDCOLNAME;

    @Column(name = "RELATEDAUTOINCCOLNAME", nullable = false)
    @Basic(optional = false)
    private String RELATEDAUTOINCCOLNAME;

    @Column(name = "COLUMNCATEGORY", nullable = false)
    @Basic(optional = false)
    private String COLUMNCATEGORY;

    @Column(name = "ISADMIN", nullable = false)
    @Basic(optional = false)
    private int ISADMIN;

    public transient List<DropDown> FIELDOPTIONS;

    public transient List<SettingsMapping> RELATEDFIELDS;

    public transient String ACCORDIONOPTIONS;

    public String getSubTableName() {
        return SUBTABLENAME;
    }

    public void setSubTableName(String SUBTABLENAME) {
        this.SUBTABLENAME = SUBTABLENAME;
    }

    public int getENABLED() {
        return ENABLED;
    }

    public void setENABLED(int ENABLED) {
        this.ENABLED = ENABLED;
    }

    public int getEDITABLE() {
        return EDITABLE;
    }

    public void setEDITABLE(int EDITABLE) {
        this.EDITABLE = EDITABLE;
    }

    public String getAccordionOptions() {
        return ACCORDIONOPTIONS;
    }

    public void setAccordionOptions(String ACCORDIONOPTIONS) {
        this.ACCORDIONOPTIONS = ACCORDIONOPTIONS;
    }

    public List<SettingsMapping> getRelatedFields() {
        return RELATEDFIELDS;
    }

    public void setRelatedFields(List<SettingsMapping> RELATEDFIELDS) {
        this.RELATEDFIELDS = RELATEDFIELDS;
    }

    public Long getColumnId() {
        return COLUMNID;
    }

    public void setCOLUMNID(Long ColumnId) {
        this.COLUMNID = ColumnId;
    }

    public String getColumnName() {
        return COLUMNNAME;
    }

    public void setColumnName(String COLUMNNAME) {
        this.COLUMNNAME = COLUMNNAME;
    }

    public String getColumnDescription() {
        return COLUMNDESCRIPTION;
    }

    public void setColumnDescription(String COLUMNDESCRIPTION) {
        this.COLUMNDESCRIPTION = COLUMNDESCRIPTION;
    }

    public String getLabelDisplay() {
        return LABELDISPLAY;
    }

    public void setLabelDisplay(String LABELDISPLAY) {
        this.LABELDISPLAY = LABELDISPLAY;
    }

    public String getFieldType() {
        return FIELDTYPE;
    }

    public void setFieldType(String FIELDTYPE) {
        this.FIELDTYPE = FIELDTYPE;
    }

    public String getQueryText() {
        return QUERYTEXT;
    }

    public void setQueryText(String QUERYTEXT) {
        this.QUERYTEXT = QUERYTEXT;
    }

    public int getRelatedColumns() {
        return RELATEDCOLUMNS;
    }

    public void setRelatedColumns(int RELATEDCOLUMNS) {
        this.RELATEDCOLUMNS = RELATEDCOLUMNS;
    }

    public String getColumnValue() {
        return COLUMNVALUE;
    }

    public void setColumnValue(String COLUMNVALUE) {
        this.COLUMNVALUE = COLUMNVALUE;
    }

    public List<DropDown> getfieldOptions() {
        return FIELDOPTIONS;
    }

    public void setfieldOptions(List<DropDown> fieldOptions) {
        this.FIELDOPTIONS = fieldOptions;
    }

    public int getAutoInc() {
        return AUTOINC;
    }

    public void setAutoInc(int aUTOINC) {
        this.AUTOINC = aUTOINC;
    }

    public int getUniqueValue() {
        return UNIQUEVALUE;
    }

    public void setUniqueValue(int uNIQUEVALUE) {
        UNIQUEVALUE = uNIQUEVALUE;
    }

    public int getmandatory() {
        return MANDATORY;
    }

    public void setmandatory(int mANDATORY) {
        MANDATORY = mANDATORY;
    }

    public String getrelatedColName() {
        return RELATEDCOLNAME;
    }

    public void setrelatedColName(String rELATEDCOLNAME) {
        RELATEDCOLNAME = rELATEDCOLNAME;
    }

    public String getrelatedAutoIncColName() {
        return RELATEDAUTOINCCOLNAME;
    }

    public void setrelatedAutoIncColName(String rELATEDAUTOINCCOLNAME) {
        RELATEDAUTOINCCOLNAME = rELATEDAUTOINCCOLNAME;
    }

    public String getColumnCategory() {
        return COLUMNCATEGORY;
    }

    public void setColumnCategory(String cOLUMNCATEGORY) {
        COLUMNCATEGORY = cOLUMNCATEGORY;
    }

    public int getIsAdmin() {
        return ISADMIN;
    }

    public void setIsAdmin(int iSADMIN) {
        ISADMIN = iSADMIN;
    }

}
