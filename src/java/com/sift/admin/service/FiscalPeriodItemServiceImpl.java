package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.FiscalPeriodItem;
import com.sift.admin.bean.FiscalPeriodItemBean;
import com.sift.admin.dao.FiscalPeriodItemDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("fiscalPeriodItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FiscalPeriodItemServiceImpl implements FiscalPeriodItemService {
	 @Autowired
	 private FiscalPeriodItemDao fiscalPeriodItemDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addFiscalPeriodItem(FiscalPeriodItem fiscalPeriodItem) {
		 fiscalPeriodItemDao.addFiscalPeriodItem(fiscalPeriodItem);
	 }

	 public List<FiscalPeriodItem> listFiscalPeriodItems() {
	  return fiscalPeriodItemDao.listFiscalPeriodItem();
	 }

	 public List<FiscalPeriodItem> listFiscalPeriodItemsByYear(int Year) {
	  return fiscalPeriodItemDao.listFiscalPeriodItemByYear(Year);
	 }
	 
	 public List<FiscalPeriodItemBean> listFiscalPeriodItemByYear(String branchId,int Year){
		  return fiscalPeriodItemDao.listFiscalPeriodItemByYear(branchId,Year);
	 }
	 
	 public FiscalPeriodItem getFiscalPeriodItem(int id){
	  return fiscalPeriodItemDao.getFiscalPeriodItem(id);
	 }

	 public void deleteFiscalPeriodItem(FiscalPeriodItem fiscalPeriodItem) {
		 fiscalPeriodItemDao.deleteFiscalPeriodItem(fiscalPeriodItem);
	 }
}