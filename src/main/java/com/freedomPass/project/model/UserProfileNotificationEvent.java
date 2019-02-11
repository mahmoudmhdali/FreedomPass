package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_userprofile_notification_event")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserProfileNotificationEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "NOTIFICATION_EVENT_ID")
    @JsonIgnore
    private NotificationEvents notificationEvents;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private UserProfile userProfile;

    @Column(name = "enabled")
    private Boolean enabled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NotificationEvents getNotificationEvents() {
        return notificationEvents;
    }

    public void setNotificationEvents(NotificationEvents notificationEvents) {
        this.notificationEvents = notificationEvents;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        String notificationEventsString = "";
        if (Hibernate.isInitialized(notificationEvents)) {
            notificationEventsString = Objects.toString(notificationEvents);
        }
        String userProfileString = "";
        if (Hibernate.isInitialized(userProfile)) {
            userProfileString = Objects.toString(userProfile);
        }
        return "\"com.freedomPass.project.model.UserProfileNotificationEvent\" : {\"id\" : \"" + id + "\","
                + "\"enabled\" : \"" + enabled + "\","
                + "\"notificationEvents\" : \"" + notificationEventsString + "\","
                + "\"userProfile\" : " + userProfileString + "}";
    }

}
