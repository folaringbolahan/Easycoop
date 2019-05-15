package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.Tax;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface TaxService { 
	public void addTax(Tax tax);
	public List<Tax> listTax(); 
	public List<Tax> listTaxByBranch(Integer branchId); 
	public Tax getTax(int id); 
	public void deleteTax(Tax tax);
}