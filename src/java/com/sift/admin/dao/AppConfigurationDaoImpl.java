package com.sift.admin.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sift.admin.model.AppConfiguration;
import com.sift.admin.model.Company;

/**
 * @author Chris Faseun
 *
 */
@Repository("loanConfigurationDao")
public class AppConfigurationDaoImpl implements AppConfigurationDao{

@Autowired
private SessionFactory sessionFactory;

public void addAppConfiguration(AppConfiguration appConfiguration) {
   sessionFactory.getCurrentSession().saveOrUpdate(appConfiguration);
}

 @SuppressWarnings("unchecked")
 public List<AppConfiguration> listAppConfigurations(){
    return (List<AppConfiguration>) sessionFactory.getCurrentSession().createCriteria(AppConfiguration.class).list();
 }

 @SuppressWarnings("unchecked")
 public List<AppConfiguration> listAppConfigurations(String configType ,String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppConfiguration.class);
	 criteria.add(Restrictions.eq("configType",configType));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("active","Y"));
	 
	 return criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<AppConfiguration> listAppConfigurations(String configType ,String companyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppConfiguration.class);
	 criteria.add(Restrictions.eq("configType",configType));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("active","Y"));
	 
	 return criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<AppConfiguration> listAppConfigurationsByCompany(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppConfiguration.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("active","Y"));
	 
	 return criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<AppConfiguration> listAppConfigurationsByCompanyByBranch(String companyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppConfiguration.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("active","Y"));
	 
	 return criteria.list();
 }
 
 public AppConfiguration getAppConfiguration(int id){
  return (AppConfiguration) sessionFactory.getCurrentSession().get(AppConfiguration.class, id);
 }
 
public AppConfiguration getAppConfiguration(String configName, String configType,String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AppConfiguration.class);
	 criteria.add(Restrictions.eq("configName",configName));
	 criteria.add(Restrictions.eq("configType",configType));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("active","Y"));
	 
	 return  criteria==null?null: (AppConfiguration)criteria.list().get(0);
}

public void deleteAppConfiguration(AppConfiguration appConfiguration) {
  sessionFactory.getCurrentSession().createQuery("DELETE FROM AppConfig WHERE id = "+appConfiguration.getId()).executeUpdate();
 }
}