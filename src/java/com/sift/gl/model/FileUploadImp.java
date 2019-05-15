/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Olakunle Awotunbo
 */
@Entity
@Table(name = "file_upload")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FileUploadImp.findAll", query = "SELECT f FROM FileUploadImp f")})
public class FileUploadImp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "company_id")
    private int companyId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "branch_id")
    private int branchId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "file_name")
    private String fileName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "reference_number")
    private String referenceNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_id")
    private int productId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "user_id")
    private String userId;
    @Column(name = "processed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedDate;
    @Column(name = "failed_count")
    private Integer failedCount;
    @Column(name = "success_count")
    private Integer successCount;
    @Column(name = "total_records")
    private Integer totalRecords;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "file_sum")
    private Float fileSum;
    @Column(name = "processed_sum")
    private Float processedSum;
    @Column(name = "is_verified")
    private int isVerified;
    @Size(max = 50)
    @Column(name = "verifier_id")
    private String verifierId;
    @Column(name = "to_create_acct")
    private int toCreateAcct;
    @Column(name = "user_upload_sum")
    private Double userUploadSum;
    @Column(name = "upload_type")
    private String uploadType;
    @Column(name = "user_upload_count")
    private int userUploadcount;
    @Column(name = "processing_status")
    private String processingStatus;
    @Column(name = "approved_by")
    private String approvedBy;
            
    public FileUploadImp() {
    }

    public FileUploadImp(Integer id) {
        this.id = id;
    }

    public FileUploadImp(Integer id, int companyId, int branchId, String fileName, String location, int status, String referenceNumber, int productId, String userId) {
        this.id = id;
        this.companyId = companyId;
        this.branchId = branchId;
        this.fileName = fileName;
        this.location = location;
        this.status = status;
        this.referenceNumber = referenceNumber;
        this.productId = productId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    public Integer getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(Integer failedCount) {
        this.failedCount = failedCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Float getFileSum() {
        return fileSum;
    }

    public void setFileSum(Float fileSum) {
        this.fileSum = fileSum;
    }

    public Float getProcessedSum() {
        return processedSum;
    }

    public void setProcessedSum(Float processedSum) {
        this.processedSum = processedSum;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public String getVerifierId() {
        return verifierId;
    }

    public void setVerifierId(String verifierId) {
        this.verifierId = verifierId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FileUploadImp)) {
            return false;
        }
        FileUploadImp other = (FileUploadImp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sift.gl.model.FileUploadImp[ id=" + id + " ]";
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

}
