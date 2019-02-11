package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TBL_REPORTS_STYLE")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ReportStyle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    @Column(name = "NAME")
    private String name;

    @JoinTable(name = "TBL_REPORTS_STYLE_JOIN", joinColumns = {
        @JoinColumn(name = "REPORT_STYLE_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "REPORT_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Report> reportCollection;

    public ReportStyle() {
    }

    public ReportStyle(Long id) {
        this.id = id;
    }

    public ReportStyle(Long id, String role) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Report> getReportCollection() {
        return reportCollection;
    }

    public void setReportCollection(Collection<Report> reportCollection) {
        this.reportCollection = reportCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportStyle)) {
            return false;
        }
        ReportStyle other = (ReportStyle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String reportCollectionString = "";
        if (Hibernate.isInitialized(reportCollection)) {
            reportCollectionString = Objects.toString(reportCollection);
        }
        return "\"com.freedomPass.project.model.ReportStyle\" : {\"id\" : \"" + id + "\","
                + "\"name\" : \"" + name + "\","
                + "\"reportCollection\" : " + reportCollectionString + "}";
    }

}
