package com.sift.loan.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.loan.model.LoanRepayment;

/**
 * @author Chris Faseun
 *
 */
@Repository("loanRepaymentDao")
public class LoanRepaymentDaoImpl implements LoanRepaymentDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addLoanRepayment(LoanRepayment loanRepayment) {
   sessionFactory.getCurrentSession().saveOrUpdate(loanRepayment);
 }

 @SuppressWarnings("unchecked")
 public List<LoanRepayment> listLoanRepayments(){
  return (List<LoanRepayment>) sessionFactory.getCurrentSession().createCriteria(LoanRepayment.class).list();
 }

 public LoanRepayment getLoanRepayment(int id){
  return (LoanRepayment) sessionFactory.getCurrentSession().get(LoanRepayment.class, id);
 }

 public void deleteLoanRepayment(LoanRepayment loanRepayment) {
  sessionFactory.getCurrentSession().createQuery("DELETE FROM LOAN_REPAYMENT WHERE id = "+loanRepayment.getId()).executeUpdate();
 }
}