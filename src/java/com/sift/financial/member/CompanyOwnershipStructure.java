package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * CompanyOwnershipStructure entity. @author MyEclipse Persistence Tools
 */

public class CompanyOwnershipStructure implements java.io.Serializable {

	// Fields

	private Integer companyOwnerStructureId;
	private String companyOwnerStructDesc;
	private Set companies = new HashSet(0);

	// Constructors

	/** default constructor */
	public CompanyOwnershipStructure() {
	}

	/** minimal constructor */
	public CompanyOwnershipStructure(String companyOwnerStructDesc) {
		this.companyOwnerStructDesc = companyOwnerStructDesc;
	}

	/** full constructor */
	public CompanyOwnershipStructure(String companyOwnerStructDesc,
			Set companies) {
		this.companyOwnerStructDesc = companyOwnerStructDesc;
		this.companies = companies;
	}

	// Property accessors

	public Integer getCompanyOwnerStructureId() {
		return this.companyOwnerStructureId;
	}

	public void setCompanyOwnerStructureId(Integer companyOwnerStructureId) {
		this.companyOwnerStructureId = companyOwnerStructureId;
	}

	public String getCompanyOwnerStructDesc() {
		return this.companyOwnerStructDesc;
	}

	public void setCompanyOwnerStructDesc(String companyOwnerStructDesc) {
		this.companyOwnerStructDesc = companyOwnerStructDesc;
	}

	public Set getCompanies() {
		return this.companies;
	}

	public void setCompanies(Set companies) {
		this.companies = companies;
	}

}