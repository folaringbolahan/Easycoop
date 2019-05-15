package com.sift.gl.model;

import java.math.BigDecimal;
import java.util.Date;

public class FileUpload{
	   
	    private Integer    id;
	 
        private String     batchId;
	
        private String     companyId;
	
        private String     branchId;
	
        private String     uploadFilename;
	
        private String     uploadStatus;
	
        private String     paymentStatus;
	
        private String     uploadedBy;
	private String     location;
        private java.util.Date  uploadDate;
	
        private Integer    totalRecords;
	
        private Integer    successCount;
	
        private Integer    failedCount;
	private java.sql.Date  processedDatesql;
        private java.util.Date  processedDate;
        //private double   filesum;
        private BigDecimal   filesum;
        
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
                public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
                public Date getProcessedDatesql() {
			return processedDatesql;
		}
		public void setProcessedDatesql(java.sql.Date processedDatesql) {
			this.processedDatesql = processedDatesql;
		}
                /*
                public double getFilesum() {
			return filesum;
		}
		public void setFilesum(double filesum) {
			this.filesum = filesum;
		}
                */

    /**
     * @return the filesum
     */
    public BigDecimal getFilesum() {
        return filesum;
    }

    /**
     * @param filesum the filesum to set
     */
    public void setFilesum(BigDecimal filesum) {
        this.filesum = filesum;
    }
}
