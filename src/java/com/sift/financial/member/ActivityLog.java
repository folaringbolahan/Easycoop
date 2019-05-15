package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * ActivityLog entity. @author MyEclipse Persistence Tools
 */

public class ActivityLog implements java.io.Serializable {

	// Fields

	private Integer logId;
	private Event event;
	private String username;
	private String description;
	private Timestamp actionDate;
	private String action;
	private String actionItem;
	private String actionResult;
	private Integer branchId;
	private Integer companyId;
	private transient String fromDate;
	private transient String toDate;
	private String ipaddress;
	

	// Constructors

	/** default constructor */
	public ActivityLog() {
	}

	/** full constructor */
	public ActivityLog(Event event, String username, String description,
			Timestamp actionDate, String action, String actionItem,
			String actionResult) {
		this.event = event;
		this.username = username;
		this.description = description;
		this.actionDate = actionDate;
		this.action = action;
		this.actionItem = actionItem;
		this.actionResult = actionResult;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getActionDate() {
		return this.actionDate;
	}

	public void setActionDate(Timestamp actionDate) {
		this.actionDate = actionDate;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionItem() {
		return this.actionItem;
	}

	public void setActionItem(String actionItem) {
		this.actionItem = actionItem;
	}

	public String getActionResult() {
		return this.actionResult;
	}

	public void setActionResult(String actionResult) {
		this.actionResult = actionResult;
	}

	
	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	

}