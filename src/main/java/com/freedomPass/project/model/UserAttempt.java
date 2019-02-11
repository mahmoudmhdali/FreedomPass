package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TBL_USER_ATTEMPTS")
@XmlRootElement
public class UserAttempt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ATTEMPTS")
    private int attempt;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LAST_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @JsonIgnore
    @JoinColumn(name = "USER_PROFILE_ID", referencedColumnName = "ID")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private UserProfile userProfileId;

    public UserAttempt() {
    }

    public UserAttempt(Long id) {
        this.id = id;
    }

    public UserAttempt(Long id, short attempt, Date lastModified) {
        this.id = id;
        this.attempt = attempt;
        this.lastModified = lastModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public UserProfile getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(UserProfile userProfileId) {
        this.userProfileId = userProfileId;
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
        if (!(object instanceof UserAttempt)) {
            return false;
        }
        UserAttempt other = (UserAttempt) object;
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
                + "\"attempt\" : \"" + attempt + "\","
                + "\"lastModified\" : \"" + lastModified + "\","
                + "\"userProfileId\" : " + userProfileIdString + "}";
    }
}
