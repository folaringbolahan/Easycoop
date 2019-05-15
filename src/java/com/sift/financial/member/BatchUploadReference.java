package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * BatchUploadReference entity. @author MyEclipse Persistence Tools
 */

public class BatchUploadReference implements java.io.Serializable {

	// Fields

	private String batchUploadReferenceId;
	private String createdBy;
	private Timestamp createdDate;
	private String modifiedBy;
	private Timestamp modifiedDate;
	private String approvedBy;
	private Timestamp approvedDate;
	private Status  status;
	private Set batchUploadFiles = new HashSet(0);

	// Constructors

	/** default constructor */
	public BatchUploadReference() {
	}

	/** minimal constructor */
	public BatchUploadReference(String batchUploadReferenceId,
			String createdBy, Timestamp createdDate, Status status) {
		this.batchUploadReferenceId = batchUploadReferenceId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.status = status;
	}

	/** full constructor */
	public BatchUploadReference(String batchUploadReferenceId,
			String createdBy, Timestamp createdDate, String modifiedBy,
			Timestamp modifiedDate, String approvedBy, Timestamp approvedDate,
			Status status, Set batchUploadFiles) {
		this.batchUploadReferenceId = batchUploadReferenceId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
		this.batchUploadFiles = batchUploadFiles;
	}

	// Property accessors

	public String getBatchUploadReferenceId() {
		return this.batchUploadReferenceId;
	}

	public void setBatchUploadReferenceId(String batchUploadReferenceId) {
		this.batchUploadReferenceId = batchUploadReferenceId;
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

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set getBatchUploadFiles() {
		return this.batchUploadFiles;
	}

	public void setBatchUploadFiles(Set batchUploadFiles) {
		this.batchUploadFiles = batchUploadFiles;
	}

}