package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name="LOAN_REPAYMENT_FREQUENCY")
public class LoanRepayFreq{    
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
    
    @Column(name="YEAR_DIVISOR")
    private int yearDivisor;
    
    @Column(name="NAME")
    private String name;

    @Column(name="CODE")
    private String code;
    
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public int getYearDivisor() {
		return yearDivisor;
	}

	public void setYearDivisor(int yearDivisor) {
		this.yearDivisor = yearDivisor;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}    
 }