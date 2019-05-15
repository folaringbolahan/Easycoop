package com.sift.admin.bean;

import java.util.Date;
import javax.persistence.Column;

public class AuthPermitBean{    
	private Integer id;
	private String  requestStatus;
	private String  email;
	private Date    requestDate;
	private Date    approvalDate;
	private String  approvalBy;
	private Integer companyId;
	private Integer branchId;
	private String  accessLevelCode;	
	private String  approvalComment;	
		
	public String getApprovalComment() {
		return approvalComment;
	}

	public void setApprovalComment(String approvalComment) {
		this.approvalComment = approvalComment;
	}

	public String getRequestStatus(){
		return requestStatus;
	}
	
	public void setRequestStatus(String requestStatus){
		this.requestStatus = requestStatus;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getRequestDate() {
		return requestDate;
	}
	
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	
	public Date getApprovalDate() {
		return approvalDate;
	}
	
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	public String getApprovalBy() {
		return approvalBy;
	}
	
	public void setApprovalBy(String approvalBy) {
		this.approvalBy = approvalBy;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public Integer getBranchId() {
		return branchId;
	}
	
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getAccessLevelCode() {
		return accessLevelCode;
	}

	public void setAccessLevelCode(String accessLevelCode) {
		this.accessLevelCode = accessLevelCode;
	}
}