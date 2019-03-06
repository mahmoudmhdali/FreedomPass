package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.freedomPass.project.model.validation.ValidName;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "TBL_USER_OUTLET_OFFER")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserOutletOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @Column(name = "NAME")
    @Size(min = 5, max = 20, message = "validation.userProfile.nameRange")
    @NotBlank(message = "validation.userProfile.nameRequired")
    @ValidName
    private String name;

    @Basic(optional = false)
    @Column(name = "INFO")
    @Size(min = 5, max = 300, message = "validation.userInfo.infoRange")
    @NotBlank(message = "validation.userInfo.infoRequired")
    private String info;

    @Transient
    private String imageName1;

    @Transient
    private String imageName2;

    @Transient
    private String imageName3;

    @Transient
    private String imageName4;

    @Basic(optional = false)
    @Column(name = "NUMBER_OF_USAGE")
    private Integer numberOfUsage;

    @Basic(optional = false)
    @Column(name = "VALIDITY")
    private Integer validity;

    @Basic(optional = false)
    @Column(name = "TYPE_OF_USAGE")
    private Integer typeOfUsage;

    @JsonIgnore
    @JoinColumn(name = "USER_OUTLET_INFO_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private UserOutletInfo userOutletInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userOutletOffer", cascade = CascadeType.ALL)
    private Collection<UserOutletOfferImages> userOutletOfferImagesCollection;

    @Transient
    private String userOutletName;

    @Transient
    private Long userOutletID;

    @JoinColumn(name = "TYPE", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private OutletOfferType outletOfferType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userOutletOffer", cascade = CascadeType.ALL)
    private Collection<UserOutletOfferPurchased> userOutletOfferPurchased;

    @JoinTable(name = "TBL_ADMIN_PASSES_OUTLET_OFFERS", joinColumns = {
        @JoinColumn(name = "OUTLET_OFFER_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "PASS_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<AdminPasses> adminPassesCollection;

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

    @JsonGetter(value = "userOutletName")
    public String getUserOutletName() {
        userOutletName = userOutletInfo.getUserProfileId().getName();
        return userOutletName;
    }

    @JsonGetter(value = "userOutletID")
    public Long getUserOutletID() {
        userOutletID = userOutletInfo.getId();
        return userOutletID;
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

    public Integer getNumberOfUsage() {
        return numberOfUsage;
    }

    public void setNumberOfUsage(Integer numberOfUsage) {
        this.numberOfUsage = numberOfUsage;
    }

    public Integer getTypeOfUsage() {
        return typeOfUsage;
    }

    public void setTypeOfUsage(Integer typeOfUsage) {
        this.typeOfUsage = typeOfUsage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName1() {
        return imageName1;
    }

    public void setImageName1(String imageName1) {
        this.imageName1 = imageName1;
    }

    public String getImageName2() {
        return imageName2;
    }

    public void setImageName2(String imageName2) {
        this.imageName2 = imageName2;
    }

    public String getImageName3() {
        return imageName3;
    }

    public void setImageName3(String imageName3) {
        this.imageName3 = imageName3;
    }

    public String getImageName4() {
        return imageName4;
    }

    public void setImageName4(String imageName4) {
        this.imageName4 = imageName4;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserOutletInfo getUserOutletInfo() {
        return userOutletInfo;
    }

    public void setUserOutletInfo(UserOutletInfo userOutletInfo) {
        this.userOutletInfo = userOutletInfo;
    }

    public OutletOfferType getOutletOfferType() {
        return outletOfferType;
    }

    public void setOutletOfferType(OutletOfferType outletOfferType) {
        this.outletOfferType = outletOfferType;
    }

    public Collection<UserOutletOfferPurchased> getUserOutletOfferPurchased() {
        return userOutletOfferPurchased;
    }

    public void setUserOutletOfferPurchased(Collection<UserOutletOfferPurchased> userOutletOfferPurchased) {
        this.userOutletOfferPurchased = userOutletOfferPurchased;
    }

    public Collection<AdminPasses> getAdminPassesCollection() {
        return adminPassesCollection;
    }

    public void setAdminPassesCollection(Collection<AdminPasses> adminPassesCollection) {
        this.adminPassesCollection = adminPassesCollection;
    }

    public Collection<UserOutletOfferImages> getUserOutletOfferImagesCollection() {
        return userOutletOfferImagesCollection;
    }

    public void setUserOutletOfferImagesCollection(Collection<UserOutletOfferImages> userOutletOfferImagesCollection) {
        this.userOutletOfferImagesCollection = userOutletOfferImagesCollection;
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
        if (!(object instanceof UserOutletOffer)) {
            return false;
        }
        UserOutletOffer other = (UserOutletOffer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
