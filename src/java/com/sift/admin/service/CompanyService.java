package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.Company;
import com.sift.admin.bean.AddressEntriesBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface CompanyService { 
	public void addCompany(Company company);
	public boolean addCompany(Company company,List<AddressEntriesBean> beanList);
	//public boolean addCompany(Company company,List<AddressEntriesBean> beanList,boolean createCoyUser);
	//public boolean addCompany(Company company,List<AddressEntriesBean> beanList,boolean createCoyUser,String contextPath);
	public boolean addCompany(Company company,List<AddressEntriesBean> beanList,boolean createCoyUser,String contextPath,String currencyCode);
	public List<Company> listCompanies(); 
	public List<Company> listCompaniesDistinct(String companyId);
	public Company getCompany(int id); 
	public void deleteCompany(Company company);
	public void deleteCompany(Company company,List<AddressEntriesBean> beanList);
}