package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * MemberContributionArchive entity. @author MyEclipse Persistence Tools
 */

public class MemberContributionArchive implements java.io.Serializable {

	// Fields

	private Integer contribArchiveId;
	private Integer memberId;
	private Double memberContribValue;
	private String createdBy;
	private Timestamp createdDate;
	private String contribProd;
	private Integer companyId;
	private Integer branchId;
	private Integer countryId;

	// Constructors

	/** default constructor */
	public MemberContributionArchive() {
	}

	/** full constructor */
	public MemberContributionArchive(Integer memberId,
			Double memberContribValue, String createdBy, Timestamp createdDate,
			String contribProd, Integer companyId, Integer branchId,
			Integer countryId) {
		this.memberId = memberId;
		this.memberContribValue = memberContribValue;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.contribProd = contribProd;
		this.companyId = companyId;
		this.branchId = branchId;
		this.countryId = countryId;
	}

	// Property accessors

	public Integer getContribArchiveId() {
		return this.contribArchiveId;
	}

	public void setContribArchiveId(Integer contribArchiveId) {
		this.contribArchiveId = contribArchiveId;
	}

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Double getMemberContribValue() {
		return this.memberContribValue;
	}

	public void setMemberContribValue(Double memberContribValue) {
		this.memberContribValue = memberContribValue;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getContribProd() {
		return this.contribProd;
	}

	public void setContribProd(String contribProd) {
		this.contribProd = contribProd;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getBranchId() {
		return this.branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

}