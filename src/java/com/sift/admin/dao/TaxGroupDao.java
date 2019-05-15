package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.TaxGroup;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface TaxGroupDao {
	 public void addTaxGroup(TaxGroup taxGroup);
	 public List<TaxGroup> listTaxGroups();
	 public List<TaxGroup> listTaxGroupsByCompanyId(Integer companyId);
	 public List<TaxGroup> listTaxGroupsByBranchId(Integer branchId);
	 public TaxGroup getTaxGroup(int typeid);
	 public void deleteTaxGroup(TaxGroup taxGroup);
}