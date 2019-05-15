package com.sift.admin.bean;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class TaxGroupItemBean{
    private Integer id;
    
    @NotNull(message = "Tax Group Id is required.")  
    @NotEmpty(message = "Tax Group Id is required.")
    private Integer taxGroupId;
    
    @NotNull(message = "Tax Id is required.")  
    @NotEmpty(message = "Tax Id is required.")
    private Integer taxId;
    
    @NotNull(message = "Company is required.")  
    @NotEmpty(message = "Company is required.")
    private Integer companyId;
    
    @NotNull(message = "Branch is required.")  
    @NotEmpty(message = "Branch is required.")
    private Integer branchId;
    private String companyName;
    private String branchName;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTaxGroupId() {
		return taxGroupId;
	}

	public void setTaxGroupId(Integer taxGroupId) {
		this.taxGroupId = taxGroupId;
	}

	public Integer getTaxId() {
		return taxId;
	}

	public void setTaxId(Integer taxId) {
		this.taxId = taxId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}   
}