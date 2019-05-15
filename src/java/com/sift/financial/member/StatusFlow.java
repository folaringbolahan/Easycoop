package com.sift.financial.member;

/**
 * StatusFlow entity. @author MyEclipse Persistence Tools
 */

public class StatusFlow implements java.io.Serializable {

	// Fields

	private Integer statusFlowId;
	private Status status;
	private Event event;
	private Integer statusSuccessId;
	private Integer statusFailId;
	private Integer statusValidId;

	// Constructors

	/** default constructor */
	public StatusFlow() {
	}

	/** full constructor */
	public StatusFlow(Status status, Event event, Integer statusSuccessId,
			Integer statusFailId, Integer statusValidId) {
		this.status = status;
		this.event = event;
		this.statusSuccessId = statusSuccessId;
		this.statusFailId = statusFailId;
		this.statusValidId = statusValidId;
	}

	// Property accessors

	public Integer getStatusFlowId() {
		return this.statusFlowId;
	}

	public void setStatusFlowId(Integer statusFlowId) {
		this.statusFlowId = statusFlowId;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	public Integer getStatusSuccessId() {
		return this.statusSuccessId;
	}

	public void setStatusSuccessId(Integer statusSuccessId) {
		this.statusSuccessId = statusSuccessId;
	}

	public Integer getStatusFailId() {
		return this.statusFailId;
	}

	public void setStatusFailId(Integer statusFailId) {
		this.statusFailId = statusFailId;
	}

	public Integer getStatusValidId() {
		return this.statusValidId;
	}

	public void setStatusValidId(Integer statusValidId) {
		this.statusValidId = statusValidId;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}