package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * TaxGroups entity. @author MyEclipse Persistence Tools
 */

public class TaxGroups implements java.io.Serializable {

	// Fields

	private Integer id;
	private String active;
	private String code;
	private String companyId;
	private String deleted;
	private String description;
	private Set members = new HashSet(0);

	// Constructors

	/** default constructor */
	public TaxGroups() {
	}

	/** full constructor */
	public TaxGroups(String active, String code, String companyId,
			String deleted, String description, Set members) {
		this.active = active;
		this.code = code;
		this.companyId = companyId;
		this.deleted = deleted;
		this.description = description;
		this.members = members;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getMembers() {
		return this.members;
	}

	public void setMembers(Set members) {
		this.members = members;
	}

}