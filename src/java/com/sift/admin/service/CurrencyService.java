package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.Currency;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface CurrencyService { 
	public void addCurrency(Currency currency);
	public List<Currency> listCurrency(); 
	public Currency getCurrency(int id); 
	public void deleteCurrency(Currency currency);
}