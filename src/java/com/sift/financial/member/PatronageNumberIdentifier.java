package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * PatronageNumberIdentifier entity. @author MyEclipse Persistence Tools
 */

public class PatronageNumberIdentifier implements java.io.Serializable {

	// Fields

	private Integer refundNumberIdentifierId;
	private Integer companyId;
	private Integer branchId;
	private String lastRefundNumber;
	private Timestamp lastRefundDate;

	// Constructors

	/** default constructor */
	public PatronageNumberIdentifier() {
	}

	/** full constructor */
	public PatronageNumberIdentifier(Integer companyId, Integer branchId,
			String lastRefundNumber, Timestamp lastRefundDate) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.lastRefundNumber = lastRefundNumber;
		this.lastRefundDate = lastRefundDate;
	}

	// Property accessors

	public Integer getRefundNumberIdentifierId() {
		return this.refundNumberIdentifierId;
	}

	public void setRefundNumberIdentifierId(Integer refundNumberIdentifierId) {
		this.refundNumberIdentifierId = refundNumberIdentifierId;
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

	public String getLastRefundNumber() {
		return this.lastRefundNumber;
	}

	public void setLastRefundNumber(String lastRefundNumber) {
		this.lastRefundNumber = lastRefundNumber;
	}

	public Timestamp getLastRefundDate() {
		return this.lastRefundDate;
	}

	public void setLastRefundDate(Timestamp lastRefundDate) {
		this.lastRefundDate = lastRefundDate;
	}

}