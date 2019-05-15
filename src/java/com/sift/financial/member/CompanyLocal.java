package com.sift.financial.member;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * CompanyLocal entity. @author MyEclipse Persistence Tools
 */

public class CompanyLocal implements java.io.Serializable {

	// Fields

	private Integer companyId;
	private String companyName;
	private Date dateOfIncorp;
	private Integer companyTypeId;
	private Integer companyCatId;
	private Integer companyZoneId;
	private Integer companyBusActId;
	private Integer companyBusAreaId;
	private Integer companyOwnStructId;
	private Set compStockTypes = new HashSet(0);
	private Set branchs = new HashSet(0);

	// Constructors

	/** default constructor */
	public CompanyLocal() {
	}

	/** minimal constructor */
	public CompanyLocal(String companyName, Date dateOfIncorp,
			Integer companyTypeId, Integer companyCatId, Integer companyZoneId,
			Integer companyBusActId, Integer companyBusAreaId) {
		this.companyName = companyName;
		this.dateOfIncorp = dateOfIncorp;
		this.companyTypeId = companyTypeId;
		this.companyCatId = companyCatId;
		this.companyZoneId = companyZoneId;
		this.companyBusActId = companyBusActId;
		this.companyBusAreaId = companyBusAreaId;
	}

	/** full constructor */
	public CompanyLocal(String companyName, Date dateOfIncorp,
			Integer companyTypeId, Integer companyCatId, Integer companyZoneId,
			Integer companyBusActId, Integer companyBusAreaId,
			Integer companyOwnStructId, Set compStockTypes, Set branchs) {
		this.companyName = companyName;
		this.dateOfIncorp = dateOfIncorp;
		this.companyTypeId = companyTypeId;
		this.companyCatId = companyCatId;
		this.companyZoneId = companyZoneId;
		this.companyBusActId = companyBusActId;
		this.companyBusAreaId = companyBusAreaId;
		this.companyOwnStructId = companyOwnStructId;
		this.compStockTypes = compStockTypes;
		this.branchs = branchs;
	}

	// Property accessors

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getDateOfIncorp() {
		return this.dateOfIncorp;
	}

	public void setDateOfIncorp(Date dateOfIncorp) {
		this.dateOfIncorp = dateOfIncorp;
	}

	public Integer getCompanyTypeId() {
		return this.companyTypeId;
	}

	public void setCompanyTypeId(Integer companyTypeId) {
		this.companyTypeId = companyTypeId;
	}

	public Integer getCompanyCatId() {
		return this.companyCatId;
	}

	public void setCompanyCatId(Integer companyCatId) {
		this.companyCatId = companyCatId;
	}

	public Integer getCompanyZoneId() {
		return this.companyZoneId;
	}

	public void setCompanyZoneId(Integer companyZoneId) {
		this.companyZoneId = companyZoneId;
	}

	public Integer getCompanyBusActId() {
		return this.companyBusActId;
	}

	public void setCompanyBusActId(Integer companyBusActId) {
		this.companyBusActId = companyBusActId;
	}

	public Integer getCompanyBusAreaId() {
		return this.companyBusAreaId;
	}

	public void setCompanyBusAreaId(Integer companyBusAreaId) {
		this.companyBusAreaId = companyBusAreaId;
	}

	public Integer getCompanyOwnStructId() {
		return this.companyOwnStructId;
	}

	public void setCompanyOwnStructId(Integer companyOwnStructId) {
		this.companyOwnStructId = companyOwnStructId;
	}

	public Set getCompStockTypes() {
		return this.compStockTypes;
	}

	public void setCompStockTypes(Set compStockTypes) {
		this.compStockTypes = compStockTypes;
	}

	public Set getBranchs() {
		return this.branchs;
	}

	public void setBranchs(Set branchs) {
		this.branchs = branchs;
	}

}