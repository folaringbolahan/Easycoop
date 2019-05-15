package com.sift.admin.bean;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement(name = "Tax")
public class TaxBean{    
	private Integer id;
	
	@NotNull(message = "country cannot be empty.")
	private Integer countryId;

	@NotNull(message = "cooperative id cannot be empty.")
	private Integer companyId;
    
	@NotNull(message = "branch cannot be empty.")
	private Integer branchId;

	@NotNull(message = "Tax Group id cannot be empty.")
	private Integer taxGroupId;
	
	@NotNull(message = "Field is required.")
	@NotEmpty(message = "Field is required.")
    private String  locationDependent;
	
	@NotNull(message = "Tax Code is required.")
    @NotEmpty(message = "Tax Code is required.")
    private String  taxCode;
	
	@NotNull(message = "Tax name is required.")
	@NotEmpty(message = "Tax name is required.")
    private String  taxName;
	
	@NotNull(message = "tax description is required.")
	@NotEmpty(message = "Tax description is required.")
    private String  taxDescription;
	
	@NotNull(message = "Rate is required.")
	@NotEmpty(message = "Rate is required.")
    private double  rate;
	
    private String  active;
    private String  deleted;
    private Date    creationDate;
    private String  createdBy;
    private Date    lastModificationDate;
    private String  lastModifiedBy;
    private String companyName;
    private String branchName;
    
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
	public Integer  getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	public String getTaxDescription() {
		return taxDescription;
	}
	public void setTaxDescription(String taxDescription) {
		this.taxDescription = taxDescription;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
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
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public String getLocationDependent() {
		return locationDependent;
	}
	public void setLocationDependent(String locationDependent) {
		this.locationDependent = locationDependent;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public Integer getTaxGroupId() {
		return taxGroupId;
	}
	public void setTaxGroupId(Integer taxGroupId) {
		this.taxGroupId = taxGroupId;
	}

	
 }