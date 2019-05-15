package com.sift.loan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
@Table(name="LOAN_REPAYMENT_SCHEDULE")
public class LoanRepaymentSchedule{    
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

    @Column(name="EXPECTED_REPAYMENT_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date expectedRepaymentDate;
    
    @Column(name="ACTUAL_REPAYMENT_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date actualRepaymentDate;
    
    @Column(name="EXPECTED_REPAYMENT_AMOUNT")
    private double expectedRepaymentAmount;
    
    @Column(name="ACTUAL_REPAYMENT_AMOUNT")
    private double actualRepaymentAmount;
    
    @Column(name="PAYMENT_STATUS")
    private String paymentStatus;
    
    @Column(name="AMOUNT_PRINCIPAL")
    private double amountPrincipal;
    
    @Column(name="CUMM_PRINCIPAL")
    private double cummPrincipal;
    
    @Column(name="AMOUNT_INTEREST")
    private double amountInterest;
    
    @Column(name="CREATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date creationDate;
    
    @Column(name="CREATED_BY")
    private String createdBy;

    @Column(name="ACTIVE")
    private String active;
    
    @Column(name="DELETED")
    private String deleted;

    @Column(name="LAST_MODIFICATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastModificationDate;
    
    @Column(name="LAST_MODIFIED_BY")
    private String lastModifiedBy;
    
    @Column(name="PENALTY_INCURRED")
    private double penaltyIncurred;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getBranchId(){
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

	public String getLastModifiedBy(){
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy){
		this.lastModifiedBy = lastModifiedBy;
	}

	public double getPenaltyIncurred(){
		return penaltyIncurred;
	}

	public void setPenaltyIncurred(double penaltyIncurred){
		this.penaltyIncurred = penaltyIncurred;
	}

	public double getCummPrincipal(){
		return cummPrincipal;
	}

	public void setCummPrincipal(double cummPrincipal){
		this.cummPrincipal = cummPrincipal;
	}   
 }