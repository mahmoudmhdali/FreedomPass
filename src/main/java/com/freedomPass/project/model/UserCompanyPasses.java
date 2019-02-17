package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "TBL_USER_COMPANY_PASSES")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserCompanyPasses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @Column(name = "NUMBER_OF_USERS")
    private Integer numberOfUsers;

    @JoinColumn(name = "USER_COMPANY_INFO_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserCompanyInfo userCompanyInfo;

    @JoinColumn(name = "PASS_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AdminPasses adminPasses;

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

    public Integer getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(Integer numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCompanyInfo getUserCompanyInfo() {
        return userCompanyInfo;
    }

    public void setUserCompanyInfo(UserCompanyInfo userCompanyInfo) {
        this.userCompanyInfo = userCompanyInfo;
    }

    public AdminPasses getAdminPasses() {
        return adminPasses;
    }

    public void setAdminPasses(AdminPasses adminPasses) {
        this.adminPasses = adminPasses;
    }

    public Collection<UserPassPurchased> getUserPassPurchased() {
        return userPassPurchased;
    }

    public void setUserPassPurchased(Collection<UserPassPurchased> userPassPurchased) {
        this.userPassPurchased = userPassPurchased;
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
        if (!(object instanceof UserCompanyPasses)) {
            return false;
        }
        UserCompanyPasses other = (UserCompanyPasses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
