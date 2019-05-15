package com.sift.financial.member;

import java.sql.Timestamp;

/**
 * AddressType entity. @author MyEclipse Persistence Tools
 */

public class AddressType implements java.io.Serializable {

	// Fields

	private Long id;
	private String typeName;
	private String active;
	private String createdBy;
	private Timestamp creationDate;
	private String deleted;
	private Timestamp lastModificationDate;
	private String lastModifiedBy;

	// Constructors

	/** default constructor */
	public AddressType() {
	}

	/** minimal constructor */
	public AddressType(String typeName) {
		this.typeName = typeName;
	}

	/** full constructor */
	public AddressType(String typeName, String active, String createdBy,
			Timestamp creationDate, String deleted,
			Timestamp lastModificationDate, String lastModifiedBy) {
		this.typeName = typeName;
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

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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