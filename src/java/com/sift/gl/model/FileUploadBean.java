package com.sift.gl.model;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUploadBean{
	    private Integer    				id;
	    private String     				batchId;
        private String     				companyId;
        private String     				branchId;
        private String     				uploadFilename;
        private CommonsMultipartFile 	fileData;
        private String     				uploadStatus;
        private String     				uploadedBy;
        private Date       				uploadDate;
        private Integer   				totalRecords;
        private Integer    				successCount;
        private Integer    				failedCount;
        private Date       				processedDate;
        private String     				paymentStatus;
        private Date       				txnDate;
        private String       				txnDatestr;
        private String     				accountno;
        //private double   				filesum;
        private BigDecimal   				filesum;
        private double   				processedsum;
        private BigDecimal   				filesum2;
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
                public String getAccountno() {
			return accountno;
		}
		public void setAccountno(String accountno) {
			this.accountno = accountno;
		}  
                public Date getTxnDate() {
			return txnDate;
		}
		public void setTxnDate(Date txnDate) {
			this.txnDate = txnDate;
		}
                public String getTxnDatestr() {
			return txnDatestr;
		}
		public void setTxnDatestr(String txnDatestr) {
			this.txnDatestr = txnDatestr;
		}
                /*
                public double getFilesum() {
			return filesum;
		}
		public void setFilesum(double filesum) {
			this.filesum = filesum;
		}
                */
                public double getProcessedsum() {
			return processedsum;
		}
		public void setProcessedsum(double processedsum) {
			this.processedsum = processedsum;
		}        

    /**
     * @return the filesum2
     */
    public BigDecimal getFilesum2() {
        return filesum2;
    }

    /**
     * @param filesum2 the filesum2 to set
     */
    public void setFilesum2(BigDecimal filesum2) {
        this.filesum2 = filesum2;
    }

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