package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.Tax;
import com.sift.admin.dao.TaxDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("taxService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TaxServiceImpl implements TaxService{
	 @Autowired
	 private TaxDao taxDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addTax(Tax tax){
		 taxDao.addTax(tax);
	 }

	 public List<Tax> listTax(){
	  return taxDao.listTax();
	 }
	 
	 public List<Tax> listTaxByBranch(Integer branchId){
		 return taxDao.listTaxByBranch(branchId);
	 }

	 public Tax getTax(int id){
	  return taxDao.getTax(id);
	 }

	 public void deleteTax(Tax tax){
		 taxDao.deleteTax(tax);
	 }
}