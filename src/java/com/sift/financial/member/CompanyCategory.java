package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * CompanyCategory entity. @author MyEclipse Persistence Tools
 */

public class CompanyCategory implements java.io.Serializable {

	// Fields

	private Integer companyCategoryId;
	private String companyCategoryName;
	private Set companies = new HashSet(0);

	// Constructors

	/** default constructor */
	public CompanyCategory() {
	}

	/** minimal constructor */
	public CompanyCategory(String companyCategoryName) {
		this.companyCategoryName = companyCategoryName;
	}

	/** full constructor */
	public CompanyCategory(String companyCategoryName, Set companies) {
		this.companyCategoryName = companyCategoryName;
		this.companies = companies;
	}

	// Property accessors

	public Integer getCompanyCategoryId() {
		return this.companyCategoryId;
	}

	public void setCompanyCategoryId(Integer companyCategoryId) {
		this.companyCategoryId = companyCategoryId;
	}

	public String getCompanyCategoryName() {
		return this.companyCategoryName;
	}

	public void setCompanyCategoryName(String companyCategoryName) {
		this.companyCategoryName = companyCategoryName;
	}

	public Set getCompanies() {
		return this.companies;
	}

	public void setCompanies(Set companies) {
		this.companies = companies;
	}

}