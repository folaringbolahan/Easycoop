package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.AppConfiguration;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface AppConfigurationService { 
	public void addAppConfiguration(AppConfiguration appConfiguration);
	public List<AppConfiguration> listAppConfigurations();
	public List<AppConfiguration> listAppConfigurationsByCompany(String companyId);
	public List<AppConfiguration> listAppConfigurationsByCompanyByBranch(String companyId,String branchId);
	public List<AppConfiguration> listAppConfigurations(String configType ,String companyId);
	public List<AppConfiguration> listAppConfigurations(String configType ,String companyId,String branchId);
	public AppConfiguration getAppConfiguration(int id); 
	public AppConfiguration getAppConfiguration(String configName, String configType ,String companyId);
	public void deleteAppConfiguration(AppConfiguration appConfiguration);
}