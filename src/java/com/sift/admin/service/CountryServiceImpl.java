package com.sift.admin.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.Country;
import com.sift.admin.dao.CountryDao;

/**
 * @author XTOFEL CONSULT
 */
@Service("countryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CountryServiceImpl implements CountryService{
	 @Autowired
	 private CountryDao countryDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addCountry(Country country){
		 countryDao.addCountry(country);
	 }

	 public List<Country> listCountry(){
	     return countryDao.listCountry();
	 }

	 public Country getCountry(int id){
	     return countryDao.getCountry(id);
	 }

	 public void deleteCountry(Country country){
		 countryDao.deleteCountry(country);
	 }
	 
	 public String getTimeZone(String companyId){
		 return countryDao.getTimeZone(companyId);
	 }
	 
	 public Date getDateByZone(String companyId){
		 return countryDao.getDateByZone(companyId);
	 }
}