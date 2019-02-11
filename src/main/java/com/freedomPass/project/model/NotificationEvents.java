package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_notification_events")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class NotificationEvents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "event_id")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Column(name = "event_name")
    private String name;

    @Column(name = "web_notification_flag")
    private Boolean hasWeb;

    @Column(name = "sms_notification_flag")
    private Boolean hasSMS;

    @Column(name = "email_notification_flag")
    private Boolean hasEmail;

    @OneToMany(mappedBy = "notificationEvent")
    private Collection<NotificationTexts> texts;

    @OneToMany(mappedBy = "notificationEvents", cascade = CascadeType.ALL)
    private Collection<UserProfileNotificationEvent> userProfileNotificationEventCollection;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasWeb() {
        return hasWeb;
    }

    public void setHasWeb(Boolean hasWeb) {
        this.hasWeb = hasWeb;
    }

    public Boolean getHasSMS() {
        return hasSMS;
    }

    public void setHasSMS(Boolean hasSMS) {
        this.hasSMS = hasSMS;
    }

    public Boolean getHasEmail() {
        return hasEmail;
    }

    public void setHasEmail(Boolean hasEmail) {
        this.hasEmail = hasEmail;
    }

    @XmlTransient
    public Collection<UserProfileNotificationEvent> getUserProfileNotificationEventCollection() {
        return userProfileNotificationEventCollection;
    }

    public void setUserProfileNotificationEventCollection(Collection<UserProfileNotificationEvent> userProfileNotificationEventCollection) {
        this.userProfileNotificationEventCollection = userProfileNotificationEventCollection;
    }

    @XmlTransient
    public Collection<NotificationTexts> getTexts() {
        return texts;
    }

    public void setTexts(Collection<NotificationTexts> texts) {
        this.texts = texts;
    }

    @Override
    public String toString() {
        String textsString = "";
        String userProfileNotificationEventCollectionString = "";
        if (Hibernate.isInitialized(texts)) {
            textsString = Objects.toString(texts);
        }
        if (Hibernate.isInitialized(userProfileNotificationEventCollection)) {
            userProfileNotificationEventCollectionString = Objects.toString(userProfileNotificationEventCollection);
        }
        return "\"com.freedomPass.project.model.NotificationEvents\" : {\"id\" : \"" + id + "\","
                + "\"name\" : \"" + name + "\","
                + "\"hasWeb\" : \"" + hasWeb + "\","
                + "\"hasSMS\" : \"" + hasSMS + "\","
                + "\"hasEmail\" : \"" + hasEmail + "\","
                + "\"texts\" : \"" + textsString + "\","
                + "\"userProfileNotificationEventCollection\" : " + userProfileNotificationEventCollectionString + "}";
    }
}
