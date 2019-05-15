package com.sift.admin.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class BranchBean{  	
	private Integer id;
	
	@NotEmpty(message = "Company is required.")
    private String companyId;
	
	@NotEmpty(message = "branch code is required.")
    private String branchCode;
	
	@NotEmpty(message = "branch name is required.")
    private String branchName;
	
	@NotEmpty(message = "country is required.")
    private String countryId;
	
	@NotEmpty(message = "Phone1 is required.")
    private String phone1; 
	
	@NotEmpty(message = "Phone2 is required.")
    private String phone2;
	
	@NotEmpty(message = "Email is required.")
	@Email(message = "Valid Email is required.")
    private String email;
	
    private String active;    
    private String deleted;
	
	@NotEmpty(message = "Post Date is required.")
    private Date postDate;
    
	@NotEmpty(message = "Current Period is required.")
    private Integer currentPeriod;
    
	@NotEmpty(message = "Current Year is required.")
    private Integer currentYear;
	  
	@NotEmpty(message = "Base Currency is required.")
    private String baseCurrency;
	
    private Date setupDate;	
    private Date creationDate;
    private String createdBy;
    private Date lastModificationDate;
    private String lastModifiedBy;
    private String companyName;
    private String connectToEazyCoop;

    private Map<Integer, AddressEntriesBean>  branchAddressFields;
	
    public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getBranchCode() {
		return branchCode;
	}
	
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getCountryId() {
		return countryId;
	}
	
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
	public String getPhone1() {
		return phone1;
	}
	
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	
	public String getPhone2() {
		return phone2;
	}
	
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
	}
	
	public Map<Integer, AddressEntriesBean> getBranchAddressFields() {
		return branchAddressFields;
	}
	
	public void setAddressFields(Map<Integer, AddressEntriesBean> branchAddressFields) {
		this.branchAddressFields = branchAddressFields;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Integer getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public Integer getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(Integer currentYear) {
		this.currentYear = currentYear;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public Date getSetupDate() {
		return setupDate;
	}

	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}

	public String getConnectToEazyCoop() {
		return connectToEazyCoop;
	}

	public void setConnectToEazyCoop(String connectToEazyCoop) {
		this.connectToEazyCoop = connectToEazyCoop;
	}

	public void setBranchAddressFields(
			Map<Integer, AddressEntriesBean> branchAddressFields) {
		this.branchAddressFields = branchAddressFields;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}    
 }