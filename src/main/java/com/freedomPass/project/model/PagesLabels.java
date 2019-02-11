package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_pages_labels")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class PagesLabels implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Column(name = "page_id", nullable = false)
    @NotNull
    private long pageid;

    @Column(name = "label_id", nullable = false)
    @NotNull
    private long labelid;

    @Column(name = "label_name", nullable = false)
    @NotNull
    private String labelname;

    @JoinColumn(name = "lang_id", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Language language;

    @Column(name = "label", nullable = false)
    @NotNull
    private String label;

    @Column(name = "label_level")
    private int labellevel;

    @Column(name = "index_legend")
    private String indexlegend;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getpageId() {
        return pageid;
    }

    public void setpageId(long pageid) {
        this.pageid = pageid;
    }

    public long getlabelId() {
        return labelid;
    }

    public void setlabelId(long labelid) {
        this.labelid = labelid;
    }

    public String getlabelname() {
        return labelname;
    }

    public void setlabelname(String labelname) {
        this.labelname = labelname;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getlabel() {
        return label;
    }

    public void settitle(String label) {
        this.label = label;
    }

    public int getlabellevel() {
        return labellevel;
    }

    public void setlabellevel(int labellevel) {
        this.labellevel = labellevel;
    }

    public String getindexlegend() {
        return indexlegend;
    }

    public void setindexlegend(String indexlegend) {
        this.indexlegend = indexlegend;
    }

}
