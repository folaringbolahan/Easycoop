package com.sift.financial.member;

import java.util.Date;

/**
 * CompanyMemberIdentifier entity. @author MyEclipse Persistence Tools
 */

public class CompanyMemberIdentifier implements java.io.Serializable {

	// Fields

	private Integer companyMemberId;
	private Integer companyId;
	private Integer lastMemberCode;
	private Date lastDate;

	// Constructors

	/** default constructor */
	public CompanyMemberIdentifier() {
	}

	/** full constructor */
	public CompanyMemberIdentifier(Integer companyId, Integer lastMemberCode,
			Date lastDate) {
		this.companyId = companyId;
		this.lastMemberCode = lastMemberCode;
		this.lastDate = lastDate;
	}

	// Property accessors

	public Integer getCompanyMemberId() {
		return this.companyMemberId;
	}

	public void setCompanyMemberId(Integer companyMemberId) {
		this.companyMemberId = companyMemberId;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getLastMemberCode() {
		return this.lastMemberCode;
	}

	public void setLastMemberCode(Integer lastMemberCode) {
		this.lastMemberCode = lastMemberCode;
	}

	public Date getLastDate() {
		return this.lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

}