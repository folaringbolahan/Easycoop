package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Event entity. @author MyEclipse Persistence Tools
 */

public class Event implements java.io.Serializable {

	// Fields

	private Integer eventId;
	private Module module;
	private String eventName;
	private String eventShort;
	private String createdBy;
	private Timestamp createdDate;
	private String delFlg;
	private Set eventProperties = new HashSet(0);
	private Set memberHoldingsMovements = new HashSet(0);
	private Set activityLogs = new HashSet(0);
	private Set mailNotifications = new HashSet(0);
        private String eventStockMove;

	// Constructors

	/** default constructor */
	public Event() {
	}

	/** minimal constructor */
	public Event(Module module, String eventName, String eventShort,
			String createdBy, Timestamp createdDate, String delFlg, String eventStockMove) {
		this.module = module;
		this.eventName = eventName;
		this.eventShort = eventShort;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.delFlg = delFlg;
                this.eventStockMove = eventStockMove;
	}

	/** full constructor */
	public Event(Module module, String eventName, String eventShort,
			String createdBy, Timestamp createdDate, String delFlg,
			Set eventProperties, Set memberHoldingsMovements, Set activityLogs,
			Set mailNotifications, String eventStockMove) {
		this.module = module;
		this.eventName = eventName;
		this.eventShort = eventShort;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.delFlg = delFlg;
		this.eventProperties = eventProperties;
		this.memberHoldingsMovements = memberHoldingsMovements;
		this.activityLogs = activityLogs;
		this.mailNotifications = mailNotifications;
                 this.eventStockMove = eventStockMove;
	}

	// Property accessors

	public Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventShort() {
		return this.eventShort;
	}

	public void setEventShort(String eventShort) {
		this.eventShort = eventShort;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public Set getEventProperties() {
		return this.eventProperties;
	}

	public void setEventProperties(Set eventProperties) {
		this.eventProperties = eventProperties;
	}

	public Set getMemberHoldingsMovements() {
		return this.memberHoldingsMovements;
	}

	public void setMemberHoldingsMovements(Set memberHoldingsMovements) {
		this.memberHoldingsMovements = memberHoldingsMovements;
	}

	public Set getActivityLogs() {
		return this.activityLogs;
	}

	public void setActivityLogs(Set activityLogs) {
		this.activityLogs = activityLogs;
	}

	public Set getMailNotifications() {
		return this.mailNotifications;
	}

	public void setMailNotifications(Set mailNotifications) {
		this.mailNotifications = mailNotifications;
	}

        public String getEventStockMove() {
            return eventStockMove;
        }

        public void setEventStockMove(String eventStockMove) {
            this.eventStockMove = eventStockMove;
        }
    
}