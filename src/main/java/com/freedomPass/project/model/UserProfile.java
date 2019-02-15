package com.freedomPass.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.freedomPass.project.model.validation.ValidName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "TBL_USER_PROFILES")
@XmlRootElement
@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserProfile implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GenericGenerator(name = "SEQ_GEN", strategy = "com.freedomPass.project.model.SequenceIdGenerator")
    @GeneratedValue(generator = "SEQ_GEN")
    private Long id;

    @Basic(optional = false)
    @Column(name = "NAME")
    @Size(min = 5, max = 20, message = "validation.userProfile.nameRange")
    @NotBlank(message = "validation.userProfile.nameRequired")
    @ValidName
    private String name;

    @Basic(optional = false)
    @Column(name = "LAST_NAME")
    @Size(min = 5, max = 20, message = "validation.userProfile.nameRange")
    @NotBlank(message = "validation.userProfile.nameRequired")
    @ValidName
    private String lastName;

    @Basic(optional = false)
    @Column(name = "QR_CODE_PATH")
    private String qrCodePath;

    @Basic(optional = false)
    @Column(name = "COUNTRY")
    private Integer country;

    @Basic(optional = false)
    @Column(name = "PARENT_ID")
    private Long parentId;

    @Basic(optional = false)
    @Column(name = "TYPE")
    @NotBlank(message = "typeRequired")
    private Integer type;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotBlank(message = "validation.userProfile.emailRequired")
    @Column(name = "EMAIL")
    @Email(message = "validation.userProfile.emailFormat")
    private String email;

    @Basic(optional = false)
    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;

    @Basic(optional = false)
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Basic(optional = false)
    @Column(name = "JOB_TITLE")
    private String jobTitle;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ENABLED")
    private boolean enabled = true;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ACCOUNT_EXPIRED")
    private boolean accountNonExpired = true;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ACCOUNT_LOCKED")
    private boolean accountNonLocked = true;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREDENTIAL_EXPIRED")
    private boolean credentialNonExpired = true;

    @Column(name = "RESET_PASSWORD_TOKEN")
    private String resetPasswordToken;

    @Column(name = "RESET_PASSWORD_TOKEN_VALIDITY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resetPasswordTokenValidity;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @JoinTable(name = "TBL_USER_PROFILE_GROUPS", inverseJoinColumns = {
        @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")}, joinColumns = {
        @JoinColumn(name = "USER_PROFILE_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Group> groupCollection;

    @OneToMany(mappedBy = "notificationEvents", cascade = CascadeType.ALL)
    private List<UserProfileNotificationEvent> userProfileNotificationEventCollection;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userProfileId", cascade = CascadeType.ALL)
    private UserAttempt userAttempt;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userProfileId", cascade = CascadeType.ALL)
    private UserCompanyInfo userCompanyInfo;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userProfileId", cascade = CascadeType.ALL)
    private UserOutletInfo userOutletInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfileId", cascade = CascadeType.ALL)
    private Collection<AuditTrail> AuditTrailCollection;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfileId", cascade = CascadeType.ALL)
    private Collection<UserPassPurchased> userPassPurchased;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfileId", cascade = CascadeType.ALL)
    private Collection<UserOutletOfferPurchased> userOutletOfferPurchased;

    private transient Collection<GrantedAuthority> authorities;

    @JoinColumn(name = "language_id", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Language language;

    public UserProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Collection<UserPassPurchased> getUserPassPurchased() {
        return userPassPurchased;
    }

    public void setUserPassPurchased(Collection<UserPassPurchased> userPassPurchased) {
        this.userPassPurchased = userPassPurchased;
    }

    public Collection<UserOutletOfferPurchased> getUserOutletOfferPurchased() {
        return userOutletOfferPurchased;
    }

    public void setUserOutletOfferPurchased(Collection<UserOutletOfferPurchased> userOutletOfferPurchased) {
        this.userOutletOfferPurchased = userOutletOfferPurchased;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getQrCodePath() {
        return qrCodePath;
    }

    public void setQrCodePath(String qrCodePath) {
        this.qrCodePath = qrCodePath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public Date getResetPasswordTokenValidity() {
        return resetPasswordTokenValidity;
    }

    public void setResetPasswordTokenValidity(Date resetPasswordTokenValidity) {
        this.resetPasswordTokenValidity = resetPasswordTokenValidity;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    @XmlTransient
    public Collection<Group> getGroupCollection() {
        return groupCollection;
    }

    public void setGroupCollection(Collection<Group> groupCollection) {
        this.groupCollection = groupCollection;
    }

    @XmlTransient
    public List<UserProfileNotificationEvent> getUserProfileNotificationEventCollection() {
        return userProfileNotificationEventCollection;
    }

    public void setUserProfileNotificationEventCollection(List<UserProfileNotificationEvent> userProfileNotificationEventCollection) {
        this.userProfileNotificationEventCollection = userProfileNotificationEventCollection;
    }

    @XmlTransient
    public UserAttempt getUserAttempt() {
        return userAttempt;
    }

    public void setUserAttempt(UserAttempt userAttemptsCollection) {
        this.userAttempt = userAttemptsCollection;
    }

    @XmlTransient
    public UserCompanyInfo getUserCompanyInfo() {
        return userCompanyInfo;
    }

    public void setUserCompanyInfo(UserCompanyInfo userCompanyInfo) {
        this.userCompanyInfo = userCompanyInfo;
    }

    @XmlTransient
    public UserOutletInfo getUserOutletInfo() {
        return userOutletInfo;
    }

    public void setUserOutletInfo(UserOutletInfo userOutletInfo) {
        this.userOutletInfo = userOutletInfo;
    }

    @XmlTransient
    public Collection<AuditTrail> getAuditTrailCollection() {
        return AuditTrailCollection;
    }

    public void setAuditTrailCollection(Collection<AuditTrail> AuditTrailCollection) {
        this.AuditTrailCollection = AuditTrailCollection;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public void setAccountLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public void setCredentialExpired(boolean credentialNonExpired) {
        this.credentialNonExpired = credentialNonExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setUserAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Group group : this.getGroupCollection()) {
            for (Role role : group.getRoleCollection()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
            }
        }
        this.authorities = (Collection<GrantedAuthority>) authorities;
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
        if (!(object instanceof UserProfile)) {
            return false;
        }
        UserProfile other = (UserProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String languageString = "";
        if (Hibernate.isInitialized(language)) {
            languageString = Objects.toString(language);
        }
        String userProfileNotificationEventCollectionString = "";
        if (Hibernate.isInitialized(userProfileNotificationEventCollection)) {
            userProfileNotificationEventCollectionString = Objects.toString(userProfileNotificationEventCollection);
        }
        String groupCollectionString = "";
        if (Hibernate.isInitialized(groupCollection)) {
            groupCollectionString = Objects.toString(groupCollection);
        }
        return "\"com.freedomPass.project.model.UserProfile\" : {\"id\" : \"" + id + "\","
                + "\"name\" : \"" + name + "\","
                + "\"email\" : \"" + email + "\","
                + "\"jobTitle\" : \"" + jobTitle + "\","
                + "\"mobileNumber\" : \"" + mobileNumber + "\","
                + "\"enabled\" : \"" + enabled + "\","
                + "\"language\" : \"" + languageString + "\","
                + "\"userProfileNotificationEventCollection\" : \"" + userProfileNotificationEventCollectionString + "\","
                + "\"groupCollection\" : " + groupCollectionString + "}";
    }
}
