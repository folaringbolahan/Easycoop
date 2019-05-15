package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name="ADDRESS_ENTRIES")
public class AddressEntries{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;

    @Column(name="KEY_ID")
    private String keyId;

    @Column(name="TYPE_ID")
    private String typeId;

    @Column(name="ADDR_FIELD_NAME")
    private String addrFieldName;

    @Column(name="ADDR_FIELD_VALUE")
    private String addrFieldValue;
    
    @Column(name="SERIAL_POS")
    private String serialPos;

    @Column(name="ACTIVE")
    private String active;
    
    @Column(name="DELETED")
    private String deleted;
 
    @Column(name="CREATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date creationDate;
    
    @Column(name="CREATED_BY")
    private String createdBy;
    
    @Column(name="LAST_MODIFICATION_DATE")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date lastModificationDate;
    
    @Column(name="LAST_MODIFIED_BY")
    private String lastModifiedBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddrFieldName() {
		return addrFieldName;
	}

	public void setAddrFieldName(String addrFieldName) {
		this.addrFieldName = addrFieldName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getAddrFieldValue() {
		return addrFieldValue;
	}

	public void setAddrFieldValue(String addrFieldValue) {
		this.addrFieldValue = addrFieldValue;
	}

	public String getSerialPos() {
		return serialPos;
	}

	public void setSerialPos(String serialPos) {
		this.serialPos = serialPos;
	}         
 }