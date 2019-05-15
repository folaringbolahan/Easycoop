package com.sift.loan.bean;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

public class LoanRequestBean{  
	private Integer id;
	
    @NotEmpty(message = "Company is required.")
    //@Size(min=10,max=20,message = "address type must be between 10 and 20 characters")
    private String companyId;
    
    @NotEmpty(message = "Branch is required.")
    private String branchId;
    
    private String loanCaseId;
    @NotEmpty(message = "Member No is required.")
    private String memberNo;
    
    @NotEmpty(message = "Loan Type is required.")
    private String loanType;
    
    private String loanStatus;
    private String requestStatus;
    private String requestBy;
    private Date requestDate;
    private ArrayList<LoanRequestExceptionBean> ExceptionList;
    
    @NotEmpty(message = "Loan Amount is required.")
    private double requestedAmount;
    
    private double approvedAmount;
    private double appliedRate;
    
    @NotEmpty(message = "Product Rate is required.")
    private double 	productRate;
    private String 	approvedBy;
    private String 	approvalComment;
    private Date  	approvalDate;
    private Date    disburseDate;
    private String  disburseBy;
    private Date    proposedCommencementDate;
    private Date    actualCommencementDate;
    private double 	loanIntTotal;
    private int 	duration;
    private int 	noOfInstallments;
    private double 	balancePrincipal;
    private double 	balanceInterest;
    private double 	balanceTotal;
    private Date  	lastRepaymentDate;
    private String 	repayFrequency;
    private double 	repayAmount;
    private double  totPenaltyDue;
    private double 	totPenaltyPaid;
    private String 	loanAccountNo;
    private Date 	creationDate;
    private String 	createdBy;
    private Date 	lastModificationDate;
    private String 	lastModifiedBy;
    private String  interestType;
    private String  memberName;
    private String  branchName;
    private String  companyName;
    private String  loanStatusDesc;
    private String  memberNoStr;
    private String  coyMemberIdStr;
    private String  loantitle;
    private String changeDisbursement;
    private String disbursementAcct;
    
	public String getLoanStatusDesc() {
		return loanStatusDesc;
	}
	public void setLoanStatusDesc(String loanStatusDesc) {
		this.loanStatusDesc = loanStatusDesc;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getInterestType() {
		return interestType;
	}
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
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
	public String getLoanAccountNo() {
		return loanAccountNo;
	}
	public void setLoanAccountNo(String loanAccountNo) {
		this.loanAccountNo = loanAccountNo;
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
	public ArrayList<LoanRequestExceptionBean> getExceptionList() {
		return ExceptionList;
	}
	public void setExceptionList(ArrayList<LoanRequestExceptionBean> exceptionList) {
		ExceptionList = exceptionList;
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
	public Date getDisburseDate() {
		return disburseDate;
	}
	public void setDisburseDate(Date disburseDate) {
		this.disburseDate = disburseDate;
	}
	public String getDisburseBy() {
		return disburseBy;
	}
	public void setDisburseBy(String disburseBy) {
		this.disburseBy = disburseBy;
	}
	public String getMemberNoStr() {
		return memberNoStr;
	}
	public void setMemberNoStr(String memberNoStr) {
		this.memberNoStr = memberNoStr;
	}
	public String getCoyMemberIdStr() {
		return coyMemberIdStr;
	}
	public void setCoyMemberIdStr(String coyMemberIdStr) {
		this.coyMemberIdStr = coyMemberIdStr;
	}
        public String getLoantitle() {
		return loantitle;
	}

	public void setLoantitle(String loantitle) {
		this.loantitle = loantitle;
	}
        
    /**
     * @return the changeDisbursement
     */
    public String getChangeDisbursement() {
        return changeDisbursement;
    }

    /**
     * @param changeDisbursement the changeDisbursement to set
     */
    public void setChangeDisbursement(String changeDisbursement) {
        this.changeDisbursement = changeDisbursement;
    }

    /**
     * @return the disbursementAcct
     */
    public String getDisbursementAcct() {
        return disbursementAcct;
    }

    /**
     * @param disbursementAcct the disbursementAcct to set
     */
    public void setDisbursementAcct(String disbursementAcct) {
        this.disbursementAcct = disbursementAcct;
    }
}