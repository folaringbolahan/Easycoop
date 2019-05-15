package com.sift.loan.bean;

import java.util.Date;

public class LoanPayOffBean{    
    private Integer id;
    private String companyId;
    private String branchId;
    private String loanCaseId;
    private String loanId;
    private double repayTotAmt;
    private double repayTotInt;
    private double repayTotPrl;
    private double repayPenalty;
    private Date repayDate;
    private String processor;
    private Date creationDate;
    private String createdBy;
    private Date lastModificationDate;
    private String lastModifiedBy;

	public Integer getId() {
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getLoanCaseId() {
		return loanCaseId;
	}

	public void setLoanCaseId(String loanCaseId) {
		this.loanCaseId = loanCaseId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public double getRepayPenalty() {
		return repayPenalty;
	}

	public void setRepayPenalty(double repayPenalty) {
		this.repayPenalty = repayPenalty;
	}              
 }