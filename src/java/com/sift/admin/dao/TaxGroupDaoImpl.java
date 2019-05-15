package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.FiscalPeriod;
import com.sift.admin.model.TaxGroup;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("taxGroupDao")
public class TaxGroupDaoImpl implements TaxGroupDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addTaxGroup(TaxGroup taxGroup) {
   sessionFactory.getCurrentSession().saveOrUpdate(taxGroup);
 }

 @SuppressWarnings("unchecked")
 public List<TaxGroup> listTaxGroups(){
  return (List<TaxGroup>) sessionFactory.getCurrentSession().createCriteria(TaxGroup.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<TaxGroup> listTaxGroupsByCompanyId(Integer companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TaxGroup.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 
	 return criteria.list();
 }	
 
 @SuppressWarnings("unchecked")
 public List<TaxGroup> listTaxGroupsByBranchId(Integer branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TaxGroup.class);
	 criteria.add(Restrictions.eq("branchId",branchId));
	 
	 return criteria.list();
 }	

 public TaxGroup getTaxGroup(int id){
  return (TaxGroup) sessionFactory.getCurrentSession().get(TaxGroup.class, id);
 }

 public void deleteTaxGroup(TaxGroup taxGroup) {
	 sessionFactory.getCurrentSession().createQuery("delete from TaxGroup where id = "+taxGroup.getId()).executeUpdate();
 }
}