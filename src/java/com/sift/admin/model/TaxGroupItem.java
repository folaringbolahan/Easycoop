package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TAX_GROUP_ITEMS")
public class TaxGroupItem{
    @Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;
    
    @Column(name="tax_group_id")
    private Integer taxGroupId;
    
    @Column(name="tax_id")
    private Integer taxId;
    
    @Column(name="company_Id")
    private Integer companyId;
    
    @Column(name="branch_id")
    private Integer branchId;

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