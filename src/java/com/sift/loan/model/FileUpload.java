package com.sift.loan.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BULK_REPAYMENT_UPLOAD")
public class FileUpload{
	    @Id
	    @Column(name="ID")
	    @GeneratedValue
	    private Integer    id;
	    
	    @Column(name="BATCH_ID")
        private String     batchId;
	    
	    @Column(name="COMPANY_ID")
        private String     companyId;
	    
	    @Column(name="BRANCH_ID")
        private String     branchId;
	    
	    @Column(name="UPLOAD_FILE_NAME")
        private String     uploadFilename;
	    
	    @Column(name="UPLOAD_STATUS")
        private String     uploadStatus;
	    
	    @Column(name="PAYMENT_STATUS")
        private String     paymentStatus;
	    
	    @Column(name="UPLOADED_BY")
        private String     uploadedBy;
	    
	    @Column(name="UPLOAD_DATE")
        private Date       uploadDate;
	    
	    @Column(name="TOTAL_RECORDS")
        private Integer    totalRecords;
	    
	    @Column(name="SUCCESS_COUNT")
        private Integer    successCount;
	    
	    @Column(name="FAILED_COUNT")
        private Integer    failedCount;
	    
	    @Column(name="PROCESSED_DATE")
        private Date       processedDate;
	    
	    @Column(name="PROCESSED_STATUS")
        private String     processingStatus;
	    
	    @Column(name="SUCCESS_SUM")
	    private Double     successSum;
	    
	    @Column(name="FAILED_SUM")
        private Double     failedSum;
	    
	    @Column(name="UPLOAD_SUM")
        private Double     uploadSum;
	    
	    @Column(name="USER_UPLOAD_TOTAL_PAYMENT")
        private Double     userUploadSum;
	    
	    @Column(name="USER_UPLOAD_TOTAL_FINE")
        private Double     userUploadFine;
	    
	    @Column(name="USER_UPLOAD_COUNT")
        private Integer     userUploadcount;
	    
	    @Column(name="UPLOAD_TYPE")
        private String     uploadType;
	    
	    @Column(name="PRINCIPAL_SUM")
        private Double     principalSum;
	    
	    @Column(name="INTEREST_SUM")
        private Double     interestSum;
	    
	    @Column(name="PENALTY_SUM")
        private Double     penaltySum;
	    
	    @Column(name="VERIFIED_BY")
        private String     verifiedBy;
	    
	    @Column(name="APPROVED_BY")
        private String     approvedBy;
        
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getUploadFilename() {
			return uploadFilename;
		}
		public void setUploadFilename(String uploadFilename) {
			this.uploadFilename = uploadFilename;
		}
		public String getUploadStatus() {
			return uploadStatus;
		}
		public void setUploadStatus(String uploadStatus) {
			this.uploadStatus = uploadStatus;
		}
		public String getUploadedBy() {
			return uploadedBy;
		}
		public void setUploadedBy(String uploadedBy) {
			this.uploadedBy = uploadedBy;
		}
		public Date getUploadDate() {
			return uploadDate;
		}
		public void setUploadDate(Date uploadDate) {
			this.uploadDate = uploadDate;
		}
		public Integer getTotalRecords() {
			return totalRecords;
		}
		public void setTotalRecords(Integer totalRecords) {
			this.totalRecords = totalRecords;
		}
		public Integer getSuccessCount() {
			return successCount;
		}
		public void setSuccessCount(Integer successCount) {
			this.successCount = successCount;
		}
		public Integer getFailedCount() {
			return failedCount;
		}
		public void setFailedCount(Integer failedCount) {
			this.failedCount = failedCount;
		}
		public Date getProcessedDate() {
			return processedDate;
		}
		public void setProcessedDate(Date processedDate) {
			this.processedDate = processedDate;
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
		public String getBatchId() {
			return batchId;
		}
		public void setBatchId(String batchId) {
			this.batchId = batchId;
		}
		public String getPaymentStatus() {
			return paymentStatus;
		}
		public void setPaymentStatus(String paymentStatus) {
			this.paymentStatus = paymentStatus;
		}
		public Double getSuccessSum() {
			return successSum;
		}
		public void setSuccessSum(Double successSum) {
			this.successSum = successSum;
		}
		public Double getFailedSum() {
			return failedSum;
		}
		public void setFailedSum(Double failedSum) {
			this.failedSum = failedSum;
		}
		public Double getUploadSum() {
			return uploadSum;
		}
		public void setUploadSum(Double uploadSum) {
			this.uploadSum = uploadSum;
		}
		public String getUploadType() {
			return uploadType;
		}
		public void setUploadType(String uploadType) {
			this.uploadType = uploadType;
		}
		public Double getPrincipalSum() {
			return principalSum;
		}
		public void setPrincipalSum(Double principalSum) {
			this.principalSum = principalSum;
		}
		public Double getInterestSum() {
			return interestSum;
		}
		public void setInterestSum(Double interestSum) {
			this.interestSum = interestSum;
		}
		public Double getPenaltySum() {
			return penaltySum;
		}
		public void setPenaltySum(Double penaltySum) {
			this.penaltySum = penaltySum;
		}
		public String getProcessingStatus() {
			return processingStatus;
		}
		public void setProcessingStatus(String processingStatus) {
			this.processingStatus = processingStatus;
		}
		public Double getUserUploadSum() {
			return userUploadSum;
		}
		public void setUserUploadSum(Double userUploadSum) {
			this.userUploadSum = userUploadSum;
		}
		public Double getUserUploadFine() {
			return userUploadFine;
		}
		public void setUserUploadFine(Double userUploadFine) {
			this.userUploadFine = userUploadFine;
		}
		public Integer getUserUploadcount() {
			return userUploadcount;
		}
		public void setUserUploadcount(Integer userUploadcount) {
			this.userUploadcount = userUploadcount;
		}
		
		public String getVerifiedBy() {
			return verifiedBy;
		}
		
		public void setVerifiedBy(String verifiedBy) {
			this.verifiedBy = verifiedBy;
		}
		
		public String getApprovedBy() {
			return approvedBy;
		}
		
		public void setApprovedBy(String approvedBy) {
			this.approvedBy = approvedBy;
		}     
}
