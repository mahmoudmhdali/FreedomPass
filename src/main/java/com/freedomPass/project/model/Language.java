package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "TBL_LANGUAGES")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @Basic(optional = false)
    @Column(name = "name")
    private String languagename;

    @Basic(optional = false)
    @Column(name = "prefix")
    private String prefix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguagename() {
        return languagename;
    }

    public void setLanguagename(String languagename) {
        this.languagename = languagename;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "\"com.freedomPass.project.model.Language\" : {\"id\" : \"" + id + "\","
                + "\"languagename\" : \"" + languagename + "\","
                + "\"prefix\" : " + prefix + "}";
    }

}
