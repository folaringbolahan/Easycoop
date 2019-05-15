package com.sift.admin.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.FiscalPeriod;
import com.sift.admin.model.FiscalPeriodItem;
import com.sift.admin.bean.FiscalPeriodBean;
import com.sift.admin.dao.FiscalPeriodDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("fiscalPeriodService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FiscalPeriodServiceImpl implements FiscalPeriodService {
	 @Autowired
	 private FiscalPeriodDao fiscalPeriodDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addFiscalPeriod(FiscalPeriod fiscalPeriod) {
		 fiscalPeriodDao.addFiscalPeriod(fiscalPeriod);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addNewFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems){
		 return fiscalPeriodDao.addNewFiscalPeriod(fiscalPeriod,fpitems);
	 }

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void updateFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems){
		 fiscalPeriodDao.updateFiscalPeriod(fiscalPeriod,fpitems);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems){
		 return fiscalPeriodDao.addFiscalPeriod(fiscalPeriod,fpitems);
	 }
	 
	 public List<FiscalPeriod> listFiscalPeriods() {
	     return fiscalPeriodDao.listFiscalPeriod();
	 }

	 public List<FiscalPeriod> listFiscalPeriodsByCompany(String companyId) {
		 return fiscalPeriodDao.listFiscalPeriodsByCompany(companyId);
	 } 	 
	 
	 public List<FiscalPeriod> listFiscalPeriodsByBranch(String branchId) {
		 return fiscalPeriodDao.listFiscalPeriodsByBranch(branchId);
	 } 
	 
	 public List<FiscalPeriodBean> listFiscalPeriodsByBranchBean(String branchId){
		 return fiscalPeriodDao.listFiscalPeriodsByBranchBean(branchId);
	 }

	 public FiscalPeriod getFiscalPeriod(int id){
	     return fiscalPeriodDao.getFiscalPeriod(id);
	 }

	 public void deleteFiscalPeriod(FiscalPeriod fiscalPeriod) {
		 fiscalPeriodDao.deleteFiscalPeriod(fiscalPeriod);
	 }
}