package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="COUNTRIES")
public class Country{    
    @Id
    @Column(name="ID")
    @GeneratedValue    
    private Integer id;
    
    @Column(name="COUNTRY_CODE")
    private String countryCode;
    
    @Column(name="CURRENCY_CODE")
    private String currencyCode;

    @Column(name="COUNTRY_NAME")
    private String countryName;

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
    
    @Column(name="TIMEZ")
    private String timeZone;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getActive(){
		return active;
	}

	public void setActive(String active){
		this.active = active;
	}

	public String getDeleted(){
		return deleted;
	}

	public void setDeleted(String deleted){
		this.deleted = deleted;
	}

	public Date getCreationDate(){
		return creationDate;
	}

	public void setCreationDate(Date creationDate){
		this.creationDate = creationDate;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public Date getLastModificationDate(){
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate){
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy(){
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy){
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryName(){
		return countryName;
	}

	public void setCountryName(String countryName){
		this.countryName = countryName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}         
 }