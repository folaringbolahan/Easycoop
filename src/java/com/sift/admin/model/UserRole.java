package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="USER_ROLE")
public class UserRole{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
    
    /*@Column(name="USER_GROUP_ID")
    private String groupId;*/

    @Column(name="ROLE_CODE")
    private String roleCode;
    
    @Column(name="ROLE_NAME")
    private String roleName;

    @Column(name="ACTIVE")
    private String active;
    
    @Column(name="DELETED")
    private String deleted;
    
    /*
    @Column(name="CREATION_DATE")
    private Date creationDate;
    
    @Column(name="CREATED_BY")
    private String createdBy;
    
    @Column(name="LAST_MODIFICATION_DATE")
    private Date lastModificationDate;
    
    @Column(name="LAST_MODIFIED_BY")
    private String lastModifiedBy;
    */
    
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
	
	/******************************************
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
    *****************************************/
	
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