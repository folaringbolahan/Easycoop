package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Countries entity. @author MyEclipse Persistence Tools
 */

public class Countries implements java.io.Serializable {

	// Fields
	private Long id;
	private String countryCode;
	private String countryName;
	private String active;
	private String createdBy;
	private Timestamp creationDate;
	private String deleted;
	private Timestamp lastModificationDate;
	private String lastModifiedBy;
	private Set companies = new HashSet(0);
	private Set religions = new HashSet(0);

	// Constructors

	/** default constructor */
	public Countries() {
	}

	/** minimal constructor */
	public Countries(String countryCode, String countryName) {
		this.countryCode = countryCode;
		this.countryName = countryName;
	}

	/** full constructor */
	public Countries(String countryCode, String countryName, String active,
			String createdBy, Timestamp creationDate, String deleted,
			Timestamp lastModificationDate, String lastModifiedBy,
			Set companies, Set religions) {
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.active = active;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.deleted = deleted;
		this.lastModificationDate = lastModificationDate;
		this.lastModifiedBy = lastModifiedBy;
		this.companies = companies;
		this.religions = religions;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Timestamp getLastModificationDate() {
		return this.lastModificationDate;
	}

	public void setLastModificationDate(Timestamp lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Set getCompanies() {
		return this.companies;
	}

	public void setCompanies(Set companies) {
		this.companies = companies;
	}

	public Set getReligions() {
		return this.religions;
	}

	public void setReligions(Set religions) {
		this.religions = religions;
	}

}