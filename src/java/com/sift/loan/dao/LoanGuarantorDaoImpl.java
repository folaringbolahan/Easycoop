package com.sift.loan.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.loan.model.LoanGuarantor;
import com.sift.loan.model.LoanRequestException;

/**
 * @author Chris Faseun
 *
 */
@Repository("loanGuarantorDao")
public class LoanGuarantorDaoImpl implements LoanGuarantorDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addLoanGuarantor(LoanGuarantor loanGuarantor) {
   sessionFactory.getCurrentSession().saveOrUpdate(loanGuarantor);
 }

 @SuppressWarnings("unchecked")
 public List<LoanGuarantor> listLoanGuarantors(){
  return (List<LoanGuarantor>) sessionFactory.getCurrentSession().createCriteria(LoanGuarantor.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanGuarantor> listLoanGuarantorsByCaseId(String loanCaseId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanGuarantor.class);
	 criteria.add(Restrictions.eq("loanCaseId",loanCaseId));	
	 
     return (List<LoanGuarantor>) criteria.list();
 } 

 public LoanGuarantor getLoanGuarantor(int id){
  return (LoanGuarantor) sessionFactory.getCurrentSession().get(LoanGuarantor.class, id);
 }

 public void deleteLoanGuarantor(LoanGuarantor loanGuarantor) {
  sessionFactory.getCurrentSession().createQuery("delete from loanGuarantor where id = "+loanGuarantor.getId()).executeUpdate();
 }
}