package com.sift.admin.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AUTH_PERMITS")
public class AuthPermit{
    @Id
    @Column(name="ID")
    @GeneratedValue
	private Integer id;
	
	@Column(name="REQUEST_STATUS")
	private String  requestStatus;
	
	@Column(name="EMAIL")
	private String  email;
	
	@Column(name="REQUEST_DATE")
	private Date    requestDate;
	
	@Column(name="APPROVAL_DATE")
	private Date    approvalDate;
	
	@Column(name="APPROVAL_BY")
	private String  approvalBy;
	
	@Column(name="APPROVAL_COMMENT")
	private String  approvalComment;	
	
	@Column(name="COMPANY_ID")
	private Integer companyId;
	
	@Column(name="BRANCH_ID")
	private Integer branchId;
	
	@Column(name="ACCESS_LEVEL_CODE")
	private String accessLevelCode;	
	
	public String getRequestStatus() {
		return requestStatus;
	}
	
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getRequestDate(){
		return requestDate;
	}
	
	public void setRequestDate(Date requestDate){
		this.requestDate = requestDate;
	}
	
	public Date getApprovalDate(){
		return approvalDate;
	}
	
	public void setApprovalDate(Date approvalDate){
		this.approvalDate = approvalDate;
	}
	
	public String getApprovalBy(){
		return approvalBy;
	}
	
	public void setApprovalBy(String approvalBy){
		this.approvalBy = approvalBy;
	}
	
	public Integer getCompanyId(){
		return companyId;
	}
	
	public void setCompanyId(Integer companyId){
		this.companyId = companyId;
	}
	
	public Integer getBranchId(){
		return branchId;
	}
	
	public void setBranchId(Integer branchId){
		this.branchId = branchId;
	}

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getAccessLevelCode(){
		return accessLevelCode;
	}

	public void setAccessLevelCode(String accessLevelCode){
		this.accessLevelCode = accessLevelCode;
	}

	public String getApprovalComment(){
		return approvalComment;
	}

	public void setApprovalComment(String approvalComment){
		this.approvalComment = approvalComment;
	}	
}