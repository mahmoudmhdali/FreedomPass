package com.freedomPass.project.model;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "TBL_USER_OUTLET_INFO")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserOutletInfo implements Serializable {

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
    private String country;

    @Basic(optional = false)
    @Column(name = "INFO")
    private String info;

    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private UserProfile userProfileId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userOutletInfo", cascade = CascadeType.ALL)
    private Collection<UserOutletInfoImages> userOutletInfoImagesCollection;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userOutletInfo", cascade = CascadeType.ALL)
    private Collection<UserOutletInfoLocations> userOutletInfoLocationsCollection;

    @JoinTable(name = "TBL_USER_OUTLET_INFO_CATEGORY", inverseJoinColumns = {
        @JoinColumn(name = "OUTLET_CATEGORY_ID", referencedColumnName = "ID")}, joinColumns = {
        @JoinColumn(name = "USER_OUTLET_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<OutletCategory> outletCategoryCollection;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userOutletInfo", cascade = CascadeType.ALL)
    private Collection<UserOutletOffer> userOutletOffers;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
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

    public Collection<OutletCategory> getOutletCategoryCollection() {
        return outletCategoryCollection;
    }

    public void setOutletCategoryCollection(Collection<OutletCategory> outletCategoryCollection) {
        this.outletCategoryCollection = outletCategoryCollection;
    }

    public Collection<UserOutletInfoImages> getUserOutletInfoImagesCollection() {
        return userOutletInfoImagesCollection;
    }

    public void setUserOutletInfoImagesCollection(Collection<UserOutletInfoImages> userOutletInfoImagesCollection) {
        this.userOutletInfoImagesCollection = userOutletInfoImagesCollection;
    }

    public Collection<UserOutletInfoLocations> getUserOutletInfoLocationsCollection() {
        return userOutletInfoLocationsCollection;
    }

    public void setUserOutletInfoLocationsCollection(Collection<UserOutletInfoLocations> userOutletInfoLocationsCollection) {
        this.userOutletInfoLocationsCollection = userOutletInfoLocationsCollection;
    }

    public UserProfile getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(UserProfile userProfileId) {
        this.userProfileId = userProfileId;
    }

    public Collection<UserOutletOffer> getUserOutletOffers() {
        return userOutletOffers;
    }

    public void setUserOutletOffers(Collection<UserOutletOffer> userOutletOffers) {
        this.userOutletOffers = userOutletOffers;
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
        if (!(object instanceof UserOutletInfo)) {
            return false;
        }
        UserOutletInfo other = (UserOutletInfo) object;
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
