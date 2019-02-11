package com.freedomPass.project.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_general_dashboard")
@XmlRootElement
public class DashBoard implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private long id;

    @Column(name = "item_key", nullable = false)
    @NotNull
    private String itemkey;

    private transient String title;

    private transient String subTitle;

    @Column(name = "counter_value")
    private String counterValue;

    private transient String counterValueBackend;

    @Column(name = "item_type", nullable = false)
    @NotNull
    private int itemType;

    @Column(name = "query", nullable = false)
    @NotNull
    private String query;

    @Column(name = "filters")
    private String filters;

    @Column(name = "enabled", nullable = false)
    @NotNull
    private int enabled;

    @Column(name = "colors", nullable = false)
    @NotNull
    private String colors;

    @Column(name = "xaxiscolumn")
    private String xaxiscolumn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getitemkey() {
        return itemkey;
    }

    public void setitemkey(String itemkey) {
        this.itemkey = itemkey;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getsubTitle() {
        return subTitle;
    }

    public void setsubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getcounterValue() {
        return counterValue;
    }

    public void setcounterValue(String counterValue) {
        this.counterValue = counterValue;
    }

    public String getCounterValueBackend() {
        return counterValueBackend;
    }

    public void setCounterValueBackend(String counterValueBackend) {
        this.counterValueBackend = counterValueBackend;
    }

    public int getitemType() {
        return itemType;
    }

    public void setitemType(int itemType) {
        this.itemType = itemType;
    }

    public String getquery() {
        return query;
    }

    public void setquery(String query) {
        this.query = query;
    }

    public String getfilters() {
        return filters;
    }

    public void setfilters(String filters) {
        this.filters = filters;
    }

    public int getenabled() {
        return enabled;
    }

    public void setenabled(int enabled) {
        this.enabled = enabled;
    }

    public String getcolors() {
        return colors;
    }

    public void setcolors(String colors) {
        this.colors = colors;
    }

    public String getxaxiscolumn() {
        return xaxiscolumn;
    }

    public void setxaxiscolumn(String xaxiscolumn) {
        this.xaxiscolumn = xaxiscolumn;
    }
}
