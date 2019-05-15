package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.bean.CountryAddressFilterBean;
import com.sift.admin.bean.UserBean;
import com.sift.admin.model.CountryAddressFilter;
import com.sift.loan.utility.HelperUtility;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("countryAddressFilterDao")
public class CountryAddressFilterDaoImpl implements CountryAddressFilterDao{

 @Autowired
 private SessionFactory sessionFactory;
 HelperUtility helperUTIL=new HelperUtility(); 
 
 public void addCountryAddressFilter(CountryAddressFilter countryAddressFilter){
   sessionFactory.getCurrentSession().saveOrUpdate(countryAddressFilter);
 }

 @SuppressWarnings("unchecked")
 public List<CountryAddressFilter> listCountryAddressFilter(){
  return (List<CountryAddressFilter>) sessionFactory.getCurrentSession().createCriteria(CountryAddressFilter.class).list();
 }

 @SuppressWarnings("unchecked")
 public List<CountryAddressFilter> listCountryAddressFilter(String countryId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CountryAddressFilter.class).addOrder(Order.asc("addrFieldIndx"));
	 criteria.add(Restrictions.eq("countryId",countryId));

	 return criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<CountryAddressFilterBean> listCountryAddressFilterBean(String countryId){
	 return helperUTIL.getCountryAddressFilterBeanList(sessionFactory, countryId);
 } 
 
 @SuppressWarnings("unchecked")
 public List<CountryAddressFilterBean> listCountryAddressFilterBean(){
	 return helperUTIL.getCountryAddressFilterBeanList(sessionFactory);
 } 

 public CountryAddressFilter getCountryAddressFilter(int id){
  return (CountryAddressFilter) sessionFactory.getCurrentSession().get(CountryAddressFilter.class, id);
 }

 public void deleteCountryAddressFilter(CountryAddressFilter countryAddressFilter) {
	 sessionFactory.getCurrentSession().createQuery("delete from CountryAddressFilter WHERE ID = "+countryAddressFilter.getId()).executeUpdate();
 }
}