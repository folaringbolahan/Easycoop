package com.sift.admin.bean;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class LoanRepayFreqBean{   

	private Integer id;
	
	@NotEmpty(message = "name cannot be empty.")
    private String name;

    private String code;
	private int yearDivisor;
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

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
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

	public int getYearDivisor(){
		return yearDivisor;
	}

	public void setYearDivisor(int yearDivisor){
		this.yearDivisor = yearDivisor;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}       
 }