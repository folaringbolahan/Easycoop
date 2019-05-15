package com.sift.admin.bean;

import java.util.*;
import javax.validation.constraints.Future;

import org.hibernate.validator.constraints.NotEmpty;

public class FiscalPeriodBean{    
    private Integer id;
    private Integer year;
    private Integer noOfPeriods;
    
    @NotEmpty(message = "Company is required.")
    private String companyId;

    @NotEmpty(message = "Branch is required.")
    private String branchId;
    
    private String active;
    private Date creationDate;
    private String createdBy;
    private Date lastModificationDate;
    private String lastModifiedBy;
    private String companyName;
    private String branchName;
    
    private ArrayList<FiscalPeriodItemBean> fiscalPeriodItems;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public void setLastModificationDate(Date lastModificationDate){
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy(){
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy){
		this.lastModifiedBy = lastModifiedBy;
	}

	public ArrayList<FiscalPeriodItemBean> getFiscalPeriodItems(){
		return fiscalPeriodItems;
	}

	public void setFiscalPeriodItems(
			ArrayList<FiscalPeriodItemBean> fiscalPeriodItems){
		this.fiscalPeriodItems = fiscalPeriodItems;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNoOfPeriods() {
		return noOfPeriods;
	}

	public void setNoOfPeriods(Integer noOfPeriods) {
		this.noOfPeriods = noOfPeriods;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}    
 }