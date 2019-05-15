package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.bean.CountryAddressFilterBean;
import com.sift.admin.model.CountryAddressFilter;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface CountryAddressFilterDao{
	 public void addCountryAddressFilter(CountryAddressFilter addDetails);
	 public List<CountryAddressFilter> listCountryAddressFilter();
	 public List<CountryAddressFilter> listCountryAddressFilter(String id);
	 public List<CountryAddressFilterBean> listCountryAddressFilterBean(String countryId);
	 public List<CountryAddressFilterBean> listCountryAddressFilterBean();
	 public CountryAddressFilter getCountryAddressFilter(int id);
	 public void deleteCountryAddressFilter(CountryAddressFilter addDetails);
}