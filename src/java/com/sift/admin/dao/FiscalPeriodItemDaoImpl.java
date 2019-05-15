package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.bean.FiscalPeriodItemBean;
import com.sift.admin.model.FiscalPeriodItem;
import com.sift.admin.model.User;
import com.sift.loan.utility.HelperUtility;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("fiscalPeriodItemDao")
public class FiscalPeriodItemDaoImpl implements FiscalPeriodItemDao{

 @Autowired
 private SessionFactory sessionFactory;

 HelperUtility helperUTIL=new HelperUtility(); 

 public void addFiscalPeriodItem(FiscalPeriodItem fiscalPeriodItem) {
   sessionFactory.getCurrentSession().saveOrUpdate(fiscalPeriodItem);
 }

 @SuppressWarnings("unchecked")
 public List<FiscalPeriodItem> listFiscalPeriodItem(){
  return (List<FiscalPeriodItem>) sessionFactory.getCurrentSession().createCriteria(FiscalPeriodItem.class).list();
 }

 @SuppressWarnings("unchecked")
 public List<FiscalPeriodItem> listFiscalPeriodItemByYear(int Year){
  return (List<FiscalPeriodItem>) sessionFactory.getCurrentSession().createCriteria(FiscalPeriodItem.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<FiscalPeriodItemBean> listFiscalPeriodItemByYear(String branchId,int Year){
	 return helperUTIL.getFiscalPeriodItemBeanList(sessionFactory, branchId, Year);
 }
 
 public FiscalPeriodItem getFiscalPeriodItem(int id){
  return (FiscalPeriodItem) sessionFactory.getCurrentSession().get(FiscalPeriodItem.class, id);
 }

 public void deleteFiscalPeriodItem(FiscalPeriodItem fiscalPeriodItem) {
	 sessionFactory.getCurrentSession().createQuery("delete from FiscalPeriodItem where id = "+fiscalPeriodItem.getId()).executeUpdate();
 }
}