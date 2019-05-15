package com.sift.loan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name="LOAN_REPAYMENT")
public class LoanRepayment{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;

    @Column(name="COMPANY_ID")
    private String companyId;
    
    @Column(name="BRANCH_ID")
    private String branchId;
    
    @Column(name="LOAN_ID")
    private String loanid;
    
    @Column(name="REPAY_TOT")
    private float repayTot;

    @Column(name="REPAY_INT")
    private float repayInt;
    
    @Column(name="REPAY_PRL")
    private float repayPrl;

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

	public String getLoanid() {
		return loanid;
	}

	public void setLoanid(String loanid) {
		this.loanid = loanid;
	}

	public float getRepayTot() {
		return repayTot;
	}

	public void setRepayTot(float repayTot) {
		this.repayTot = repayTot;
	}

	public float getRepayPrl() {
		return repayPrl;
	}

	public void setRepayPrl(float repayPrl) {
		this.repayPrl = repayPrl;
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

	public float getRepayInt() {
		return repayInt;
	}

	public void setRepayInt(float repayInt) {
		this.repayInt = repayInt;
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
 }