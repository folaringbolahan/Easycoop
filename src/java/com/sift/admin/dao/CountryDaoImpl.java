package com.sift.admin.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.Country;
import com.sift.loan.utility.EazyCoopUtility;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("countryDao")
@Transactional
public class CountryDaoImpl implements CountryDao{

 @Autowired
 private SessionFactory sessionFactory;

 EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

 public void addCountry(Country country){
   sessionFactory.getCurrentSession().saveOrUpdate(country);
 }

 @SuppressWarnings("unchecked")
 public List<Country> listCountry(){
  return (List<Country>) sessionFactory.getCurrentSession().createCriteria(Country.class).list();
 }

 public Country getCountry(int id){
  return (Country) sessionFactory.getCurrentSession().get(Country.class, id);
 }

 public void deleteCountry(Country country) {
	 sessionFactory.getCurrentSession().createQuery("DELETE FROM Country WHERE id = "+country.getId()).executeUpdate();
 }
 
 @SuppressWarnings("unchecked")
 public String getTimeZone(String companyId){
	  String value="";
	  String sql="select timez from countries where country_id=(select id from country_id from company where id="+companyId+")";
	  Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);  
      
	  value=(query==null || query.list().isEmpty())?"":query.list().get(0).toString();     	
	  return value;
 }
 
 @SuppressWarnings("unchecked")
 public Date getDateByZone(String companyId){
	 String dtimezone=getTimeZone(companyId);	 
	 return eazyCoopUTIL.getTimeByZone(dtimezone);
 }
}