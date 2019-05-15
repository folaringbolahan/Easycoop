package com.sift.gl.model;
// Generated Nov 11, 2010 5:32:25 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Users generated by hbm2java
 */
public class Users {

     private int id;
     private String userId;
     private String userName;
     private Integer branch;
     private Integer companyid;
     private String department;
     private String password;
     private Short passwordTenure;
     private String jobCode;
     private String accessLevel;
     private String supervisorId;
     private Boolean timeRestriction;
     private Date lastLoginDate;
     private Date lastLoginTime;
     private Integer failedLoginAttempts;
     private Date lastPasswordDate;
     private Date userSetupDate;
     private String mustChangePassword;
     private int accountNonLocked;
     private String lastmodifiedstr;
     private int accountNonExpired;
     private int credentialsNonExpired;

    public Users() {
    }

	
    public Users(String userId) {
        this.userId = userId;
    }
    public Users(String userId, String userName, Integer branch, String department, String password, Short passwordTenure, String jobCode, String accessLevel, String supervisorId, Boolean timeRestriction, Date lastLoginDate, Date lastLoginTime, Integer failedLoginAttempts, Date lastPasswordDate, Date userSetupDate,Integer companyid,String mustChangePassword) {
       this.userId = userId;
       this.userName = userName;
       this.branch = branch;
       this.department = department;
       this.password = password;
       this.passwordTenure = passwordTenure;
       this.jobCode = jobCode;
       this.accessLevel = accessLevel;
       this.supervisorId = supervisorId;
       this.timeRestriction = timeRestriction;
       this.lastLoginDate = lastLoginDate;
       this.lastLoginTime = lastLoginTime;
       this.failedLoginAttempts = failedLoginAttempts;
       this.lastPasswordDate = lastPasswordDate;
       this.userSetupDate = userSetupDate;
       this.companyid = companyid;
       this.mustChangePassword = mustChangePassword;
    }
   
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
     public int getAccountNonLocked() {
        return this.accountNonLocked;
    }
    public void setAccountNonLocked(int accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
     public int getAccountNonExpired() {
        return this.accountNonExpired;
    }
    public void setAccountNonExpired(int accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
     public int getCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
    public void setCredentialsNonExpired(int credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getLastmodifiedstr() {
        return this.lastmodifiedstr;
    }
    
    public void setLastmodifiedstr(String lastmodifiedstr) {
        this.lastmodifiedstr = lastmodifiedstr;
    }
    public Integer getBranch() {
        return this.branch;
    }
    
    public void setBranch(Integer branch) {
        this.branch = branch;
    }
    public String getDepartment() {
        return this.department;
    }
    public Integer getCompanyid() {
        return this.companyid;
    }
    
    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public Short getPasswordTenure() {
        return this.passwordTenure;
    }
    
    public void setPasswordTenure(Short passwordTenure) {
        this.passwordTenure = passwordTenure;
    }
    public String getJobCode() {
        return this.jobCode;
    }
    
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }
    public String getAccessLevel() {
        return this.accessLevel;
    }
    
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
    public String getSupervisorId() {
        return this.supervisorId;
    }
    
    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }
    public Boolean getTimeRestriction() {
        return this.timeRestriction;
    }
    
    public void setTimeRestriction(Boolean timeRestriction) {
        this.timeRestriction = timeRestriction;
    }
    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }
    
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }
    
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public Integer getFailedLoginAttempts() {
        return this.failedLoginAttempts;
    }
    
    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }
    public Date getLastPasswordDate() {
        return this.lastPasswordDate;
    }
    
    public void setLastPasswordDate(Date lastPasswordDate) {
        this.lastPasswordDate = lastPasswordDate;
    }
    public Date getUserSetupDate() {
        return this.userSetupDate;
    }
    
    public void setUserSetupDate(Date userSetupDate) {
        this.userSetupDate = userSetupDate;
    }
    public String getMustChangePassword() {
	return mustChangePassword;
    }
    public void setMustChangePassword(String mustChangePassword) {
	this.mustChangePassword = mustChangePassword;
    }
}


