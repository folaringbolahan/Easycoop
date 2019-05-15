package com.sift.admin.ws.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.Country;
import com.sift.admin.model.Currency;
import com.sift.admin.dao.*;
import com.sift.admin.bean.*;
import com.sift.loan.utility.BeanMapperUtility;

import org.springframework.beans.factory.annotation.Autowired;

@Service("countryWSService")
public class CountryWSServiceImpl implements CountryWSService{
	@Autowired
    private CountryDao countryDao;

	@Override public String createOrSaveNewCountry(CountryBean country){
		Country countryObj = new Country();

		countryObj.setCountryCode(country.getCountryCode());
		countryObj.setCountryName(country.getCountryName());
		countryObj.setActive(country.getActive());
		countryObj.setDeleted(country.getDeleted());
		countryObj.setCreatedBy(country.getCreatedBy());
	    countryObj.setCreationDate(new java.util.Date());
	    countryObj.setLastModifiedBy(country.getCreatedBy());
	    countryObj.setLastModificationDate(new java.util.Date());

	    countryDao.addCountry(countryObj);
		return null;
	}

	@Override public CountryBean getCountryInfo(int id){
		Country country= countryDao.getCountry(id);
		CountryBean countryObj = new CountryBean();

		countryObj.setId(country.getId());
		countryObj.setCountryCode(country.getCountryCode());
		countryObj.setCountryName(country.getCountryName());
		countryObj.setActive(country.getActive());
		countryObj.setDeleted(country.getDeleted());
		countryObj.setCreatedBy(country.getCreatedBy());
	    countryObj.setCreationDate(new java.util.Date());
	    countryObj.setLastModifiedBy(country.getCreatedBy());
	    countryObj.setLastModificationDate(new java.util.Date());

		return countryObj;
	}

	@Override public String updateCountryInfo(CountryBean country){
		Country countryObj = new Country();

		countryObj.setId(country.getId());
		countryObj.setCountryCode(country.getCountryCode());
		countryObj.setCountryName(country.getCountryName());
		countryObj.setActive(country.getActive());
		countryObj.setDeleted(country.getDeleted());
		countryObj.setCreatedBy(country.getCreatedBy());
	    countryObj.setCreationDate(new java.util.Date());
	    countryObj.setLastModifiedBy(country.getCreatedBy());
	    countryObj.setLastModificationDate(new java.util.Date());

	    countryDao.addCountry(countryObj);
		return null;
	}
    
	@Override
    public String deleteCountryInfo(CountryBean country){
    	Country countryObj = new Country();
    	countryObj.setId(country.getId());

    	countryDao.deleteCountry(countryObj);
		return null;
    }

	@Override public CountryList getAllCountries(){   	
		BeanMapperUtility mapper=new BeanMapperUtility();
		CountryList countryObj = new CountryList();
		
		List<CountryBean> beans=new ArrayList<CountryBean>();
		List<Country> models=countryDao.listCountry();		
		beans=mapper.prepareListofCountryBean(models);
		
		countryObj.country=beans;		
		return countryObj;
    }
}