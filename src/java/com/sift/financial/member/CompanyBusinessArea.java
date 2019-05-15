package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * CompanyBusinessArea entity. @author MyEclipse Persistence Tools
 */

public class CompanyBusinessArea implements java.io.Serializable {

	// Fields

	private Integer companyBusAreaId;
	private String companyBusAreaDesc;
	private Set companies = new HashSet(0);

	// Constructors

	/** default constructor */
	public CompanyBusinessArea() {
	}

	/** minimal constructor */
	public CompanyBusinessArea(String companyBusAreaDesc) {
		this.companyBusAreaDesc = companyBusAreaDesc;
	}

	/** full constructor */
	public CompanyBusinessArea(String companyBusAreaDesc, Set companies) {
		this.companyBusAreaDesc = companyBusAreaDesc;
		this.companies = companies;
	}

	// Property accessors

	public Integer getCompanyBusAreaId() {
		return this.companyBusAreaId;
	}

	public void setCompanyBusAreaId(Integer companyBusAreaId) {
		this.companyBusAreaId = companyBusAreaId;
	}

	public String getCompanyBusAreaDesc() {
		return this.companyBusAreaDesc;
	}

	public void setCompanyBusAreaDesc(String companyBusAreaDesc) {
		this.companyBusAreaDesc = companyBusAreaDesc;
	}

	public Set getCompanies() {
		return this.companies;
	}

	public void setCompanies(Set companies) {
		this.companies = companies;
	}

}