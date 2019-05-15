package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.bean.FiscalPeriodItemBean;
import com.sift.admin.model.FiscalPeriodItem;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface FiscalPeriodItemDao {
	 public void addFiscalPeriodItem(FiscalPeriodItem fiscalPeriodItem);
	 public List<FiscalPeriodItem> listFiscalPeriodItem();
	 public List<FiscalPeriodItem> listFiscalPeriodItemByYear(int Year);
	 public List<FiscalPeriodItemBean> listFiscalPeriodItemByYear(String branchId,int Year);
	 public FiscalPeriodItem getFiscalPeriodItem(int id);
	 public void deleteFiscalPeriodItem(FiscalPeriodItem fiscalPeriodItem);
}