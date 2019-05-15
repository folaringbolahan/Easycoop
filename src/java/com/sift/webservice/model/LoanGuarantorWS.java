/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.webservice.model;

/**
 *
 * @author Olakunle Awotunbo
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="LOAN_GUARANTOR")
@XmlRootElement(name="LOAN_GUARANTOR")
@XmlAccessorType(XmlAccessType.NONE)
public class LoanGuarantorWS{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
 
    @Column(name="COMPANY_ID")
    private String companyId;
    
    @Column(name="BRANCH_ID")
    private String branchId;
    
    @Column(name="LOAN_CASE_ID")
    private String loanCaseId;
    
    @Column(name="MEMBER_NO")
    private String memberNo;
 
    @Column(name="GUARANTOR_NO")
    private String guarantorNo;
 
    @Column(name="GUARANTOR_COMMENT")
    private String guarantorComment;
 
    @Column(name="ACCEPTANCE_STATUS")
    private String acceptanceStatus;
 
    @Column(name="REQUEST_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date requestDate;
    
    @Column(name="ACCEPTANCE_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date  acceptanceDate;

    @Column(name="CREATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date creationDate;
    
    @Column(name="CREATED_BY")
    private String createdBy;
    
    @Column(name="LAST_MODIFICATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastModificationDate;
    
    @Column(name="LAST_MODIFIED_BY")
    private String lastModifiedBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getGuarantorNo() {
		return guarantorNo;
	}

	public void setGuarantorNo(String guarantorNo) {
		this.guarantorNo = guarantorNo;
	}

	public String getGuarantorComment() {
		return guarantorComment;
	}

	public void setGuarantorComment(String guarantorComment) {
		this.guarantorComment = guarantorComment;
	}

	public String getAcceptanceStatus() {
		return acceptanceStatus;
	}

	public void setAcceptanceStatus(String acceptanceStatus) {
		this.acceptanceStatus = acceptanceStatus;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate){
		this.requestDate = requestDate;
	}

	public Date getAcceptanceDate(){
		return acceptanceDate;
	}

	public void setAcceptanceDate(Date acceptanceDate){
		this.acceptanceDate = acceptanceDate;
	}

	public Date getCreationDate(){
		return creationDate;
	}

	public void setCreationDate(Date creationDate){
		this.creationDate = creationDate;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public Date getLastModificationDate(){
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate){
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy(){
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy){
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getBranchId(){
		return branchId;
	}

	public void setBranchId(String branchId){
		this.branchId = branchId;
	}    
 }