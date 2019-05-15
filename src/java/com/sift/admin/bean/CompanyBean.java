package com.sift.admin.bean;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class CompanyBean{    
	private Integer id;
	
	@NotEmpty(message = "Reg No is required.")
    private String  regNo;
	
	@NotEmpty(message = "Company Name is required.")
    private String  name;
	
	private String  code;
	
	@NotEmpty(message = "Short Name is required.")
    private String  shortName;
	
	@NotEmpty(message = "country must be specified.")
    private String  countryId;
	
	@NotEmpty(message = "phone1 is required.")
    private String  phone1;
	
	@NotEmpty(message = "phone2 is required.")
    private String  phone2;
	
	@NotEmpty(message = "Email is required.")
	@Email(message = "Email must be valid.")
    private String  email;
	
	@NotEmpty(message = "Fax is required.")
    private String  fax;
	
	@NotEmpty(message = "Website is required.")
    private String  website;
	
    private Date    creationDate;
    private String  createdBy;
    private String  active;
    private String  deleted;
    private Date    lastModificationDate;
    private String  lastModifiedBy;
    private String  connectToEazyCoop;

    private Map<Integer, AddressEntriesBean>  companyAddressFields;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Integer, AddressEntriesBean> getCompanyAddressFields() {
		return companyAddressFields;
	}

	public void setCompanyAddressFields(
			Map<Integer, AddressEntriesBean> companyAddressFields) {
		this.companyAddressFields = companyAddressFields;
	}

	public String getCountryId(){
		return countryId;
	}

	public void setCountryId(String countryId){
		this.countryId = countryId;
	}

	public String getPhone1(){
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getConnectToEazyCoop() {
		return connectToEazyCoop;
	}

	public void setConnectToEazyCoop(String connectToEazyCoop) {
		this.connectToEazyCoop = connectToEazyCoop;
	}
   
 }