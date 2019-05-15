package com.sift.loan.bean;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class LoanRequestExceptionBean {
	private Integer id;
	
	@NotEmpty(message = "Loan Case Id is required.")
    @Size(min=25,max=25,message = "Loan Case Id must be 25 characters")
	private String  loanCaseId;
	
	@NotEmpty(message = "Exception Message is required.")
	private String  exceptionMessage;
	
	@NotEmpty(message = "Exception Status is required.")
	private Integer exceptionStatus;
	
	@NotEmpty(message = "Closure Status is required.")
	private String  closureStatus;
	
	private String  closedBy;
	private String  closureComment;
	private Date    closureDate;
	private String  createdBy;
	private String  lastModifiedBy;
	private Date    creationDate;
	private Date    LastModificationDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoanCaseId() {
		return loanCaseId;
	}
	public void setLoanCaseId(String loanCaseId) {
		this.loanCaseId = loanCaseId;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public Integer getExceptionStatus() {
		return exceptionStatus;
	}
	public void setExceptionStatus(Integer exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}
	public String getClosureStatus() {
		return closureStatus;
	}
	public void setClosureStatus(String closureStatus) {
		this.closureStatus = closureStatus;
	}
	public String getClosedBy() {
		return closedBy;
	}
	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}
	public String getClosureComment() {
		return closureComment;
	}
	public void setClosureComment(String closureComment) {
		this.closureComment = closureComment;
	}
	public Date getClosureDate() {
		return closureDate;
	}
	public void setClosureDate(Date closureDate) {
		this.closureDate = closureDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModificationDate() {
		return LastModificationDate;
	}
	public void setLastModificationDate(Date lastModificationDate) {
		LastModificationDate = lastModificationDate;
	}
}
