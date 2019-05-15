package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * MemberHoldings entity. @author MyEclipse Persistence Tools
 */

public class MemberHoldings implements java.io.Serializable {

	// Fields

	private Integer memberHoldingId;
	private Integer memberId;
	private Integer stockId;
	private Double holdings;
	private String createdBy;
	private Timestamp createdDate;
	private Integer companyId;

	// Constructors

	/** default constructor */
	public MemberHoldings() {
	}

	/** full constructor */
	public MemberHoldings(Integer memberId, Integer stockId, Double holdings,
			String createdBy, Timestamp createdDate, Integer companyId) {
		this.memberId = memberId;
		this.stockId = stockId;
		this.holdings = holdings;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.companyId = companyId;
	}

	// Property accessors

	public Integer getMemberHoldingId() {
		return this.memberHoldingId;
	}

	public void setMemberHoldingId(Integer memberHoldingId) {
		this.memberHoldingId = memberHoldingId;
	}

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getStockId() {
		return this.stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Double getHoldings() {
		return this.holdings;
	}

	public void setHoldings(Double holdings) {
		this.holdings = holdings;
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

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}