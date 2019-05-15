package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.CountryAddressFilter;
import com.sift.admin.bean.CountryAddressFilterBean;
import com.sift.admin.dao.CountryAddressFilterDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("countryAddressFilterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CountryAddressFilterServiceImpl implements CountryAddressFilterService{
	 @Autowired
	 private CountryAddressFilterDao countryAddressFilterDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addCountryAddressFilter(CountryAddressFilter countryAddressFilter){
		 countryAddressFilterDao.addCountryAddressFilter(countryAddressFilter);
	 }

	 public List<CountryAddressFilter> listCountryAddressFilter(){
	  return countryAddressFilterDao.listCountryAddressFilter();
	 }

	 public List<CountryAddressFilter> listCountryAddressFilter(String id){
		  return countryAddressFilterDao.listCountryAddressFilter(id);
	 }
	 
	 public List<CountryAddressFilterBean> listCountryAddressFilterBean(String countryId){
		 return countryAddressFilterDao.listCountryAddressFilterBean(countryId);
	 }
	 
	 public List<CountryAddressFilterBean> listCountryAddressFilterBean(){
		 return countryAddressFilterDao.listCountryAddressFilterBean();
	 }

	 public CountryAddressFilter getCountryAddressFilter(int id){
	  return countryAddressFilterDao.getCountryAddressFilter(id);
	 }

	 public void deleteCountryAddressFilter(CountryAddressFilter countryAddressFilter){
		 countryAddressFilterDao.deleteCountryAddressFilter(countryAddressFilter);
	 }
}