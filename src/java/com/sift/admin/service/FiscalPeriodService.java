package com.sift.admin.service;

import java.util.*;

import com.sift.admin.bean.FiscalPeriodBean;
import com.sift.admin.model.*;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface FiscalPeriodService { 
	public void addFiscalPeriod(FiscalPeriod fiscalPeriod);
	public boolean addNewFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems);
	public void updateFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems);
	public boolean addFiscalPeriod(FiscalPeriod fiscalPeriod,ArrayList<FiscalPeriodItem> fpitems);
	public List<FiscalPeriod> listFiscalPeriods(); 
	public List<FiscalPeriod> listFiscalPeriodsByCompany(String companyId); 
	public List<FiscalPeriod> listFiscalPeriodsByBranch(String branchId); 
	public List<FiscalPeriodBean> listFiscalPeriodsByBranchBean(String branchId); 
	public FiscalPeriod getFiscalPeriod(int id); 
	public void deleteFiscalPeriod(FiscalPeriod fiscalPeriod);
}