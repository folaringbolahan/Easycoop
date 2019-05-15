package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.Currency;
import com.sift.admin.dao.CurrencyDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("currencyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CurrencyServiceImpl implements CurrencyService {
	 @Autowired
	 private CurrencyDao currencyDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addCurrency(Currency currency) {
		 currencyDao.addCurrency(currency);
	 }

	 public List<Currency> listCurrency() {
	  return currencyDao.listCurrency();
	 }

	 public Currency getCurrency(int id){
	  return currencyDao.getCurrency(id);
	 }

	 public void deleteCurrency(Currency currency) {
		 currencyDao.deleteCurrency(currency);
	 }
}