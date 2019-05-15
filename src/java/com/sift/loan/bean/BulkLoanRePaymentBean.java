package com.sift.loan.bean;

import java.util.Date;

public class BulkLoanRePaymentBean{    
    private Integer id;
    private Integer companyId;
    private Integer branchId;
    private String loanCaseId;
    private Integer loanId;
    private double repayTotAmt;
    private double repayTotPenalty;
    private double repayTotInt;
    private double repayTotPrl;
    private Date   repayDate;
    private String processor;
    private Date   creationDate;
    private String createdBy;
    private Date   lastModificationDate;
    private String lastModifiedBy;
    
	public Integer getId(){
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public double getRepayTotAmt() {
		return repayTotAmt;
	}

	public void setRepayTotAmt(double repayTotAmt) {
		this.repayTotAmt = repayTotAmt;
	}

	public double getRepayTotInt() {
		return repayTotInt;
	}

	public void setRepayTotInt(double repayTotInt) {
		this.repayTotInt = repayTotInt;
	}

	public double getRepayTotPrl() {
		return repayTotPrl;
	}

	public void setRepayTotPrl(double repayTotPrl) {
		this.repayTotPrl = repayTotPrl;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getLoanCaseId() {
		return loanCaseId;
	}

	public void setLoanCaseId(String loanCaseId) {
		this.loanCaseId = loanCaseId;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public double getRepayTotPenalty() {
		return repayTotPenalty;
	}

	public void setRepayTotPenalty(double repayTotPenalty) {
		this.repayTotPenalty = repayTotPenalty;
	}            
 }