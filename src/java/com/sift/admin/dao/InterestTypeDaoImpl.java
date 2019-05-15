package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.FiscalPeriod;
import com.sift.admin.model.InterestType;

/**
 * @author Chris Faseun
 *
 */
@Repository("interestTypeDao")
public class InterestTypeDaoImpl implements InterestTypeDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addInterestType(InterestType interestType) {
   sessionFactory.getCurrentSession().saveOrUpdate(interestType);
 }

 @SuppressWarnings("unchecked")
 public List<InterestType> listInterestTypes(){
  return (List<InterestType>) sessionFactory.getCurrentSession().createCriteria(InterestType.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<InterestType> listInterestTypes(String typeCode){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InterestType.class);
	 criteria.add(Restrictions.eq("typeCode",typeCode));
	 
	 return (List<InterestType>) criteria.list();
 }

 public InterestType getInterestType(int id){
  return (InterestType) sessionFactory.getCurrentSession().get(InterestType.class, id);
 }

 public void deleteInterestType(InterestType interestType) {
  sessionFactory.getCurrentSession().createQuery("delete from InterestType where id = "+interestType.getId()).executeUpdate();
 }
}