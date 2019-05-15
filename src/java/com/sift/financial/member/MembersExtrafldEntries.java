package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * AddressEntries entity. @author MyEclipse Persistence Tools
 */

public class MembersExtrafldEntries implements java.io.Serializable {

	// Fields

	private Long id;
	private Long keyId;
	private String typeId;
	private String extraFieldName;
	private String extraFieldValue;
        private Integer extraFieldoptionId;
	private String extraFieldoptionValue;
        private Integer extraFieldId;
	private String serialPos;
	private String active;
	private String createdBy;
	private Timestamp creationDate;
	private String deleted;
	private Timestamp lastModificationDate;
	private String lastModifiedBy;

	// Constructors

	/** default constructor */
	public MembersExtrafldEntries() {
	}

	/** minimal constructor */
	public MembersExtrafldEntries(Long keyId, String typeId, String extraFieldName,
			String extraFieldValue,Integer extraFieldId, String serialPos) {
		this.keyId = keyId;
		this.typeId = typeId;
		this.extraFieldName = extraFieldName;
		this.extraFieldValue = extraFieldValue;
                this.extraFieldId = extraFieldId;
		this.serialPos = serialPos;
	}

	/** full constructor */
	public MembersExtrafldEntries(Long keyId, String typeId, String extraFieldName,
			String extraFieldValue,Integer extraFieldId, String serialPos, String active,
			String createdBy, Timestamp creationDate, String deleted,
			Timestamp lastModificationDate, String lastModifiedBy) {
		this.keyId = keyId;
		this.typeId = typeId;
		this.extraFieldName = extraFieldName;
		this.extraFieldValue = extraFieldValue;
                this.extraFieldId = extraFieldId;
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

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getExtraFieldName() {
		return this.extraFieldName;
	}

	public void setExtraFieldName(String extraFieldName) {
		this.extraFieldName = extraFieldName;
	}
        public String getExtraFieldValue() {
		return this.extraFieldValue;
	}

	public void setExtraFieldValue(String extraFieldValue) {
		this.extraFieldValue = extraFieldValue;
	}
	public Integer getExtraFieldId() {
		return this.extraFieldId;
	}

	public void setExtraFieldId(Integer extraFieldId) {
		this.extraFieldId = extraFieldId;
	}
        public Integer getExtraFieldoptionId() {
		return this.extraFieldoptionId;
	}

	public void setExtraFieldoptionId(Integer extraFieldoptionId) {
		this.extraFieldoptionId = extraFieldoptionId;
	}

	public String getExtraFieldoptionValue() {
		return this.extraFieldoptionValue;
	}

	public void setExtraFieldoptionValue(String extraFieldoptionValue) {
		this.extraFieldoptionValue = extraFieldoptionValue;
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