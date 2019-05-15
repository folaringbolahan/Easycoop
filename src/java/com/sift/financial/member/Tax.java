package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * Tax entity. @author MyEclipse Persistence Tools
 */

public class Tax implements java.io.Serializable {

	// Fields

	private Long id;
	private String taxName;
	private String taxDescription;
	private Float rate;
	private String active;
	private String deleted;
	private Timestamp creationDate;
	private String createdBy;
	private String lastModifiedBy;
	private Timestamp lastModificationDate;
	private String taxCode;
	private Integer countryId;
	private String locationDependent;
	private String companyId;
	private String taxGroupId;

	// Constructors

	/** default constructor */
	public Tax() {
	}

	/** minimal constructor */
	public Tax(String createdBy, String lastModifiedBy) {
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
	}

	/** full constructor */
	public Tax(String taxName, String taxDescription, Float rate,
			String active, String deleted, Timestamp creationDate,
			String createdBy, String lastModifiedBy,
			Timestamp lastModificationDate, String taxCode, Integer countryId,
			String locationDependent, String companyId, String taxGroupId) {
		this.taxName = taxName;
		this.taxDescription = taxDescription;
		this.rate = rate;
		this.active = active;
		this.deleted = deleted;
		this.creationDate = creationDate;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModificationDate = lastModificationDate;
		this.taxCode = taxCode;
		this.countryId = countryId;
		this.locationDependent = locationDependent;
		this.companyId = companyId;
		this.taxGroupId = taxGroupId;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaxName() {
		return this.taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public String getTaxDescription() {
		return this.taxDescription;
	}

	public void setTaxDescription(String taxDescription) {
		this.taxDescription = taxDescription;
	}

	public Float getRate() {
		return this.rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getLastModificationDate() {
		return this.lastModificationDate;
	}

	public void setLastModificationDate(Timestamp lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getTaxCode() {
		return this.taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public Integer getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getLocationDependent() {
		return this.locationDependent;
	}

	public void setLocationDependent(String locationDependent) {
		this.locationDependent = locationDependent;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getTaxGroupId() {
		return this.taxGroupId;
	}

	public void setTaxGroupId(String taxGroupId) {
		this.taxGroupId = taxGroupId;
	}

}