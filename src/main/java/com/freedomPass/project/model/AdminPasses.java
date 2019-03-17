package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.freedomPass.project.model.validation.ValidName;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "TBL_ADMIN_PASSES")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class AdminPasses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Transient
    private String imageName1;

    @Transient
    private List<Long> offersCollection;

    @Transient
    private List<Long> passesCollection;

    @Basic(optional = false)
    @Column(name = "VALIDITY")
    private Integer validity;

    @Basic(optional = false)
    @Column(name = "NAME")
    @Size(min = 5, max = 20, message = "validation.userProfile.nameRange")
    @NotBlank(message = "validation.userProfile.nameRequired")
    @ValidName
    private String name;

    @Basic(optional = false)
    @Column(name = "DESCRIPTION")
    private String description;

    @Basic(optional = false)
    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @Basic(optional = false)
    @Column(name = "FILE_NAME")
    private String fileName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "adminPasses", cascade = CascadeType.ALL)
    private Collection<UserCompanyPasses> userCompanyPasses;

    @JoinTable(name = "TBL_ADMIN_PASSES_OUTLET_OFFERS", inverseJoinColumns = {
        @JoinColumn(name = "OUTLET_OFFER_ID", referencedColumnName = "ID")}, joinColumns = {
        @JoinColumn(name = "PASS_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<UserOutletOffer> userOutletOfferCollection;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfileId", cascade = CascadeType.ALL)
    private Collection<UserPassPurchased> userPassPurchased;

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

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<UserCompanyPasses> getUserCompanyPasses() {
        return userCompanyPasses;
    }

    public void setUserCompanyPasses(Collection<UserCompanyPasses> userCompanyPasses) {
        this.userCompanyPasses = userCompanyPasses;
    }

    public Collection<UserOutletOffer> getUserOutletOfferCollection() {
        return userOutletOfferCollection;
    }

    public void setUserOutletOfferCollection(Collection<UserOutletOffer> userOutletOfferCollection) {
        this.userOutletOfferCollection = userOutletOfferCollection;
    }

    public String getImageName1() {
        return imageName1;
    }

    public void setImageName1(String imageName1) {
        this.imageName1 = imageName1;
    }

    public List<Long> getOffersCollection() {
        return offersCollection;
    }

    public void setOffersCollection(List<Long> offersCollection) {
        this.offersCollection = offersCollection;
    }

    public List<Long> getPassesCollection() {
        return passesCollection;
    }

    public void setPassesCollection(List<Long> passesCollection) {
        this.passesCollection = passesCollection;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public Collection<UserPassPurchased> getUserPassPurchased() {
        return userPassPurchased;
    }

    public void setUserPassPurchased(Collection<UserPassPurchased> userPassPurchased) {
        this.userPassPurchased = userPassPurchased;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdminPasses)) {
            return false;
        }
        AdminPasses other = (AdminPasses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\"com.freedomPass.project.model.UserProfile\" : {\"id\" : \"" + id + "\","
                + "\"name\" : \"" + name + "\","
                + "\"fileName\" : \"" + fileName + "\","
                + "\"imagePath\" : \"" + imagePath + "\","
                + "\"description\" : \"" + description + "\","
                + "\"validity\" : " + validity + "}";
    }
}
