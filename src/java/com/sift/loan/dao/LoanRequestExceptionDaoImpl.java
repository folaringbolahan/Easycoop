package com.sift.loan.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.LoanRequestException;

/**
 * @author Chris Faseun
 *
 */
@Repository("loanRequestExceptionDao")
public class LoanRequestExceptionDaoImpl implements LoanRequestExceptionDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addLoanRequestException(LoanRequestException loanRequestException) {
    sessionFactory.getCurrentSession().saveOrUpdate(loanRequestException);
 }

 @SuppressWarnings("unchecked")
 public List<LoanRequestException> listLoanRequestExceptions(){
    return (List<LoanRequestException>) sessionFactory.getCurrentSession().createCriteria(LoanRequestException.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanRequestException> listLoanRequestExceptionsByCaseId(String loanCaseId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequestException.class);
	 criteria.add(Restrictions.eq("loanCaseId",loanCaseId));	
	 
     return (List<LoanRequestException>) criteria.list();
 }
  
 public LoanRequestException getLoanRequestException(int id){
     return (LoanRequestException) sessionFactory.getCurrentSession().get(LoanRequestException.class, id);
 }

 public void deleteLoanRequestException(LoanRequestException loanRequestException) {
     sessionFactory.getCurrentSession().createQuery("delete from LoanRequestException WHERE id = "+loanRequestException.getId()).executeUpdate();
 }
}