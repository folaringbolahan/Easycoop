package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.Company;
import com.sift.admin.bean.AddressEntriesBean;
import com.sift.admin.dao.CompanyDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("companyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CompanyServiceImpl implements CompanyService {
	 @Autowired
	 private CompanyDao companyDao;
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addCompany(Company company) {
		 companyDao.addCompany(company);
	 }

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addCompany(Company company,List<AddressEntriesBean> beanList) {
		 return companyDao.addCompany(company,beanList);
	 }
	 
	 /*********************************************************************************************************************
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addCompany(Company company,List<AddressEntriesBean> beanList,boolean createCoyUser){
		 return companyDao.addCompany(company,beanList,createCoyUser);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addCompany(Company company,List<AddressEntriesBean> beanList,boolean createCoyUser,String contextPath){
		return companyDao.addCompany(company,beanList,createCoyUser,contextPath);
	 }
     **********************************************************************************************************************/
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addCompany(Company company,List<AddressEntriesBean> beanList,boolean createCoyUser,String contextPath,String currencyCode){
		return companyDao.addCompany(company,beanList,createCoyUser,contextPath,currencyCode);
	 }
	 	 
	 public List<Company> listCompanies() {
	  return companyDao.listCompanies();
	 }
	
	 public List<Company> listCompaniesDistinct(String companyId){
		 return companyDao.listCompaniesDistinct(companyId);
	 }
	 
	 public Company getCompany(int id){
	  return companyDao.getCompany(id);
	 }

	 public void deleteCompany(Company company) {
		 companyDao.deleteCompany(company);
	 }
	 
	 public void deleteCompany(Company company,List<AddressEntriesBean> beanList) {
		 companyDao.deleteCompany(company,beanList);
	 }
}