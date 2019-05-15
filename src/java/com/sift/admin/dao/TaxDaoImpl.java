package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.Branch;
import com.sift.admin.model.Tax;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("taxDao")
@Transactional
public class TaxDaoImpl implements TaxDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addTax(Tax tax){
     sessionFactory.getCurrentSession().saveOrUpdate(tax);
 }

 @SuppressWarnings("unchecked")
 public List<Tax> listTax(){
     return (List<Tax>) sessionFactory.getCurrentSession().createCriteria(Tax.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<Tax> listTaxByBranch(Integer branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tax.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 
	 return  (List<Tax>)criteria.list();
 }

 public Tax getTax(int id){
     return (Tax) sessionFactory.getCurrentSession().get(Tax.class, id);
 }

 public void deleteTax(Tax tax) {
	 sessionFactory.getCurrentSession().createQuery("delete from Tax where id= "+tax.getId()).executeUpdate();
 }
}