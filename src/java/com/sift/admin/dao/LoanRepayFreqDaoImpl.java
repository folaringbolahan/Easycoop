package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.FiscalPeriod;
import com.sift.admin.model.LoanRepayFreq;
import com.sift.loan.model.Product;

/**
 * @author Chris Faseun
 */
@Repository("loanRepayFreqDao")
public class LoanRepayFreqDaoImpl implements LoanRepayFreqDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addLoanRepayFreq(LoanRepayFreq loanRepayFreq){
   sessionFactory.getCurrentSession().saveOrUpdate(loanRepayFreq);
 }

 @SuppressWarnings("unchecked")
 public List<LoanRepayFreq> listLoanRepayFreqs(){
  return (List<LoanRepayFreq>) sessionFactory.getCurrentSession().createCriteria(LoanRepayFreq.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanRepayFreq> listLoanRepayFreqsDistinct(String id){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRepayFreq.class);
	 criteria.add(Restrictions.eq("id",Integer.parseInt(id)));
	 
	 return  (List<LoanRepayFreq>)criteria.list();
 }

 @SuppressWarnings("unchecked")
 public List<LoanRepayFreq> listLoanRepayFreqsDistinct(String name,String value){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRepayFreq.class);
	 criteria.add(Restrictions.eq(name,value));
	 
	 return  (List<LoanRepayFreq>)criteria.list();
 }

 public LoanRepayFreq getLoanRepayFreq(int id){
  return (LoanRepayFreq) sessionFactory.getCurrentSession().get(LoanRepayFreq.class, id);
 }
 
 public LoanRepayFreq getLoanRepayFreqCustomized(String name,String value){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRepayFreq.class);
	 criteria.add(Restrictions.eq(name,value));	 
	 
	 return (LoanRepayFreq)criteria.list().get(0);
 }


 public void deleteLoanRepayFreq(LoanRepayFreq loanRepayFreq) {
  sessionFactory.getCurrentSession().createQuery("delete from LoanRepaymentFreq where id = "+loanRepayFreq.getId()).executeUpdate();
 }
}