package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "TBL_GROUPS")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 5, max = 30, message = "validation.group.nameRange")
    @Column(name = "NAME")
    private String name;

    @Basic(optional = false)
    @NotBlank(message = "validation.group.descriptionRequired")
    @Column(name = "DESCRIPTION")
    private String description;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @JoinTable(name = "TBL_USER_PROFILE_GROUPS", joinColumns = {
        @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "USER_PROFILE_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<UserProfile> userProfileCollection;

    @JoinTable(name = "TBL_GROUPS_ROLES", inverseJoinColumns = {
        @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}, joinColumns = {
        @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Role> roleCollection;

    @JoinTable(name = "TBL_GROUPS_REPORTS", inverseJoinColumns = {
        @JoinColumn(name = "REPORT_ID", referencedColumnName = "ID")}, joinColumns = {
        @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Report> reportCollection;

    public Group() {
    }

    public Group(Long id) {
        this.id = id;
    }

    public Group(Long id, String role) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    @XmlTransient
    public Collection<UserProfile> getUserProfileCollection() {
        return userProfileCollection;
    }

    public void setUserProfileCollection(Collection<UserProfile> userProfileCollection) {
        this.userProfileCollection = userProfileCollection;
    }

    @XmlTransient
    public Collection<Role> getRoleCollection() {
        return roleCollection;
    }

    public void setRoleCollection(Collection<Role> roleCollection) {
        this.roleCollection = roleCollection;
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
        if (!(object instanceof Group)) {
            return false;
        }
        Group other = (Group) object;
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
        String userProfileCollectionString = "";
        if (Hibernate.isInitialized(userProfileCollection)) {
            userProfileCollectionString = Objects.toString(userProfileCollection);
        }
        String roleCollectionString = "";
        if (Hibernate.isInitialized(roleCollection)) {
            roleCollectionString = Objects.toString(roleCollection);
        }
        return "\"com.freedomPass.project.model.Group\" : {\"id\" : \"" + id + "\","
                + "\"name\" : \"" + name + "\","
                + "\"description\" : \"" + description + "\","
                + "\"reportCollection\" : \"" + reportCollectionString + "\","
                + "\"userProfileCollection\" : \"" + userProfileCollectionString + "\","
                + "\"roleCollection\" : " + roleCollectionString + "}";
    }

}
