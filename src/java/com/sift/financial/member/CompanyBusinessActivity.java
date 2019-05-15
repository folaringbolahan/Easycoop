package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * CompanyBusinessActivity entity. @author MyEclipse Persistence Tools
 */

public class CompanyBusinessActivity implements java.io.Serializable {

	// Fields

	private Integer companyBusActivityId;
	private String companyBusinessActivityDesc;
	private Set companies = new HashSet(0);

	// Constructors

	/** default constructor */
	public CompanyBusinessActivity() {
	}

	/** minimal constructor */
	public CompanyBusinessActivity(String companyBusinessActivityDesc) {
		this.companyBusinessActivityDesc = companyBusinessActivityDesc;
	}

	/** full constructor */
	public CompanyBusinessActivity(String companyBusinessActivityDesc,
			Set companies) {
		this.companyBusinessActivityDesc = companyBusinessActivityDesc;
		this.companies = companies;
	}

	// Property accessors

	public Integer getCompanyBusActivityId() {
		return this.companyBusActivityId;
	}

	public void setCompanyBusActivityId(Integer companyBusActivityId) {
		this.companyBusActivityId = companyBusActivityId;
	}

	public String getCompanyBusinessActivityDesc() {
		return this.companyBusinessActivityDesc;
	}

	public void setCompanyBusinessActivityDesc(
			String companyBusinessActivityDesc) {
		this.companyBusinessActivityDesc = companyBusinessActivityDesc;
	}

	public Set getCompanies() {
		return this.companies;
	}

	public void setCompanies(Set companies) {
		this.companies = companies;
	}

}