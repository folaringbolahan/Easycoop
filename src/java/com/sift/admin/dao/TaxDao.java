package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.Tax;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface TaxDao{
	 public void addTax(Tax addDetails);
	 public List<Tax> listTax();
	 public List<Tax> listTaxByBranch(Integer branchId);
	 public Tax getTax(int id);
	 public void deleteTax(Tax addDetails);
}