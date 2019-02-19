package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;
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
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TBL_USER_COMPANY_INFO_IMAGES")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserCompanyInfoImages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @Column(name = "IMAGE_INDEX")
    private Integer imageIndex;

    @Basic(optional = false)
    @Column(name = "PATH")
    private String path;
    
    @JsonIgnore
    @JoinColumn(name = "USER_COMPANY_INFO_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserCompanyInfo userCompanyInfo;

    public Integer getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(Integer imageIndex) {
        this.imageIndex = imageIndex;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserCompanyInfoImages)) {
            return false;
        }
        UserCompanyInfoImages other = (UserCompanyInfoImages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String userCompanyInfoString = "";
        if (Hibernate.isInitialized(userCompanyInfo)) {
            userCompanyInfoString = Objects.toString(userCompanyInfo);
        }
        return "\"com.freedomPass.project.model.UserProfile\" : {\"id\" : \"" + id + "\","
                + "\"imageIndex\" : \"" + imageIndex + "\","
                + "\"path\" : \"" + path + "\","
                + "\"userProfileId\" : " + userCompanyInfoString + "}";
    }
}
