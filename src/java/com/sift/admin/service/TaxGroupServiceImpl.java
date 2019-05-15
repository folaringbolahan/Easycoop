package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.TaxGroup;
import com.sift.admin.dao.TaxGroupDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("taxGroupService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TaxGroupServiceImpl implements TaxGroupService {
	 @Autowired
	 private TaxGroupDao taxGroupDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addTaxGroup(TaxGroup taxGroup) {
		 taxGroupDao.addTaxGroup(taxGroup);
	 }

	 public List<TaxGroup> listTaxGroups(){
	     return taxGroupDao.listTaxGroups();
	 }

	 public List<TaxGroup> listTaxGroupsByCompanyId(Integer companyId){
	     return taxGroupDao.listTaxGroupsByCompanyId(companyId);
	 }
	 
	 public List<TaxGroup> listTaxGroupsByBranchId(Integer branchId){
	     return taxGroupDao.listTaxGroupsByBranchId(branchId);
	 }
		
	 public TaxGroup getTaxGroup(int id){
	     return taxGroupDao.getTaxGroup(id);
	 }

	 public void deleteTaxGroup(TaxGroup taxGroup) {
		 taxGroupDao.deleteTaxGroup(taxGroup);
	 }
}