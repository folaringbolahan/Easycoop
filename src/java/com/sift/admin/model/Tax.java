package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
@Table(name="TAX")
public class Tax{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
    
    @Column(name="COUNTRY_ID")
    private Integer countryId;
    
    @Column(name="COMPANY_ID")
    private Integer companyId;
    
    @Column(name="BRANCH_ID")
    private Integer branchId;
    
    @Column(name="TAX_GROUP_ID")
    private Integer taxGroupId;

    @Column(name="LOCATION_DEPENDENT")
    private String locationDependent;
    
    @Column(name="TAX_CODE")
    private String taxCode;

    @Column(name="TAX_NAME")
    private String taxName;

    @Column(name="TAX_DESCRIPTION")
    private String taxDescription;
    
    @Column(name="RATE")
    private double rate;

    @Column(name="ACTIVE")
    private String active;
    
    @Column(name="DELETED")
    private String deleted;
 
    @Column(name="CREATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date creationDate;
    
    @Column(name="CREATED_BY")
    private String createdBy;
    
    @Column(name="LAST_MODIFICATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastModificationDate;
    
    @Column(name="LAST_MODIFIED_BY")
    private String lastModifiedBy;

	public Integer getId() {
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

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
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