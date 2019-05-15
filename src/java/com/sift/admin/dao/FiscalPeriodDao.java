package com.sift.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.sift.admin.bean.FiscalPeriodBean;
import com.sift.admin.model.FiscalPeriod;
import com.sift.admin.model.FiscalPeriodItem;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface FiscalPeriodDao {
	 public void addFiscalPeriod(FiscalPeriod fiscalPeriod);
	 public boolean addFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems);
	 public void updateFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems);
	 public boolean addNewFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems);
	 public List<FiscalPeriod> listFiscalPeriod();
	 public List<FiscalPeriod> listFiscalPeriodsByCompany(String companyId); 
	 public List<FiscalPeriod> listFiscalPeriodsByBranch(String branchId);
	 public List<FiscalPeriodBean> listFiscalPeriodsByBranchBean(String branchId);
	 public FiscalPeriod getFiscalPeriod(int typeid);
	 public void deleteFiscalPeriod(FiscalPeriod fiscalPeriod);
}