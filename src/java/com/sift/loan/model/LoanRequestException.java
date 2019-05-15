package com.sift.loan.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="LOAN_REQUEST_EXCEPTIONS")
public class LoanRequestException{
    @Id
    @Column(name="ID")
    @GeneratedValue
	private Integer id;
	
    @Column(name="LOAN_CASE_ID")
	private String  loanCaseId;
    
    @Column(name="EXCEPTION_MESSAGE")
	private String  exceptionMessage;
    
    @Column(name="EXCEPTION_STATUS")
	private Integer exceptionStatus;
    
    @Column(name="CLOSURE_STATUS")
	private String  closureStatus;
    
    @Column(name="CLOSED_BY")
	private String  closedBy;
    
    @Column(name="CLOSURE_COMMENT")
	private String  closureComment;
	
	@Column(name="CLOSURE_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date    closureDate;
	
	@Column(name="CREATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date    creationDate;
	
	@Column(name="CREATED_BY")	
	private String  createdBy;
	
    @Column(name="LAST_MODIFICATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastModificationDate;
    
    @Column(name="LAST_MODIFIED_BY")
    private String lastModifiedBy;
	
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
		return lastModificationDate;
	}
	public void setLastModificationDate(Date lastModificationDate) {
		lastModificationDate = lastModificationDate;
	}
}