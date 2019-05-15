/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Nelson Akpos
 */
@Entity
@Table(name="vot_fileupload")
public class VotFileUpload implements Serializable  {
    private static final long serialVersionUID = 1L;
     @Id 
    @Column(name="id")
    @GeneratedValue
    private int id;
     
   @Column(name="company_id")
    private int companyId;
   
   @Column(name="branch_id")
    private int branchId;
   
   @Column(name="file_name")
    private String fileName;
   
   @Column(name="location")
    private String location;
   
   @Column(name="status")
    private byte status = 0;
   
   @Column(name="reference_number")
    private String referenceNumber;
   
   @Column(name="user_id")
    private String userId;
   
   @Column(name="processed_date")
    private Date processedDate;
   
   @Column(name="is_verified")
    private byte isVerified = 0;
   
   @Column(name="is_approved")
    private byte isApproved = 0;
   
   @Column(name="failed_count")
    private int failedCount = 0;
   
   @Column(name="success_count")
    private int successCount = 0;
   
   @Column(name="total_records")
    private int totalRecords = 0; 
   
    public VotFileUpload() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the companyId
     */
    public int getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the branchId
     */
    public int getBranchId() {
        return branchId;
    }

    /**
     * @param branchId the branchId to set
     */
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the status
     */
    public byte getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(byte status) {
        this.status = status;
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

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the processedDate
     */
    public Date getProcessedDate() {
        return processedDate;
    }

    /**
     * @param processedDate the processedDate to set
     */
    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    /**
     * @return the isVerified
     */
    public byte getIsVerified() {
        return isVerified;
    }

    /**
     * @param isVerified the isVerified to set
     */
    public void setIsVerified(byte isVerified) {
        this.isVerified = isVerified;
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
     * @return the failedCount
     */
    public int getFailedCount() {
        return failedCount;
    }

    /**
     * @param failedCount the failedCount to set
     */
    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    /**
     * @return the successCount
     */
    public int getSuccessCount() {
        return successCount;
    }

    /**
     * @param successCount the successCount to set
     */
    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    /**
     * @return the totalRecords
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * @param totalRecords the totalRecords to set
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }
    
    
}
