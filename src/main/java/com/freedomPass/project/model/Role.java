package com.freedomPass.project.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.Hibernate;

@Entity
@Table(name = "TBL_ROLES")
@XmlRootElement
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ROLE")
    private String role;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ROLE_LABEL")
    private String roleLabel;

    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_SYSTEM_ROLE")
    private boolean isSystemRole;

    @JoinTable(name = "TBL_GROUPS_ROLES", joinColumns = {
        @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Group> groupCollection;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(String roleLabel) {
        this.roleLabel = roleLabel;
    }

    public boolean getIsSystemRole() {
        return isSystemRole;
    }

    public void setIsSystemRole(boolean isSystemRole) {
        this.isSystemRole = isSystemRole;
    }

    @XmlTransient
    public Collection<Group> getGroupCollection() {
        return groupCollection;
    }

    public void setGroupCollection(Collection<Group> groupCollection) {
        this.groupCollection = groupCollection;
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
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String groupCollectionString = "";
        if (Hibernate.isInitialized(groupCollection)) {
            groupCollectionString = Objects.toString(groupCollection);
        }
        return "\"com.freedomPass.project.model.Group\" : {\"id\" : \"" + id + "\","
                + "\"role\" : \"" + role + "\","
                + "\"roleLabel\" : \"" + roleLabel + "\","
                + "\"isSystemRole\" : \"" + isSystemRole + "\","
                + "\"groupCollection\" : " + groupCollectionString + "}";
    }

}
