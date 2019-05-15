package com.sift.loan.bean;

import java.util.Date;

public class LoanRepayUploadBean {
     private String loanCaseId;
     private String memberNo;
     private double repayAmount;
     private Date   paymentDate;
     private String paymentBy;
     private String validationMessage;
     private String uploadError; //Y or N
     private int    daysOverDue;
     private double fineToPay;
     private int    scheduleId;
     private String batchId;
     private String    companyId;
     private String branchId;
     
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

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public int getDaysOverDue() {
		return daysOverDue;
	}

	public void setDaysOverDue(int daysOverDue) {
		this.daysOverDue = daysOverDue;
	}

	public double getFineToPay() {
		return fineToPay;
	}

	public void setFineToPay(double fineToPay) {
		this.fineToPay = fineToPay;
	}
	
	public LoanRepayUploadBean(){
	}
	
	public LoanRepayUploadBean(int scheduleIdInt,String loanCaseIdStr,String memberNoStr,double amountDbl,Date uploadDate,String uploader){
		 scheduleId=scheduleIdInt;  
		 loanCaseId=loanCaseIdStr;
	   	 memberNo=memberNoStr;
	   	 repayAmount=amountDbl;
	   	 paymentDate=uploadDate;
	   	 paymentBy=uploader;    	 
	}

	public LoanRepayUploadBean(int scheduleIdInt,String loanCaseIdStr,String memberNoStr,double amountDbl,Date uploadDate,String uploader,String validationMessageStr,
								String uploadErrorStr,int daysOverDueInt,String batchIdStr,String companyIdStr,String branchIdStr,double penalty){
		 scheduleId=scheduleIdInt; 
		 loanCaseId=loanCaseIdStr;
    	 memberNo=memberNoStr;
    	 repayAmount=amountDbl;
    	 paymentDate=uploadDate;
    	 paymentBy=uploader;    	 
    	 validationMessage=validationMessageStr;
    	 uploadError=uploadErrorStr;
    	 daysOverDue=daysOverDueInt;
    	 batchId=batchIdStr;
    	 companyId=companyIdStr;
    	 branchId=branchIdStr;
    	 fineToPay=penalty;
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

	
	 public Date getPaymentDate(){
		return paymentDate;
	 }
	
	 public void setPaymentDate(Date paymentDate){
		this.paymentDate = paymentDate;
	 }
	
	 public String getPaymentBy(){
		return paymentBy;
	 }
	
	 public void setPaymentBy(String paymentBy){
		this.paymentBy = paymentBy;
	 }
	
	 public String getValidationMessage(){
		return validationMessage;
	 }
	
	 public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	 }
	 
	 public double getRepayAmount() {
		return repayAmount;
	 }
	 
	 public void setRepayAmount(double repayAmount) {
		this.repayAmount = repayAmount;
	 }

	public String getUploadError() {
		return uploadError;
	}

	public void setUploadError(String uploadError) {
		this.uploadError = uploadError;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
}
