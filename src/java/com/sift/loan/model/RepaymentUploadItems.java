package com.sift.loan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BULK_REPAYMENT_UPLOAD_ITEMS")
public class RepaymentUploadItems{
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
    
    @Column(name="SCHEDULE_ID")
    private Integer    scheduleId;
    
    @Column(name="BATCH_ID")
    private String     batchId;
    
    @Column(name="COMPANY_ID")
    private String     companyId;
    
    @Column(name="BRANCH_ID")
    private String     branchId;
    
    @Column(name="UPLOADED_BY")
    private String     uploadedBy;
    
    @Column(name="LOAN_CASE_ID")
    private String loanCaseId;
    
    @Column(name="MEMBER_NO")
    private String memberNo;
    
    @Column(name="UPLOADED_DATE")
    private Date   uploadedDate;
    
    @Column(name="AMOUNT")
    private double amount;
    
    @Column(name="PENALTY")
    private double penalty;
    
    @Column(name="ACTIVE")
    private String active;
    
    @Column(name="PROCESSED_STATUS")
    private String processedStatus;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId){
		this.branchId = branchId;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy){
		this.uploadedBy = uploadedBy;
	}
	public String getLoanCaseId() {
		return loanCaseId;
	}
	public void setLoanCaseId(String loanCaseId){
		this.loanCaseId = loanCaseId;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public Date getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getPenalty() {
		return penalty;
	}
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getProcessedStatus() {
		return processedStatus;
	}
	public void setProcessedStatus(String processedStatus) {
		this.processedStatus = processedStatus;
	}
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
}