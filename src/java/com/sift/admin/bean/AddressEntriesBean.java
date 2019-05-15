package com.sift.admin.bean;

import java.util.Date;

public class AddressEntriesBean{
    private Integer id;
    private String keyId;
    private String typeId;
    private String addrFieldName;
    private String addrFieldValue;
    private String serialPos;
    private String active;
    private String deleted;
    private Date creationDate;
    private String createdBy;
    private Date lastModificationDate;
    private String lastModifiedBy;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getAddrFieldName() {
		return addrFieldName;
	}
	public void setAddrFieldName(String addrFieldName) {
		this.addrFieldName = addrFieldName;
	}
	
	public String getAddrFieldValue() {
		return addrFieldValue;
	}
	public void setAddrFieldValue(String addrFieldValue) {
		this.addrFieldValue = addrFieldValue;
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
	public String getSerialPos() {
		return serialPos;
	}
	public void setSerialPos(String serialPos) {
		this.serialPos = serialPos;
	}

}
