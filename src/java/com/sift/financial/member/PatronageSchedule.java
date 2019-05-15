package com.sift.financial.member;

import java.util.Date;

/**
 * PatronageSchedule entity. @author MyEclipse Persistence Tools
 */

public class PatronageSchedule implements java.io.Serializable {

	// Fields

	private Integer patronageScheduleId;
	private Integer refundId;
	private Date effectiveDate;
	private String memberNo;
	private String paid;
	private String companyCode;
	private String branchCode;
	private Double patronageGrossValue;
	private String patronageType;
	private Double refundNetValue;
	private Double refundTaxValue;
	private Date paidDate;
	private String payChannel;
	private String paidBy;
	private Double refundTax;

	// Constructors

	/** default constructor */
	public PatronageSchedule() {
	}

	/** minimal constructor */
	public PatronageSchedule(Integer refundId, Date effectiveDate,
			String memberNo, String paid, String companyCode,
			String branchCode, Double patronageGrossValue,
			String patronageType, Double refundNetValue, Double refundTaxValue,
			Double refundTax) {
		this.refundId = refundId;
		this.effectiveDate = effectiveDate;
		this.memberNo = memberNo;
		this.paid = paid;
		this.companyCode = companyCode;
		this.branchCode = branchCode;
		this.patronageGrossValue = patronageGrossValue;
		this.patronageType = patronageType;
		this.refundNetValue = refundNetValue;
		this.refundTaxValue = refundTaxValue;
		this.refundTax = refundTax;
	}

	/** full constructor */
	public PatronageSchedule(Integer refundId, Date effectiveDate,
			String memberNo, String paid, String companyCode,
			String branchCode, Double patronageGrossValue,
			String patronageType, Double refundNetValue, Double refundTaxValue,
			Date paidDate, String payChannel, String paidBy, Double refundTax) {
		this.refundId = refundId;
		this.effectiveDate = effectiveDate;
		this.memberNo = memberNo;
		this.paid = paid;
		this.companyCode = companyCode;
		this.branchCode = branchCode;
		this.patronageGrossValue = patronageGrossValue;
		this.patronageType = patronageType;
		this.refundNetValue = refundNetValue;
		this.refundTaxValue = refundTaxValue;
		this.paidDate = paidDate;
		this.payChannel = payChannel;
		this.paidBy = paidBy;
		this.refundTax = refundTax;
	}

	// Property accessors

	public Integer getPatronageScheduleId() {
		return this.patronageScheduleId;
	}

	public void setPatronageScheduleId(Integer patronageScheduleId) {
		this.patronageScheduleId = patronageScheduleId;
	}

	public Integer getRefundId() {
		return this.refundId;
	}

	public void setRefundId(Integer refundId) {
		this.refundId = refundId;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getMemberNo() {
		return this.memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getPaid() {
		return this.paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBranchCode() {
		return this.branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Double getPatronageGrossValue() {
		return this.patronageGrossValue;
	}

	public void setPatronageGrossValue(Double patronageGrossValue) {
		this.patronageGrossValue = patronageGrossValue;
	}

	public String getPatronageType() {
		return this.patronageType;
	}

	public void setPatronageType(String patronageType) {
		this.patronageType = patronageType;
	}

	public Double getRefundNetValue() {
		return this.refundNetValue;
	}

	public void setRefundNetValue(Double refundNetValue) {
		this.refundNetValue = refundNetValue;
	}

	public Double getRefundTaxValue() {
		return this.refundTaxValue;
	}

	public void setRefundTaxValue(Double refundTaxValue) {
		this.refundTaxValue = refundTaxValue;
	}

	public Date getPaidDate() {
		return this.paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public String getPayChannel() {
		return this.payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPaidBy() {
		return this.paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public Double getRefundTax() {
		return this.refundTax;
	}

	public void setRefundTax(Double refundTax) {
		this.refundTax = refundTax;
	}

}