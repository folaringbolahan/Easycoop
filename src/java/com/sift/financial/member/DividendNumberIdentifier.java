package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * DividendNumberIdentifier entity. @author MyEclipse Persistence Tools
 */

public class DividendNumberIdentifier implements java.io.Serializable {

	// Fields

	private Integer dividendNumberIdentifierId;
	private Integer companyId;
	private Integer branchId;
	private String lastDividendNumber;
	private Timestamp lastDivDate;

	// Constructors

	/** default constructor */
	public DividendNumberIdentifier() {
	}

	/** full constructor */
	public DividendNumberIdentifier(Integer companyId, Integer branchId,
			String lastDividendNumber, Timestamp lastDivDate) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.lastDividendNumber = lastDividendNumber;
		this.lastDivDate = lastDivDate;
	}

	// Property accessors

	public Integer getDividendNumberIdentifierId() {
		return this.dividendNumberIdentifierId;
	}

	public void setDividendNumberIdentifierId(Integer dividendNumberIdentifierId) {
		this.dividendNumberIdentifierId = dividendNumberIdentifierId;
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

	public String getLastDividendNumber() {
		return this.lastDividendNumber;
	}

	public void setLastDividendNumber(String lastDividendNumber) {
		this.lastDividendNumber = lastDividendNumber;
	}

	public Timestamp getLastDivDate() {
		return this.lastDivDate;
	}

	public void setLastDivDate(Timestamp lastDivDate) {
		this.lastDivDate = lastDivDate;
	}

}