package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_notification_texts")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class NotificationTexts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "text_id")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "event_id", nullable = false)
    @JsonIgnore
    private NotificationEvents notificationEvent;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "text")
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NotificationEvents getNotificationEvent() {
        return notificationEvent;
    }

    public void setNotificationEvent(NotificationEvents notificationEvent) {
        notificationEvent = notificationEvent;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        String languageString = "";
        if (Hibernate.isInitialized(language)) {
            languageString = Objects.toString(language);
        }
        String notificationEventString = "";
        if (Hibernate.isInitialized(notificationEvent)) {
            notificationEventString = Objects.toString(notificationEvent);
        }
        return "\"com.freedomPass.project.model.NotificationTexts\" : {\"id\" : \"" + id + "\","
                + "\"text\" : \"" + text + "\","
                + "\"language\" : \"" + languageString + "\","
                + "\"notificationEvent\" : " + notificationEventString + "}";
    }

}
