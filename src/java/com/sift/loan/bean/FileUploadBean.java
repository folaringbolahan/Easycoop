package com.sift.loan.bean;

import java.util.Date;

import javax.persistence.Column;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUploadBean{
	    private Integer    				id;
	    private String     				batchId;
        private String     				companyId;
        private String     				branchId;
        private String     				uploadFilename;
        private String     				uploadFileShortName;
        private CommonsMultipartFile 	fileData;
        private String     				uploadStatus;
        private String     				uploadStatusDesc;
        private String     				uploadedBy;
        private Date       				uploadDate;
        private Integer   				totalRecords;
        private Integer    				successCount;
        private Integer    				failedCount;
        private Double    				successSum;
        private Double    				failedSum;
        private Double    				uploadSum;
        private Date       				processedDate;
        private String     				processingStatus;
        private String     				processingStatusDesc;
        private String     				paymentStatus;
        private String     				paymentStatusDesc;
        private String                  uploadType;        
        private Double    				principalSum;
        private Double    				interestSum;
        private Double    				penaltySum;
        private Double     				userUploadSum;
        private Double     				userUploadFine;
        private Integer     			userUploadcount;
        private String     				verifiedBy;
        private String     				approvedBy;
        
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
		public String getBatchId(){
			return batchId;
		}
		public void setBatchId(String batchId){
			this.batchId = batchId;
		}
		public CommonsMultipartFile getFileData() {
			return fileData;
		}
		public void setFileData(CommonsMultipartFile fileData){
			this.fileData = fileData;
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
		public String getUploadStatusDesc() {
			return uploadStatusDesc;
		}
		public void setUploadStatusDesc(String uploadStatusDesc) {
			this.uploadStatusDesc = uploadStatusDesc;
		}
		public String getUploadFileShortName() {
			return uploadFileShortName;
		}
		public void setUploadFileShortName(String uploadFileShortName) {
			this.uploadFileShortName = uploadFileShortName;
		}
		public String getPaymentStatusDesc() {
			return paymentStatusDesc;
		}
		public void setPaymentStatusDesc(String paymentStatusDesc) {
			this.paymentStatusDesc = paymentStatusDesc;
		}
		public String getProcessingStatusDesc() {
			return processingStatusDesc;
		}
		public void setProcessingStatusDesc(String processingStatusDesc) {
			this.processingStatusDesc = processingStatusDesc;
		}     
}