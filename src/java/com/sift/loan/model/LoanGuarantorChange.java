/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.loan.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
/**
 *
 * @author Nelson Akpos
 */
@Entity
@Table(name="loan_guarantor_change")
public class LoanGuarantorChange {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer id;
    
    @Column(name="companyId")
    private String companyId;
    
    @Column(name="branchId")
    private String branchId;
    
    @Column(name="LoanCaseId")
    private String loanCaseId;
    
    @Column(name="memberNo")
    private String memberNo;
 
    @Column(name="guarantorNo")
    private String guarantorNo;
 
    @Column(name="guarantorComment")
    private String guarantorComment;
 
    @Column(name="acceptanceStatus")
    private String acceptanceStatus;
 
    @Column(name="requestDate")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date requestDate;
    
    @Column(name="acceptanceDate")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  acceptanceDate;

    @Column(name="creationDate")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date creationDate;
    
    @Column(name="createdBy")
    private String createdBy;
    
    @Column(name="lastModificationDate")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastModificationDate;
    
    @Column(name="lastModifiedBy")
    private String lastModifiedBy;
    
    @Column(name="guarantorReplaced")
    private String guarantorReplaced;
    
    @Column(name="approved")
    private String approved;

    
    
   
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the branchId
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * @param branchId the branchId to set
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    /**
     * @return the loanCaseId
     */
    public String getLoanCaseId() {
        return loanCaseId;
    }

    /**
     * @param loanCaseId the loanCaseId to set
     */
    public void setLoanCaseId(String loanCaseId) {
        this.loanCaseId = loanCaseId;
    }

    /**
     * @return the memberNo
     */
    public String getMemberNo() {
        return memberNo;
    }

    /**
     * @param memberNo the memberNo to set
     */
    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    /**
     * @return the guarantorNo
     */
    public String getGuarantorNo() {
        return guarantorNo;
    }

    /**
     * @param guarantorNo the guarantorNo to set
     */
    public void setGuarantorNo(String guarantorNo) {
        this.guarantorNo = guarantorNo;
    }

    /**
     * @return the guarantorComment
     */
    public String getGuarantorComment() {
        return guarantorComment;
    }

    /**
     * @param guarantorComment the guarantorComment to set
     */
    public void setGuarantorComment(String guarantorComment) {
        this.guarantorComment = guarantorComment;
    }

    /**
     * @return the acceptanceStatus
     */
    public String getAcceptanceStatus() {
        return acceptanceStatus;
    }

    /**
     * @param acceptanceStatus the acceptanceStatus to set
     */
    public void setAcceptanceStatus(String acceptanceStatus) {
        this.acceptanceStatus = acceptanceStatus;
    }

    /**
     * @return the requestDate
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return the acceptanceDate
     */
    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    /**
     * @param acceptanceDate the acceptanceDate to set
     */
    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the lastModificationDate
     */
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    /**
     * @param lastModificationDate the lastModificationDate to set
     */
    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    /**
     * @return the lastModifiedBy
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * @param lastModifiedBy the lastModifiedBy to set
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * @return the guarantorReplaced
     */
    public String getGuarantorReplaced() {
        return guarantorReplaced;
    }

    /**
     * @param guarantorReplaced the guarantorReplaced to set
     */
    public void setGuarantorReplaced(String guarantorReplaced) {
        this.guarantorReplaced = guarantorReplaced;
    }

    /**
     * @return the approved
     */
    public String getApproved() {
        return approved;
    }

    /**
     * @param approved the approved to set
     */
    public void setApproved(String approved) {
        this.approved = approved;
    }
    
    
    @Override
    public String toString() {
        return "LoanGuarantorChange{" + "id=" + id + ", companyId=" + companyId + ", branchId=" + branchId + ", loanCaseId=" + loanCaseId + ", memberNo=" + memberNo + ", guarantorNo=" + guarantorNo + ", guarantorComment=" + guarantorComment + ", acceptanceStatus=" + acceptanceStatus + ", requestDate=" + requestDate + ", acceptanceDate=" + acceptanceDate + ", creationDate=" + creationDate + ", createdBy=" + createdBy + ", lastModificationDate=" + lastModificationDate + ", lastModifiedBy=" + lastModifiedBy + ", guarantorReplaced=" + guarantorReplaced + ", approved=" + approved + '}';
    }

    
   
}
