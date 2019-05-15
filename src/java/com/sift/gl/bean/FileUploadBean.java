/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.bean;

import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author Olakunle Awotunbo
 */
public class FileUploadBean {
 private Integer id;
    private String batchId;
    private String companyId;
    private String branchId;
    private String uploadFilename;
    private CommonsMultipartFile fileData;
    private String uploadStatus;
    private String uploadedBy;
    private Date uploadDate;
    private Integer totalRecords;
    private Integer successCount;
    private Integer failedCount;
    private Date processedDate;
    private String paymentStatus;
    private int productId;
    private Float fileSum;
    private int toCreateAcct;    
    private Double userUploadSum;      
    private String uploadType;
    private String processingStatus;    
    private String approvedBy;
    private String verifierId;
    private String referenceNumber;
    private String memberNoStr;
    private List<FileUploadBean> uploadedListAll;
    private List<FileUploadBean> uploadedListSuccess;
    private List<FileUploadBean> uploadedListFailed;
    private int userUploadcount;
    private String validationMsg;
    private String productName;

    public FileUploadBean() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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

    public CommonsMultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public Float getFileSum() {
        return fileSum;
    }

    public void setFileSum(Float fileSum) {
        this.fileSum = fileSum;
    }

    /**
     * @return the toCreateAcct
     */
    public int getToCreateAcct() {
        return toCreateAcct;
    }

    /**
     * @param toCreateAcct the toCreateAcct to set
     */
    public void setToCreateAcct(int toCreateAcct) {
        this.toCreateAcct = toCreateAcct;
    }

    /**
     * @return the userUploadSum
     */
    public Double getUserUploadSum() {
        return userUploadSum;
    }

    /**
     * @param userUploadSum the userUploadSum to set
     */
    public void setUserUploadSum(Double userUploadSum) {
        this.userUploadSum = userUploadSum;
    }

    /**
     * @return the uploadType
     */
    public String getUploadType() {
        return uploadType;
    }

    /**
     * @param uploadType the uploadType to set
     */
    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    /**
     * @return the processingStatus
     */
    public String getProcessingStatus() {
        return processingStatus;
    }

    /**
     * @param processingStatus the processingStatus to set
     */
    public void setProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }

    /**
     * @return the approvedBy
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     * @param approvedBy the approvedBy to set
     */
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     * @return the verifierId
     */
    public String getVerifierId() {
        return verifierId;
    }

    /**
     * @param verifierId the verifierId to set
     */
    public void setVerifierId(String verifierId) {
        this.verifierId = verifierId;
    }

    /**
     * @return the referenceNumber
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * @param referenceNumber the referenceNumber to set
     */
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    
    public FileUploadBean(String memberNoStr, String validationMsg){
		
	   	 this.memberNoStr = memberNoStr;	   	 
	   	 this.validationMsg = validationMsg;	 
	}
     public FileUploadBean(String memberNoStr, String validationMsg,String memberName, int companyId, int branchId, String batchId, int productId, Date uploadDate, String uploadedBy){
		
	   	 this.memberNoStr = memberNoStr;	   	 
	   	 this.validationMsg = validationMsg;	
                 this.companyId = String.valueOf(companyId);
                 this.branchId =  String.valueOf(branchId);
                 this.referenceNumber = batchId;
                 this.productId = productId;
                 this.uploadDate = uploadDate;
                 this.uploadedBy = uploadedBy;
                 
	}

    /**
     * @return the memberNoStr
     */
    public String getMemberNoStr() {
        return memberNoStr;
    }

    /**
     * @param memberNoStr the memberNoStr to set
     */
    public void setMemberNoStr(String memberNoStr) {
        this.memberNoStr = memberNoStr;
    }

    /**
     * @return the uploadedListAll
     */
    public List<FileUploadBean> getUploadedListAll() {
        return uploadedListAll;
    }

    /**
     * @param uploadedListAll the uploadedListAll to set
     */
    public void setUploadedListAll(List<FileUploadBean> uploadedListAll) {
        this.uploadedListAll = uploadedListAll;
    }

    /**
     * @return the uploadedListSuccess
     */
    public List<FileUploadBean> getUploadedListSuccess() {
        return uploadedListSuccess;
    }

    /**
     * @param uploadedListSuccess the uploadedListSuccess to set
     */
    public void setUploadedListSuccess(List<FileUploadBean> uploadedListSuccess) {
        this.uploadedListSuccess = uploadedListSuccess;
    }

    /**
     * @return the uploadedListFailed
     */
    public List<FileUploadBean> getUploadedListFailed() {
        return uploadedListFailed;
    }

    /**
     * @param uploadedListFailed the uploadedListFailed to set
     */
    public void setUploadedListFailed(List<FileUploadBean> uploadedListFailed) {
        this.uploadedListFailed = uploadedListFailed;
    }

    /**
     * @return the userUploadcount
     */
    public int getUserUploadcount() {
        return userUploadcount;
    }

    /**
     * @param userUploadcount the userUploadcount to set
     */
    public void setUserUploadcount(int userUploadcount) {
        this.userUploadcount = userUploadcount;
    }

    /**
     * @return the validationMsg
     */
    public String getValidationMsg() {
        return validationMsg;
    }

    /**
     * @param validationMsg the validationMsg to set
     */
    public void setValidationMsg(String validationMsg) {
        this.validationMsg = validationMsg;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
}
