package com.sift.admin.ws.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.Currency;
import com.sift.admin.dao.*;
import com.sift.admin.bean.*;
import com.sift.loan.utility.BeanMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;

@Service("currencyWS")
@Transactional
public class CurrencyWSServiceImpl implements CurrencyWSService{
	@Autowired
    private CurrencyDao currencyDao;

	@Override public String createOrSaveNewCurrency(CurrencyBean currency){
		Currency currencyObj = new Currency();

		currencyObj.setIsBase(currency.getIsBase());
		currencyObj.setCurrencyCode(currency.getCurrencyCode());
		currencyObj.setCurrencyName(currency.getCurrencyName());
		currencyObj.setActive(currency.getActive());
		currencyObj.setDeleted(currency.getDeleted());
		currencyObj.setCreatedBy(currency.getCreatedBy());
	    currencyObj.setCreationDate(new java.util.Date());
	    currencyObj.setLastModifiedBy(currency.getCreatedBy());
	    currencyObj.setLastModificationDate(new java.util.Date());

	    currencyDao.addCurrency(currencyObj);
		return "ok";
	}

	@Override public CurrencyBean getCurrencyInfo(int id){
		Currency currency= currencyDao.getCurrency(id);
		CurrencyBean currencyObj = new CurrencyBean();

		currencyObj.setId(currency.getId());
		currencyObj.setIsBase(currency.getIsBase());
		currencyObj.setCurrencyCode(currency.getCurrencyCode());
		currencyObj.setCurrencyName(currency.getCurrencyName());
		currencyObj.setActive(currency.getActive());
		currencyObj.setDeleted(currency.getDeleted());
		currencyObj.setCreatedBy(currency.getCreatedBy());
	    currencyObj.setCreationDate(new java.util.Date());
	    currencyObj.setLastModifiedBy(currency.getCreatedBy());
	    currencyObj.setLastModificationDate(new java.util.Date());

		return currencyObj;
	}

	@Override public String updateCurrencyInfo(CurrencyBean currency){
		Currency currencyObj = new Currency();

		currencyObj.setId(currency.getId());
		currencyObj.setIsBase(currency.getIsBase());
		currencyObj.setCurrencyCode(currency.getCurrencyCode());
		currencyObj.setCurrencyName(currency.getCurrencyName());
		currencyObj.setActive(currency.getActive());
		currencyObj.setDeleted(currency.getDeleted());
		currencyObj.setCreatedBy(currency.getCreatedBy());
	    currencyObj.setCreationDate(new java.util.Date());
	    currencyObj.setLastModifiedBy(currency.getCreatedBy());
	    currencyObj.setLastModificationDate(new java.util.Date());

	    currencyDao.addCurrency(currencyObj);
	    return "ok";
	}

	@Override public String deleteCurrencyInfo(int id){
    	Currency currencyObj = new Currency();
    	currencyObj.setId(id);

    	currencyDao.deleteCurrency(currencyObj);
		return null;
    }

	@Override
    public CurrencyList getAllCurrencies(){
		BeanMapperUtility mapper=new BeanMapperUtility();
		CurrencyList currencyObj = new CurrencyList();
		
		List<CurrencyBean> beans=new ArrayList<CurrencyBean>();
		List<Currency> models=currencyDao.listCurrency();		
		beans=mapper.prepareListofCurrencyBean(models);
		
		currencyObj.currency=beans;
    	return currencyObj;
    }
}