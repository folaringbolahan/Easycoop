package com.sift.admin.service;

import java.util.List;

import com.sift.admin.bean.FiscalPeriodItemBean;
import com.sift.admin.model.FiscalPeriod;
import com.sift.admin.model.FiscalPeriodItem;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface FiscalPeriodItemService { 
	public void addFiscalPeriodItem(FiscalPeriodItem fiscalPeriodItem);
	public List<FiscalPeriodItem> listFiscalPeriodItems(); 
	public List<FiscalPeriodItem> listFiscalPeriodItemsByYear(int Year); 
	public List<FiscalPeriodItemBean> listFiscalPeriodItemByYear(String branchId,int Year);
	public FiscalPeriodItem getFiscalPeriodItem(int periodId); 
	public void deleteFiscalPeriodItem(FiscalPeriodItem fiscalPeriodItem);
}