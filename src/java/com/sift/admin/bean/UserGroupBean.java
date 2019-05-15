package com.sift.admin.bean;

import java.util.Date;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotEmpty;

public class UserGroupBean{    

    private Integer id;
    
    private String accessId;
    private String companyId;
    private String branchId;
    
    @NotEmpty(message = "Code is required.")  
    private String code;
    
    @NotEmpty(message = "Description cannot be empty.")  
    private String description;
    private String active;
    private String deleted;
    private String companyName;
    private String branchName;
    /*private Date creationDate;
    private String createdBy;
    private Date lastModificationDate;
    private String lastModifiedBy;*/

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
 }