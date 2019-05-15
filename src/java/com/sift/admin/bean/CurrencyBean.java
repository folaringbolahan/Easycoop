package com.sift.admin.bean;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement(name = "Currency")
public class CurrencyBean{ 
	private Integer id;
	
    @NotNull(message = "Currency Code is required.")  
    @Size(min=3,max=4,message = "Currency name must be between 3 and 4 characters")
    private String currencyCode;
    
    @NotNull(message = "Currency Name cannot be empty.")  
    @Size(min=5,max=50,message = "Currency name must be between 5 and 50 characters")
    private String currencyName;
    
    private String isBase;    
    private String active;
    private String deleted;
    private Date creationDate;
    private String createdBy;
    private Date lastModificationDate;
    private String lastModifiedBy;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getCurrencyCode(){
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode){
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName(){
		return currencyName;
	}

	public void setCurrencyName(String currencyName){
		this.currencyName = currencyName;
	}

	public String getIsBase(){
		return isBase;
	}

	public void setIsBase(String isBase){
		this.isBase = isBase;
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
 }