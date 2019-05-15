package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * AddressEntries entity. @author MyEclipse Persistence Tools
 */

public class AddressEntries implements java.io.Serializable {

	// Fields

	private Long id;
	private Long keyId;
	private Long typeId;
	private String addrFieldName;
	private String addrFieldValue;
	private String serialPos;
	private String active;
	private String createdBy;
	private Timestamp creationDate;
	private String deleted;
	private Timestamp lastModificationDate;
	private String lastModifiedBy;

	// Constructors

	/** default constructor */
	public AddressEntries() {
	}

	/** minimal constructor */
	public AddressEntries(Long keyId, Long typeId, String addrFieldName,
			String addrFieldValue, String serialPos) {
		this.keyId = keyId;
		this.typeId = typeId;
		this.addrFieldName = addrFieldName;
		this.addrFieldValue = addrFieldValue;
		this.serialPos = serialPos;
	}

	/** full constructor */
	public AddressEntries(Long keyId, Long typeId, String addrFieldName,
			String addrFieldValue, String serialPos, String active,
			String createdBy, Timestamp creationDate, String deleted,
			Timestamp lastModificationDate, String lastModifiedBy) {
		this.keyId = keyId;
		this.typeId = typeId;
		this.addrFieldName = addrFieldName;
		this.addrFieldValue = addrFieldValue;
		this.serialPos = serialPos;
		this.active = active;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.deleted = deleted;
		this.lastModificationDate = lastModificationDate;
		this.lastModifiedBy = lastModifiedBy;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getKeyId() {
		return this.keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	public Long getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getAddrFieldName() {
		return this.addrFieldName;
	}

	public void setAddrFieldName(String addrFieldName) {
		this.addrFieldName = addrFieldName;
	}

	public String getAddrFieldValue() {
		return this.addrFieldValue;
	}

	public void setAddrFieldValue(String addrFieldValue) {
		this.addrFieldValue = addrFieldValue;
	}

	public String getSerialPos() {
		return this.serialPos;
	}

	public void setSerialPos(String serialPos) {
		this.serialPos = serialPos;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Timestamp getLastModificationDate() {
		return this.lastModificationDate;
	}

	public void setLastModificationDate(Timestamp lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

}