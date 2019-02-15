package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Date;
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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TBL_USER_OUTLET_OFFER_USED")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserOutletOfferPurchased implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @Column(name = "USED_DATE")
    private Date usedDate;

    @Basic(optional = false)
    @Column(name = "NEXT_RESET_DATE")
    private Date nextResetDate;

    @Basic(optional = false)
    @Column(name = "COUNTER")
    private Integer counter;

    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserProfile userProfileId;

    @JoinColumn(name = "OUTLET_OFFER_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserOutletOffer userOutletOffer;

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public Date getNextResetDate() {
        return nextResetDate;
    }

    public void setNextResetDate(Date nextResetDate) {
        this.nextResetDate = nextResetDate;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(UserProfile userProfileId) {
        this.userProfileId = userProfileId;
    }

    public UserOutletOffer getUserOutletOffer() {
        return userOutletOffer;
    }

    public void setUserOutletOffer(UserOutletOffer userOutletOffer) {
        this.userOutletOffer = userOutletOffer;
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
        if (!(object instanceof UserOutletOfferPurchased)) {
            return false;
        }
        UserOutletOfferPurchased other = (UserOutletOfferPurchased) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
