package com.sift.financial;



public class ReqReviewBean {
	
	private String reviewType;
	private String postReviewAction;
	private String user;
	private Long evtRecId;
	private String message;
	private String wasRejected;
	private Long memberId;
	private Long branchId;
	private Long companyId;

	private String validationCode;
	private Object[] flowParam;
	private Object eventObject;
	
	private String expRemark;
	private String newStatus;
	
	
	public Long getEvtRecId() {
		return evtRecId;
	}
	
	public void setEvtRecId(Long evtRecId) {
		this.evtRecId = evtRecId;
	}
	
	public String getExpRemark() {
		return expRemark;
	}
	
	public void setExpRemark(String expRemark) {
		this.expRemark = expRemark;
	}
	
	public String getPostReviewAction() {
		return postReviewAction;
	}
	
	public void setPostReviewAction(String postReviewAction) {
		this.postReviewAction = postReviewAction;
	}
	
	public Object getEventObject() {
		return eventObject;
	}
	
	public void setEventObject(Object eventObject) {
		this.eventObject = eventObject;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getReviewType() {
		return reviewType;
	}
	
	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}
	
	public String getWasRejected() {
		return wasRejected;
	}
	
	public void setWasRejected(String wasRejected) {
		this.wasRejected = wasRejected;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
	public Object[] getFlowParam() {
		return flowParam;
	}
	
	public void setFlowParam(Object[] flowParam) {
		this.flowParam = flowParam;
	}
	
	public String getValidationCode() {
		return validationCode;
	}
	
	public void setValidationCode(String validationCode) {
		
		this.validationCode = validationCode;
		
	}
	
	
	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
}
