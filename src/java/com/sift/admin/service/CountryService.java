package com.sift.admin.service;

import java.util.Date;
import java.util.List;

import com.sift.admin.model.Country;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface CountryService { 
	public void addCountry(Country country);
	public List<Country> listCountry(); 
	public Country getCountry(int id); 
	public void deleteCountry(Country country);
	public String getTimeZone(String companyId);
	public Date getDateByZone(String companyId);
}