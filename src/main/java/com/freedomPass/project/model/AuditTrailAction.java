package com.freedomPass.project.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TBL_AUDIT_TRAIL_ACTIONS")
@XmlRootElement
public class AuditTrailAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ACTION_DESCRIPTION")
    private String actionDescription;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "actionId", cascade = CascadeType.ALL)
    private Collection<AuditTrail> AuditTrailCollection;

    public AuditTrailAction() {
    }

    public AuditTrailAction(Long id) {
        this.id = id;
    }

    public AuditTrailAction(Long id, String actionDescription) {
        this.id = id;
        this.actionDescription = actionDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    @XmlTransient
    public Collection<AuditTrail> getAuditTrailCollection() {
        return AuditTrailCollection;
    }

    public void setAuditTrailCollection(Collection<AuditTrail> AuditTrailCollection) {
        this.AuditTrailCollection = AuditTrailCollection;
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
        if (!(object instanceof AuditTrailAction)) {
            return false;
        }
        AuditTrailAction other = (AuditTrailAction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.freedomPass.ivroutcall.model.AuditTrailActions[ id=" + id + " ]";
    }

}
