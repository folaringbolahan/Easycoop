/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.webservice.model;

/**
 *
 * @author Olakunle Awotunbo
 */


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="LOAN_REQUEST")
@XmlRootElement(name="LOAN_REQUEST")
@XmlAccessorType(XmlAccessType.NONE)
public class LoanRequestWS{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
 
    @Column(name="COMPANY_ID")
    private String companyId;
    
    @Column(name="BRANCH_ID")
    private String branchId;
    
    @Column(name="LOAN_CASE_ID")
    private String loanCaseId;
    
    @Column(name="MEMBER_NO")
    private String memberNo;
 
    @Column(name="LOAN_TYPE")
    private String loanType;
 
    @Column(name="LOAN_STATUS")
    private String loanStatus;
 
    @Column(name="REQUEST_STATUS")
    private String requestStatus;
 
    @Column(name="REQUEST_BY")
    private String requestBy;
 
    @Column(name="REQUEST_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date requestDate;

    @Column(name="REQUESTED_AMOUNT")
    private double requestedAmount;
    
    @Column(name="APPROVED_AMOUNT")
    private double approvedAmount;

    @Column(name="APPLIED_RATE")
    private double appliedRate;
    
    @Column(name="PROPOSED_COMMENCEMENT_RATE")
    private Date  proposedCommencementDate;

    @Column(name="ACTUAL_COMMENCEMENT_RATE")
    private Date  actualCommencementDate;
    
    @Column(name="PRODUCT_RATE")
    private double productRate;
    
    @Column(name="APPROVED_BY")
    private String approvedBy;
 
    @Column(name="APPROVAL_COMMENT")
    private String approvalComment;
 
    @Column(name="APPROVAL_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  approvalDate;
        
    @Column(name="LOAN_INT_TOTAL")
    private double loanIntTotal;
    
    @Column(name="DURATION_DAYS")
    private String durationDays;

    @Column(name="NO_OF_INSTALLMENTS")
    private int 	noOfInstallments;

    @Column(name="OUTSTANDING_BAL_PR")
    private double balancePrincipal;
 
    @Column(name="OUTSTANDING_BAL_INT")
    private double balanceInterest;
 
    @Column(name="OUTSTANDING_BAL_TOTAL")
    private double balanceTotal;
 
    @Column(name="LAST_REPAYMENT_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  lastRepaymentDate;

    @Column(name="REPAY_FREQUENCY")
    private String repayFrequency;

    @Column(name="REPAY_AMOUNT")
    private double repayAmount;

    @Column(name="TOTAL_PENALTY_DUE")
    private double  totPenaltyDue;

    @Column(name="TOTAL_PENALTY_PAID")
    private double totPenaltyPaid;

    @Column(name="LOAN_ACCOUNT_NUMBER")
    private String loanAccountNumber;
    
    @Column(name="LAST_MODIFICATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastModificationDate;
    
    @Column(name="LAST_MODIFIED_BY")
    private String lastModifiedBy;
    
    @Column(name="INTEREST_TYPE")
    private String  interestType;
    
    @Column(name="DISBURSE_BY")
    private String  disburseBy;
    
    @Column(name="DURATION")
    private int duration;
    
    @Column(name="EASYCOOP_LOAN_ID")
    private String easyCoopLoanId;



	public String getInterestType() {
		return interestType;
	}

	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}

	public Integer getId(){
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

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public double getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(double requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public double getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(double approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public double getAppliedRate() {
		return appliedRate;
	}

	public void setAppliedRate(double appliedRate) {
		this.appliedRate = appliedRate;
	}
	
	public double getProductRate() {
		return productRate;
	}

	public void setProductRate(double productRate) {
		this.productRate = productRate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovalComment() {
		return approvalComment;
	}

	public void setApprovalComment(String approvalComment) {
		this.approvalComment = approvalComment;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public double getLoanIntTotal() {
		return loanIntTotal;
	}

	public void setLoanIntTotal(double loanIntTotal) {
		this.loanIntTotal = loanIntTotal;
	}

	public String getDurationDays() {
		return durationDays;
	}

	public void setDurationDays(String durationDays) {
		this.durationDays = durationDays;
	}

	public double getBalancePrincipal() {
		return balancePrincipal;
	}

	public void setBalancePrincipal(double balancePrincipal) {
		this.balancePrincipal = balancePrincipal;
	}

	public double getBalanceInterest() {
		return balanceInterest;
	}

	public void setBalanceInterest(double balanceInterest) {
		this.balanceInterest = balanceInterest;
	}

	public double getBalanceTotal() {
		return balanceTotal;
	}

	public void setBalanceTotal(double balanceTotal) {
		this.balanceTotal = balanceTotal;
	}

	public Date getLastRepaymentDate() {
		return lastRepaymentDate;
	}

	public void setLastRepaymentDate(Date lastRepaymentDate) {
		this.lastRepaymentDate = lastRepaymentDate;
	}

	public String getRepayFrequency() {
		return repayFrequency;
	}

	public void setRepayFrequency(String repayFrequency) {
		this.repayFrequency = repayFrequency;
	}

	public double getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public double getTotPenaltyDue() {
		return totPenaltyDue;
	}

	public void setTotPenaltyDue(double totPenaltyDue) {
		this.totPenaltyDue = totPenaltyDue;
	}

	public double getTotPenaltyPaid() {
		return totPenaltyPaid;
	}

	public void setTotPenaltyPaid(double totPenaltyPaid) {
		this.totPenaltyPaid = totPenaltyPaid;
	}

	public String getLoanAccountNumber() {
		return loanAccountNumber;
	}

	public void setLoanAccountNumber(String loanAccountNumber) {
		this.loanAccountNumber = loanAccountNumber;
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

	public int getNoOfInstallments() {
		return noOfInstallments;
	}

	public void setNoOfInstallments(int noOfInstallments) {
		this.noOfInstallments = noOfInstallments;
	}

	public Date getProposedCommencementDate() {
		return proposedCommencementDate;
	}

	public void setProposedCommencementDate(Date proposedCommencementDate) {
		this.proposedCommencementDate = proposedCommencementDate;
	}

	public Date getActualCommencementDate() {
		return actualCommencementDate;
	}

	public void setActualCommencementDate(Date actualCommencementDate) {
		this.actualCommencementDate = actualCommencementDate;
	}    

    /**
     * @return the disburseBy
     */
    public String getDisburseBy() {
        return disburseBy;
    }

    /**
     * @param disburseBy the disburseBy to set
     */
    public void setDisburseBy(String disburseBy) {
        this.disburseBy = disburseBy;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the easyCoopLoanId
     */
    public String getEasyCoopLoanId() {
        return easyCoopLoanId;
    }

    /**
     * @param easyCoopLoanId the easyCoopLoanId to set
     */
    public void setEasyCoopLoanId(String easyCoopLoanId) {
        this.easyCoopLoanId = easyCoopLoanId;
    }
 }