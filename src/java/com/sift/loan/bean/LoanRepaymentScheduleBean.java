package com.sift.loan.bean;

import java.util.Date;

public class LoanRepaymentScheduleBean{
    private Integer id;
    private String  companyId;
    private String  branchId;
    private String  loanCaseId;
    private String  memberNo;
    private Date    expectedRepaymentDate;
    private Date    actualRepaymentDate;
    private double  expectedRepaymentAmount;
    private double  actualRepaymentAmount;
    private String  paymentStatus;
    private double  amountPrincipal;
    private double  cummPrincipal;
    private double  amountInterest;
    private double  penaltyIncurred;
    private Date    creationDate;
    private String  createdBy;
    private String  active;
    private String  deleted;
    private Date    lastModificationDate;
    private String  lastModifiedBy;
    private String  memberName;
    private String  coyMemberNo;
    private String  memberNoStr;
    
	public String getMemberNoStr() {
		return memberNoStr;
	}

	public void setMemberNoStr(String memberNoStr) {
		this.memberNoStr = memberNoStr;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCoyMemberNo() {
		return coyMemberNo;
	}

	public void setCoyMemberNo(String coyMemberNo) {
		this.coyMemberNo = coyMemberNo;
	}

	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}
	
	public String getBranchId(){
		return branchId;
	}
	public void setBranchId(String branchId){
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
	public Date getExpectedRepaymentDate() {
		return expectedRepaymentDate;
	}
	public void setExpectedRepaymentDate(Date expectedRepaymentDate) {
		this.expectedRepaymentDate = expectedRepaymentDate;
	}
	public Date getActualRepaymentDate() {
		return actualRepaymentDate;
	}
	public void setActualRepaymentDate(Date actualRepaymentDate) {
		this.actualRepaymentDate = actualRepaymentDate;
	}
	public double getExpectedRepaymentAmount() {
		return expectedRepaymentAmount;
	}
	public void setExpectedRepaymentAmount(double expectedRepaymentAmount) {
		this.expectedRepaymentAmount = expectedRepaymentAmount;
	}
	public double getActualRepaymentAmount() {
		return actualRepaymentAmount;
	}
	public void setActualRepaymentAmount(double actualRepaymentAmount) {
		this.actualRepaymentAmount = actualRepaymentAmount;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public double getAmountPrincipal() {
		return amountPrincipal;
	}
	public void setAmountPrincipal(double amountPrincipal) {
		this.amountPrincipal = amountPrincipal;
	}
	public double getAmountInterest() {
		return amountInterest;
	}
	public void setAmountInterest(double amountInterest) {
		this.amountInterest = amountInterest;
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
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
	public double getPenaltyIncurred() {
		return penaltyIncurred;
	}
	public void setPenaltyIncurred(double penaltyIncurred) {
		this.penaltyIncurred = penaltyIncurred;
	}
	public double getCummPrincipal() {
		return cummPrincipal;
	}
	public void setCummPrincipal(double cummPrincipal) {
		this.cummPrincipal = cummPrincipal;
	}
}