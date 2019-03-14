package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "TBL_USER_COMPANY_INFO")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserCompanyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @Column(name = "COUNTRY")
    private Integer country;

    @Basic(optional = false)
    @Column(name = "INFO")
    @Size(min = 5, max = 300, message = "validation.userInfo.infoRange")
    @NotBlank(message = "validation.userInfo.infoRequired")
    private String info;

    @JsonIgnore
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private UserProfile userProfileId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userCompanyInfo", cascade = CascadeType.ALL)
    private Collection<UserCompanyInfoImages> userCompanyInfoImagesCollection;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userCompanyInfo", cascade = CascadeType.ALL)
    private Collection<UserCompanyInfoLocations> userCompanyInfoLocationsCollection;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userCompanyInfo", cascade = CascadeType.ALL)
    private Collection<UserCompanyPasses> userCompanyPasses;

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

    @Transient
    private String userCompanyName;

    @Transient
    private Long userCompanyID;

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

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<UserCompanyInfoImages> getUserCompanyInfoImagesCollection() {
        return userCompanyInfoImagesCollection;
    }

    public void setUserCompanyInfoImagesCollection(Collection<UserCompanyInfoImages> userCompanyInfoImagesCollection) {
        this.userCompanyInfoImagesCollection = userCompanyInfoImagesCollection;
    }

    public Collection<UserCompanyInfoLocations> getUserCompanyInfoLocationsCollection() {
        return userCompanyInfoLocationsCollection;
    }

    public void setUserCompanyInfoLocationsCollection(Collection<UserCompanyInfoLocations> userCompanyInfoLocationsCollection) {
        this.userCompanyInfoLocationsCollection = userCompanyInfoLocationsCollection;
    }

    public UserProfile getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(UserProfile userProfileId) {
        this.userProfileId = userProfileId;
    }

    public Collection<UserCompanyPasses> getUserCompanyPasses() {
        return userCompanyPasses;
    }

    public void setUserCompanyPasses(Collection<UserCompanyPasses> userCompanyPasses) {
        this.userCompanyPasses = userCompanyPasses;
    }

    @JsonGetter(value = "userCompanyName")
    public String getUserCompanyName() {
        userCompanyName = userProfileId.getName();
        return userCompanyName;
    }

    @JsonGetter(value = "userCompanyID")
    public Long getUserCompanyID() {
        userCompanyID = userProfileId.getId();
        return userCompanyID;
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
        if (!(object instanceof UserCompanyInfo)) {
            return false;
        }
        UserCompanyInfo other = (UserCompanyInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String userProfileIdString = "";
        if (Hibernate.isInitialized(userProfileId)) {
            userProfileIdString = Objects.toString(userProfileId);
        }
        return "\"com.freedomPass.project.model.UserProfile\" : {\"id\" : \"" + id + "\","
                + "\"info\" : \"" + info + "\","
                + "\"country\" : \"" + country + "\","
                + "\"userProfileId\" : " + userProfileIdString + "}";
    }
}
