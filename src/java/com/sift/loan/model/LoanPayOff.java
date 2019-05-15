package com.sift.loan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name="LOAN_PAYOFF")
public class LoanPayOff{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;

    @Column(name="COMPANY_ID")
    private String companyId;
    
    @Column(name="BRANCH_ID")
    private String branchId;

    @Column(name="LOAN_ID")
    private String loanId;
    
    @Column(name="LOAN_CASE_ID")
    private String loanCaseId;
    
    @Column(name="REPAY_TOT_AMT")
    private double repayTotAmt;

    @Column(name="REPAY_TOT_INT")
    private double repayTotInt;
    
    @Column(name="REPAY_TOT_PRL")
    private double repayTotPrl;
    
    @Column(name="REPAY_PENALTY")
    private double repayPenalty;

    @Column(name="REPAY_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date repayDate;
    
    @Column(name="PROCESSOR")
    private String processor;
 
    @Column(name="CREATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date creationDate;
    
    @Column(name="CREATED_BY")
    private String createdBy;
    
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