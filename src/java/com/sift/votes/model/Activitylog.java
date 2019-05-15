/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;


import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yomi-pc
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Activitylog implements java.io.Serializable{
        @XmlElement(name = "logId")
        private Integer logId;
        @XmlElement(name = "event")
	private int event;
        @XmlElement(name = "username")
	private String username;
        @XmlElement(name = "description")
	private String description;
        @XmlElement(name = "actionDate")
	private Date actionDate;
        @XmlElement(name = "action")
	private String action;
        @XmlElement(name = "actionItem")
	private String actionItem;
        @XmlElement(name = "actionResult")
	private String actionResult;
        @XmlElement(name = "ipaddress")
        private String ipaddress;
        @XmlElement(name = "agmId")
	private int agmid;
        @XmlElement(name = "companyid")
	private int companyid;
        @XmlElement(name = "timezone")
	private String timezone;
	private transient String toDate;
	

	// Constructors

	/** default constructor */
	public Activitylog() {
	}

	/** full constructor */
	public Activitylog(int event, String username, String description,
			Date actionDate, String action, String actionItem,
			String actionResult,String ipaddress,int agmid,int companyid, String timezone) {
		this.event = event;
		this.username = username;
		this.description = description;
		this.actionDate = actionDate;
		this.action = action;
		this.actionItem = actionItem;
		this.actionResult = actionResult;
                this.ipaddress = ipaddress;
		this.agmid = agmid;
		this.companyid = companyid;
                this.timezone = timezone;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public int getEvent() {
		return this.event;
	}

	public void setEvent(int event) {
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

	public Date getActionDate() {
		return this.actionDate;
	}

	public void setActionDate(Date actionDate) {
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

	public int getAgmid() {
		return agmid;
	}

	public void setAgmid(int agmid) {
		this.agmid = agmid;
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
        public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
}
