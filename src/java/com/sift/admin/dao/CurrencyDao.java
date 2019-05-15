package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.Currency;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface CurrencyDao {
	 public void addCurrency(Currency currency);
	 public List<Currency> listCurrency();
	 public Currency getCurrency(int typeid);
	 public void deleteCurrency(Currency currency);
}