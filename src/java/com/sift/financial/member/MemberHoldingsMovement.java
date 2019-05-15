package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.Date;

/**
 * MemberHoldingsMovement entity. @author MyEclipse Persistence Tools
 */

public class MemberHoldingsMovement implements java.io.Serializable {

	// Fields

	private Integer movementId;
	private Member member;
	private Event event;
	private CompStockType compStockType;
	private Company company;
	private String movementType;
	private Double ammount;
	private String delFlg;
	private Timestamp createdDate;
	private String createdBy;
	private Double movementHoldings;
	private Date effectiveDate;
        
        private Integer uploadId;
        private Integer batchStockId;
	

	// Constructors

	/** default constructor */
	public MemberHoldingsMovement() {
	}

	/** full constructor */
	public MemberHoldingsMovement(Member member, Event event,
			String movementType, String delFlg, Timestamp createdDate,
			String createdBy, Double movementHoldings, Company company,CompStockType compStockType, Date effectiveDate,Integer uploadId, Integer batchStockId) {
		this.member = member;
		this.event = event;
		this.movementType = movementType;
		this.delFlg = delFlg;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.movementHoldings = movementHoldings;
		this.company = company;
		this.compStockType= compStockType;
		this.effectiveDate = effectiveDate;
                this.batchStockId = batchStockId;
                this.uploadId = uploadId;
	}

	// Property accessors

	public Integer getMovementId() {
		return this.movementId;
	}

	public void setMovementId(Integer movementId) {
		this.movementId = movementId;
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getMovementType() {
		return this.movementType;
	}

	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Double getMovementHoldings() {
		return this.movementHoldings;
	}

	public void setMovementHoldings(Double movementHoldings) {
		this.movementHoldings = movementHoldings;
	}

	public CompStockType getCompStockType() {
		return compStockType;
	}

	public void setCompStockType(CompStockType compStockType) {
		this.compStockType = compStockType;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Double getAmmount() {
		return ammount;
	}

	public void setAmmount(Double ammount) {
		this.ammount = ammount;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

        public Integer getUploadId() {
            return uploadId;
        }

        public void setUploadId(Integer uploadId) {
            this.uploadId = uploadId;
        }

        public Integer getBatchStockId() {
            return batchStockId;
        }

        public void setBatchStockId(Integer batchStockId) {
            this.batchStockId = batchStockId;
        }
       
}