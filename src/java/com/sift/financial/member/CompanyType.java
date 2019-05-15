package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * CompanyType entity. @author MyEclipse Persistence Tools
 */

public class CompanyType implements java.io.Serializable {

	// Fields

	private Integer companyTypeId;
	private String companyTypeName;
	private Set companies = new HashSet(0);

	// Constructors

	/** default constructor */
	public CompanyType() {
	}

	/** minimal constructor */
	public CompanyType(String companyTypeName) {
		this.companyTypeName = companyTypeName;
	}

	/** full constructor */
	public CompanyType(String companyTypeName, Set companies) {
		this.companyTypeName = companyTypeName;
		this.companies = companies;
	}

	// Property accessors

	public Integer getCompanyTypeId() {
		return this.companyTypeId;
	}

	public void setCompanyTypeId(Integer companyTypeId) {
		this.companyTypeId = companyTypeId;
	}

	public String getCompanyTypeName() {
		return this.companyTypeName;
	}

	public void setCompanyTypeName(String companyTypeName) {
		this.companyTypeName = companyTypeName;
	}

	public Set getCompanies() {
		return this.companies;
	}

	public void setCompanies(Set companies) {
		this.companies = companies;
	}

}