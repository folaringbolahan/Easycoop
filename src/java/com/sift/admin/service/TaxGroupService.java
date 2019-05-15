package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.TaxGroup;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface TaxGroupService { 
	public void addTaxGroup(TaxGroup taxGroup);
	public List<TaxGroup> listTaxGroups(); 
	public List<TaxGroup> listTaxGroupsByCompanyId(Integer companyId);
	public List<TaxGroup> listTaxGroupsByBranchId(Integer branchId);
	public TaxGroup getTaxGroup(int id); 
	public void deleteTaxGroup(TaxGroup taxGroup);
}