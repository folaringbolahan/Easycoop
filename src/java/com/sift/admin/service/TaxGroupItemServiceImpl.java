package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.TaxGroupItem;
import com.sift.admin.dao.TaxGroupItemDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("taxGroupItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TaxGroupItemServiceImpl implements TaxGroupItemService{
	 @Autowired
	 private TaxGroupItemDao taxGroupItemDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addTaxGroupItem(TaxGroupItem taxGroupItem){
		 taxGroupItemDao.addTaxGroupItem(taxGroupItem);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addTaxGroupItem(TaxGroupItem taxGroupItem,String options[]){
		 taxGroupItemDao.addTaxGroupItem(taxGroupItem,options);
	 }

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addTaxGroupItem(TaxGroupItem taxGroupItem,String options[],String reportMenus[]){
		 taxGroupItemDao.addTaxGroupItem(taxGroupItem,options,reportMenus);
	 }
	 
	 public List<TaxGroupItem> listTaxGroupItem(){
	  return taxGroupItemDao.listTaxGroupItem();
	 }
	 
	 public List<TaxGroupItem> listTaxGroupItem(String usergroup){
		  return taxGroupItemDao.listTaxGroupItem(usergroup);
	 }

	 public TaxGroupItem getTaxGroupItem(String id){
	  return taxGroupItemDao.getTaxGroupItem(id);
	 }

	 public void deleteTaxGroupItem(TaxGroupItem taxGroupItem){
		 taxGroupItemDao.deleteTaxGroupItem(taxGroupItem);
	 }
}