package com.sift.admin.service;

import java.util.List;

import com.sift.admin.bean.CountryAddressFilterBean;
import com.sift.admin.model.CountryAddressFilter;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface CountryAddressFilterService { 
	public void addCountryAddressFilter(CountryAddressFilter countryAddressFilter);
	public List<CountryAddressFilter> listCountryAddressFilter(); 
	public List<CountryAddressFilter> listCountryAddressFilter(String id); 
	public List<CountryAddressFilterBean> listCountryAddressFilterBean(String countryId);
	public List<CountryAddressFilterBean> listCountryAddressFilterBean();
	public CountryAddressFilter getCountryAddressFilter(int id); 
	public void deleteCountryAddressFilter(CountryAddressFilter countryAddressFilter);
}