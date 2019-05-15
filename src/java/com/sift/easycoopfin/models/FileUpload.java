/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author logzy
 */
public class FileUpload implements Serializable {

    public FileUpload() {
    }
    private int id;
    private int companyId;
    private int branchId;
    private String fileName;
    private String location;
    private byte status = 0;
    private String referenceNumber;
    private int productId;
    private String userId;
    private Date processedDate;
    private byte isVerified = 0;
    private byte isApproved = 0;
    private int failedCount = 0;
    private int successCount = 0;
    private int totalRecords = 0;
    /*private float fileSum = 0f;
    private float processedSum = 0f;
    */
    //private double fileSum = 0.0;
    private BigDecimal fileSum;
    private BigDecimal processedSum;
    private String verifierId;
    private String state = "F";
    private int attemptCount = 0;
    private String approvedBy;
    
    private void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public int getORMID() {
        return getId();
    }

    public void setCompanyId(int value) {
        this.companyId = value;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setBranchId(int value) {
        this.branchId = value;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setFileName(String value) {
        this.fileName = value;
    }

    public String getFileName() {
        return fileName;
    }

    public void setLocation(String value) {
        this.location = value;
    }

    public String getLocation() {
        return location;
    }

    public void setReferenceNumber(String value) {
        this.referenceNumber = value;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setUserId(String value) {
        this.userId = value;
    }

    public String getUserId() {
        return userId;
    }

    public void setStatus(byte value) {
        this.status = value;
    }

    public byte getStatus() {
        return status;
    }

    public void setProductId(int value) {
        this.productId = value;
    }

    public int getProductId() {
        return productId;
    }

    public void setProcessedDate(Date value) {
        this.processedDate = value;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setIsVerified(byte value) {
        this.isVerified = value;
    }

    public byte getIsVerified() {
        return isVerified;
    }

    public void setFailedCount(int value) {
        this.failedCount = value;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setSuccessCount(int value) {
        this.successCount = value;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setTotalRecords(int value) {
        this.totalRecords = value;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setFileSum(BigDecimal value) {
        this.fileSum = value;
    }

    public BigDecimal getFileSum() {
        return fileSum;
    }

    public void setProcessedSum(BigDecimal value) {
        this.processedSum = value;
    }

    public BigDecimal getProcessedSum() {
        return processedSum;
    }

    public void setVerifierId(String value) {
        this.verifierId = value;
    }

    public String getVerifierId() {
        return verifierId;
    }
 public void setState(String value){
            this.state = value;
        }
        public String getState(){
            return state;
        }
        public void setAttemptCount(int value){
            this.attemptCount = value;
        }
        public int getAttemptCount(){
            return attemptCount;
        }
    public String toString() {
        return String.valueOf(getId());
    }

    /**
     * @return the isApproved
     */
    public byte getIsApproved() {
        return isApproved;
    }

    /**
     * @param isApproved the isApproved to set
     */
    public void setIsApproved(byte isApproved) {
        this.isApproved = isApproved;
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
}
