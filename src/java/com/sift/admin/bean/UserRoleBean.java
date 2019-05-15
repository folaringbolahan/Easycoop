package com.sift.admin.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class UserRoleBean{    
    private Integer id;
    
    /**@NotEmpty(message = "Group Id is required.") 
    private String groupId;***/
 
    @NotEmpty(message = "Role Code cannot be empty.")  
    private String roleCode;
    
    @NotEmpty(message = "Role Name cannot be empty.")  
    private String roleName;
    
    private String active;
    private String deleted;
    /*private Date creationDate;
    private String createdBy;
    private Date lastModificationDate;
    private String lastModifiedBy;*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
    
	/***************************************
	public String getGroupId() {
		return groupId;
	}
	
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	***************************************/

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
  
	/*
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate){
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
    */
    
 }