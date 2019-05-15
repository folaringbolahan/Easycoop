package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.AppConfiguration;
import com.sift.admin.dao.AppConfigurationDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("AppConfigurationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AppConfigurationServiceImpl implements AppConfigurationService {
	 @Autowired
	 private AppConfigurationDao appConfigurationDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addAppConfiguration(AppConfiguration appConfiguration){
		 appConfigurationDao.addAppConfiguration(appConfiguration);
	 }

	 public List<AppConfiguration> listAppConfigurations(){
	    return appConfigurationDao.listAppConfigurations();
	 }
	 
	 public List<AppConfiguration> listAppConfigurations(String configType ,String companyId){
		return appConfigurationDao.listAppConfigurations(configType ,companyId);
	 }
	 
	 public List<AppConfiguration> listAppConfigurations(String configType ,String companyId,String branchId){
		return appConfigurationDao.listAppConfigurations(configType ,companyId,branchId);
	 }

	 public List<AppConfiguration> listAppConfigurationsByCompany(String companyId){
		return appConfigurationDao.listAppConfigurationsByCompany(companyId);
	 }
	 
	 public List<AppConfiguration> listAppConfigurationsByCompanyByBranch(String companyId,String branchId){
		return appConfigurationDao.listAppConfigurationsByCompanyByBranch(companyId,branchId);
	 }
		
	 public AppConfiguration getAppConfiguration(int id){
	  return appConfigurationDao.getAppConfiguration(id);
	 }
	 
	 public AppConfiguration getAppConfiguration(String configName, String configType,String companyId){
		  return appConfigurationDao.getAppConfiguration(configName,configType,companyId);
	 }

	 public void deleteAppConfiguration(AppConfiguration appConfiguration){
		 appConfigurationDao.deleteAppConfiguration(appConfiguration);
	 }
}