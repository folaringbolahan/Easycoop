package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * BatchContribution entity. @author MyEclipse Persistence Tools
 */

public class BatchContribution implements java.io.Serializable {

	// Fields

	private Integer batchContribId;
	private Status status;
	private BatchUploadFile batchUploadFile;
	private String memberEmail;
	private Double batchContribValue;
	private String createdBy;
	private Timestamp createdDate;
	private String modifiedBy;
	private Timestamp modifiedDate;
	private String approvedBy;
	private Timestamp approvedDate;
	private String contribProd;
	private Integer companyId;
	private Integer branchId;
	private Integer countryId;
	private String valError;

	// Constructors

	/** default constructor */
	public BatchContribution() {
	}

	/** minimal constructor */
	public BatchContribution(Status status, BatchUploadFile batchUploadFile,
			String memberEmail, Double batchContribValue, String createdBy,
			Timestamp createdDate, String contribProd, Integer companyId,
			Integer branchId, Integer countryId) {
		this.status = status;
		this.batchUploadFile = batchUploadFile;
		this.memberEmail = memberEmail;
		this.batchContribValue = batchContribValue;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.contribProd = contribProd;
		this.companyId = companyId;
		this.branchId = branchId;
		this.countryId = countryId;
	}

	/** full constructor */
	public BatchContribution(Status status, BatchUploadFile batchUploadFile,
			String memberEmail, Double batchContribValue, String createdBy,
			Timestamp createdDate, String modifiedBy, Timestamp modifiedDate,
			String approvedBy, Timestamp approvedDate, String contribProd,
			Integer companyId, Integer branchId, Integer countryId,
			String valError) {
		this.status = status;
		this.batchUploadFile = batchUploadFile;
		this.memberEmail = memberEmail;
		this.batchContribValue = batchContribValue;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.contribProd = contribProd;
		this.companyId = companyId;
		this.branchId = branchId;
		this.countryId = countryId;
		this.valError = valError;
	}

	// Property accessors

	public Integer getBatchContribId() {
		return this.batchContribId;
	}

	public void setBatchContribId(Integer batchContribId) {
		this.batchContribId = batchContribId;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public BatchUploadFile getBatchUploadFile() {
		return this.batchUploadFile;
	}

	public void setBatchUploadFile(BatchUploadFile batchUploadFile) {
		this.batchUploadFile = batchUploadFile;
	}

	public String getMemberEmail() {
		return this.memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public Double getBatchContribValue() {
		return this.batchContribValue;
	}

	public void setBatchContribValue(Double batchContribValue) {
		this.batchContribValue = batchContribValue;
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

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Timestamp getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
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

	public String getValError() {
		return this.valError;
	}

	public void setValError(String valError) {
		this.valError = valError;
	}

}