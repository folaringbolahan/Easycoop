package com.sift.loan.bean;

import java.util.Date;

public class LoanRepaymentBean{    

	private Integer id;
    private String  companyId;
    private String  branchId;
    private String  loanid;
    private float   repayTot;
    private float   repayInt;
    private float   repayPrl;
    private Date    repayDate;
    private String  processor;
    private Date    creationDate;
    private String  createdBy;
    private Date    lastModificationDate;
    private String  lastModifiedBy;

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

	public String getCreatedBy(){
		return createdBy;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public Date getLastModificationDate(){
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate){
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy(){
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy){
		this.lastModifiedBy = lastModifiedBy;
	}

	public float getRepayInt(){
		return repayInt;
	}

	public void setRepayInt(float repayInt){
		this.repayInt = repayInt;
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

	public void setBranchId(String branchId){
		this.branchId = branchId;
	}              
}