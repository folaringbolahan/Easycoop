package com.sift.admin.dao;

import java.util.Date;
import java.util.List;

import com.sift.admin.model.Country;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface CountryDao{
	 public void addCountry(Country addDetails);
	 public List<Country> listCountry();
	 public Country getCountry(int id);
	 public void deleteCountry(Country addDetails);
	 public String getTimeZone(String companyId);
	 public Date getDateByZone(String companyId);
}