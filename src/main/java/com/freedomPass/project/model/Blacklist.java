package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_blacklist")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Blacklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "black_id")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @Column(name = "msisdn")
    @Size(min = 8, max = 20, message = "validation.userProfile.mobileRange")
    private String msisdn;

    @Column(name = "dateblacklisted")
    @Temporal(TemporalType.DATE)
    private Date dateblacklisted;
    @Column(name = "bwflag")
    private boolean bwflag;

    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserProfile userProfileId;

    public Blacklist() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getmsisdn() {
        return msisdn;
    }

    public void setmsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Date getdateblacklisted() {
        return dateblacklisted;
    }

    public void setdateblacklisted(Date dateblacklisted) {
        this.dateblacklisted = dateblacklisted;
    }

    public boolean getbwflag() {
        return bwflag;
    }

    public void setbwflag(boolean bwflag) {
        this.bwflag = bwflag;
    }

    public UserProfile getuserProfileId() {
        return userProfileId;
    }

    public void setuserProfileId(UserProfile userProfileId) {
        this.userProfileId = userProfileId;
    }

    @Override
    public String toString() {
        String userProfileIdString = "";
        if (Hibernate.isInitialized(userProfileId)) {
            userProfileIdString = Objects.toString(userProfileId);
        }
        return "\"com.freedomPass.project.model.ReportStyle\" : {\"id\" : \"" + id + "\","
                + "\"msisdn\" : \"" + msisdn + "\","
                + "\"bwflag\" : \"" + bwflag + "\","
                + "\"userProfileId\" : " + userProfileIdString + "}";
    }

}
