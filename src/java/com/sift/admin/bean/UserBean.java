package com.sift.admin.bean;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserBean{
    private Integer id;
    
    @NotEmpty(message = "User Company cannot be empty.")
    private String companyId;
    
    @NotEmpty(message = "User Branch cannot be empty.")
    private String branchId; 
    
    private String userId;
    private Integer enabled;
    
    @NotEmpty(message = "Email Field cannot be empty.")
    @Email(message    = "Valid User Email is required.")
    private String email;
        
    @NotEmpty(message = "User Phone is required.")
    private String phone;    
  
    private String password;
    
    @NotEmpty(message = "User Group cannot be empty.")
    private String groupId;
    
    //@NotEmpty(message = "User Role cannot be empty.")
    //private String role;
    
    @NotEmpty(message = "Username cannot be empty.")
    private String username;
    
    private String passwordTenure;    
    private String lastLogonDate;
    private String authMode;
    private Date   creationDate;
    private String createdBy;
    private String deleted;
    private Date   lastModificationDate;
    private String lastModifiedBy;
    private String mustChangePass;
    private String branchName;
    private String companyName;
    private Integer isBranchUser;
    private short accountNonLocked;
    private short accountNonExpired;
     private short credentialsNonExpired;
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMustChangePass() {
		return mustChangePass;
	}

	public void setMustChangePass(String mustChangePass) {
		this.mustChangePass = mustChangePass;
	}

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
    
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/*
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    */
	
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

	public void setDeleted(String deleted){
		this.deleted = deleted;
	}

	public Date getLastModificationDate(){
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate){
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy(){
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy){
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getBranchId(){
		return branchId;
	}

	public void setBranchId(String branchId){
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

	public Integer getIsBranchUser() {
		return isBranchUser;
	}

	public void setIsBranchUser(Integer isBranchUser) {
		this.isBranchUser = isBranchUser;
	}     
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