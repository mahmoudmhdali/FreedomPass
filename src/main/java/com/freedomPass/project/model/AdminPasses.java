package com.freedomPass.project.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
    @NotNull
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "adminPasses", cascade = CascadeType.ALL)
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
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
                + "\"description\" : \"" + description + "\","
                + "\"validity\" : " + validity + "}";
    }
}
