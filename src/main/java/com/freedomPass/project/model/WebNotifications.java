package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_web_notifications")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class WebNotifications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_added")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateAdded = new Date();

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserProfile user;

    @Column(name = "seen_flag")
    private Boolean hasSEEN = false;

    @Column(name = "text")
    private String text;

    @Column(name = "href")
    private String href;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public Boolean getHasSEEN() {
        return hasSEEN;
    }

    public void setHasSEEN(Boolean hasSEEN) {
        this.hasSEEN = hasSEEN;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        String userString = "";
        if (Hibernate.isInitialized(user)) {
            userString = Objects.toString(user);
        }
        return "\"com.freedomPass.project.model.ReportStyle\" : {\"id\" : \"" + id + "\","
                + "\"href\" : \"" + href + "\","
                + "\"text\" : \"" + text + "\","
                + "\"hasSEEN\" : \"" + hasSEEN + "\","
                + "\"user\" : " + userString + "}";
    }

}
