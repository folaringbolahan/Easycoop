package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.Date;

/**
 * PatronageRefund entity. @author MyEclipse Persistence Tools
 */

public class PatronageRefund implements java.io.Serializable {

	// Fields

	private Integer refundId;
	private PatronageRefundType patronageRefundType;
	private Status status;
	private Integer companyId;
	private Integer branchId;
	private String refundYear;
	private String refundPeriod;
	private String formula;
	private String refundValue;
	private Date refundDeclarationDate;
	private Date refundEffDate;
	private Date refundPayDate;
	private Timestamp createdDate;
	private String createdBy;
	private Timestamp approvedDate;
	private String approvedBy;
	private String refundPayAccount;
	private String refundNumber;

	// Constructors

	/** default constructor */
	public PatronageRefund() {
	}

	/** minimal constructor */
	public PatronageRefund(PatronageRefundType patronageRefundType,
			Status status, Integer companyId, Integer branchId,
			String refundYear, String refundPeriod, String formula,
			String refundValue, Date refundDeclarationDate, Date refundEffDate,
			Date refundPayDate, Timestamp createdDate, String createdBy) {
		this.patronageRefundType = patronageRefundType;
		this.status = status;
		this.companyId = companyId;
		this.branchId = branchId;
		this.refundYear = refundYear;
		this.refundPeriod = refundPeriod;
		this.formula = formula;
		this.refundValue = refundValue;
		this.refundDeclarationDate = refundDeclarationDate;
		this.refundEffDate = refundEffDate;
		this.refundPayDate = refundPayDate;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
	}

	/** full constructor */
	public PatronageRefund(PatronageRefundType patronageRefundType,
			Status status, Integer companyId, Integer branchId,
			String refundYear, String refundPeriod, String formula,
			String refundValue, Date refundDeclarationDate, Date refundEffDate,
			Date refundPayDate, Timestamp createdDate, String createdBy,
			Timestamp approvedDate, String approvedBy, String refundPayAccount,
			String refundNumber) {
		this.patronageRefundType = patronageRefundType;
		this.status = status;
		this.companyId = companyId;
		this.branchId = branchId;
		this.refundYear = refundYear;
		this.refundPeriod = refundPeriod;
		this.formula = formula;
		this.refundValue = refundValue;
		this.refundDeclarationDate = refundDeclarationDate;
		this.refundEffDate = refundEffDate;
		this.refundPayDate = refundPayDate;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.approvedDate = approvedDate;
		this.approvedBy = approvedBy;
		this.refundPayAccount = refundPayAccount;
		this.refundNumber = refundNumber;
	}

	// Property accessors

	public Integer getRefundId() {
		return this.refundId;
	}

	public void setRefundId(Integer refundId) {
		this.refundId = refundId;
	}

	public PatronageRefundType getPatronageRefundType() {
		return this.patronageRefundType;
	}

	public void setPatronageRefundType(PatronageRefundType patronageRefundType) {
		this.patronageRefundType = patronageRefundType;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public String getRefundYear() {
		return this.refundYear;
	}

	public void setRefundYear(String refundYear) {
		this.refundYear = refundYear;
	}

	public String getRefundPeriod() {
		return this.refundPeriod;
	}

	public void setRefundPeriod(String refundPeriod) {
		this.refundPeriod = refundPeriod;
	}

	public String getFormula() {
		return this.formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getRefundValue() {
		return this.refundValue;
	}

	public void setRefundValue(String refundValue) {
		this.refundValue = refundValue;
	}

	public Date getRefundDeclarationDate() {
		return this.refundDeclarationDate;
	}

	public void setRefundDeclarationDate(Date refundDeclarationDate) {
		this.refundDeclarationDate = refundDeclarationDate;
	}

	public Date getRefundEffDate() {
		return this.refundEffDate;
	}

	public void setRefundEffDate(Date refundEffDate) {
		this.refundEffDate = refundEffDate;
	}

	public Date getRefundPayDate() {
		return this.refundPayDate;
	}

	public void setRefundPayDate(Date refundPayDate) {
		this.refundPayDate = refundPayDate;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getRefundPayAccount() {
		return this.refundPayAccount;
	}

	public void setRefundPayAccount(String refundPayAccount) {
		this.refundPayAccount = refundPayAccount;
	}

	public String getRefundNumber() {
		return this.refundNumber;
	}

	public void setRefundNumber(String refundNumber) {
		this.refundNumber = refundNumber;
	}

}