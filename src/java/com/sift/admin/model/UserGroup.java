package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="usergroups")
public class UserGroup{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
    
    @Column(name="COMPANYID")
    private String companyId;
    
    @Column(name="BRANCHID")
    private String branchId;
    
    @Column(name="AccessId")
    private String accessId;
    
    @Column(name="CODE")
    private String code;
    
    @Column(name="DESCRIPTION")
    private String description;

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
    
	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getDescription(){
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getActive(){
		return active;
	}

	public void setActive(String active){
		this.active = active;
	}

	public String getDeleted(){
		return deleted;
	}

	public void setDeleted(String deleted){
		this.deleted = deleted;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
    
	/*
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
	}     */
	
 }