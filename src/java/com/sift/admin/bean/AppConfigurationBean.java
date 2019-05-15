package com.sift.admin.bean;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class AppConfigurationBean{
    private Integer id;
    
    @NotEmpty(message = "Config name is required.")
    private String configName;
    
    @NotEmpty(message = "Config Type is required.")
    private String configType;
    
    @NotEmpty(message = "Config Min Value is required.")
    private String configMinValue;
    
    @NotEmpty(message = "Config Max Value is required.")
    private String configMaxValue;
    
    private String branchId;
    private String computationType;
    private String formulaValue;
    private String operand;
    private String multiplier;
    
    @NotEmpty(message = "Company is required.")
    private String companyId;
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
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getConfigType() {
		return configType;
	}
	public void setConfigType(String configType) {
		this.configType = configType;
	}
	public String getConfigMinValue() {
		return configMinValue;
	}
	public void setConfigMinValue(String configMinValue) {
		this.configMinValue = configMinValue;
	}
	public String getConfigMaxValue() {
		return configMaxValue;
	}
	public void setConfigMaxValue(String configMaxValue) {
		this.configMaxValue = configMaxValue;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
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
	public String getComputationType() {
		return computationType;
	}
	public void setComputationType(String computationType) {
		this.computationType = computationType;
	}
	public String getFormulaValue() {
		return formulaValue;
	}
	public void setFormulaValue(String formulaValue) {
		this.formulaValue = formulaValue;
	}
	public String getOperand() {
		return operand;
	}
	public void setOperand(String operand) {
		this.operand = operand;
	}
	public String getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(String multiplier) {
		this.multiplier = multiplier;
	}    
 }