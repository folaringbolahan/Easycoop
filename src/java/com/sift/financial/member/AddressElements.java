package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.Date;

/**
 * AddressElements entity. @author MyEclipse Persistence Tools
 */

public class AddressElements implements java.io.Serializable {

	// Fields

	private Long id;
	private String addrFieldName;
	private String active;
	private String deleted;
	private Date creationDate;
	private String createdBy;
	private String lastModifiedBy;
	private Timestamp lastModificationDate;

	// Constructors

	/** default constructor */
	public AddressElements() {
	}

	/** minimal constructor */
	public AddressElements(String createdBy) {
		this.createdBy = createdBy;
	}

	/** full constructor */
	public AddressElements(String addrFieldName, String active, String deleted,
			Date creationDate, String createdBy, String lastModifiedBy,
			Timestamp lastModificationDate) {
		this.addrFieldName = addrFieldName;
		this.active = active;
		this.deleted = deleted;
		this.creationDate = creationDate;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModificationDate = lastModificationDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddrFieldName() {
		return this.addrFieldName;
	}

	public void setAddrFieldName(String addrFieldName) {
		this.addrFieldName = addrFieldName;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getLastModificationDate() {
		return this.lastModificationDate;
	}

	public void setLastModificationDate(Timestamp lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

}