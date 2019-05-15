package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.Basic;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="USERS")
public class User{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
 
    @Column(name="enabled")
    private Integer enabled;

    @Column(name="companyId")
    private String companyId;
    
    @Column(name="branch")
    private String branchId;
    
    @Column(name="UserId")
    private String userId;
    
    @Column(name="EMAIL")
    private String email;
    
    @Column(name="USERNAME")
    private String username;

    @Column(name="PHONE")
    private String phone;

    @Column(name="AccessLevel")
    private String groupId;
    
    @Column(name="PASSWORD")
    private String password;
    
    @Column(name="PASSWORD_TENURE")
    private String passwordTenure;
    
    /*@Column(name="ROLE")
    private String role;*/

    @Column(name="LastLoginDate")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String lastLogonDate;

    @Column(name="AUTH_MODE")
    private String authMode;
    
    @Column(name="CREATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date creationDate;
    
    @Column(name="CREATED_BY")
    private String createdBy;
    
    @Column(name="DELETED")
    private String deleted;
 
    @Column(name="LAST_MODIFICATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastModificationDate;
    
    @Column(name="LAST_MODIFIED_BY")
    private String lastModifiedBy;
    
    @Column(name="MUST_CHANGE_PASS")
    private String mustChangePass;

    @Column(name="IS_BRANCH_USER")
    private Integer isBranchUser;
    
    @Column(name="LoginAttempts")
    private Integer loginAttempts;
    
    /*@Column(name="accountNonLocked",nullable=false)
    private Byte accountNonLocked;
    */
    @Basic(optional = false)
    @NotNull
    @Column(name = "accountNonLocked")
    private short accountNonLocked;

    @Basic(optional = false)
    @NotNull
    @Column(name = "accountNonExpired")
    private short accountNonExpired;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "credentialsNonExpired")
     private short credentialsNonExpired;
    
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getPhone(){
		return phone;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getGroupId(){
		return groupId;
	}

	public void setGroupId(String groupId){
		this.groupId = groupId;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**************************************
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	**************************************/
	
	public String getLastLogonDate() {
		return lastLogonDate;
	}

	public void setLastLogonDate(String lastLogonDate) {
		this.lastLogonDate = lastLogonDate;
	}

	public String getAuthMode() {
		return authMode;
	}

	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordTenure() {
		return passwordTenure;
	}

	public void setPasswordTenure(String passwordTenure) {
		this.passwordTenure = passwordTenure;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getMustChangePass() {
		return mustChangePass;
	}

	public void setMustChangePass(String mustChangePass) {
		this.mustChangePass = mustChangePass;
	}

	public Integer getIsBranchUser() {
		return isBranchUser;
	}

	public void setIsBranchUser(Integer isBranchUser) {
		this.isBranchUser = isBranchUser;
	}     

    /**
     * @return the loginAttemps
     */
    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    /**
     * @param loginAttemps the loginAttemps to set
     */
    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    /**
     * @return the accountNonLocked
     */
    public short getAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * @param accountNonLocked the accountNonLocked to set
     */
    public void setAccountNonLocked(short accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
     public short getAccountNonExpired() {
        return this.accountNonExpired;
    }
    public void setAccountNonExpired(short accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
     public short getCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
    public void setCredentialsNonExpired(short credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
 }