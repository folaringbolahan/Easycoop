package com.sift.loan.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.loan.model.LoanDisbursement;

/**
 * @author Chris Faseun
 *
 */
@Repository("loanDisbursementDao")
public class LoanDisbursementDaoImpl implements LoanDisbursementDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addLoanDisbursement(LoanDisbursement loanDisbursement) {
   sessionFactory.getCurrentSession().saveOrUpdate(loanDisbursement);
 }

 @SuppressWarnings("unchecked")
 public List<LoanDisbursement> listLoanDisbursements(){
  return (List<LoanDisbursement>) sessionFactory.getCurrentSession().createCriteria(LoanDisbursement.class).list();
 }

 public LoanDisbursement getLoanDisbursement(int id){
  return (LoanDisbursement) sessionFactory.getCurrentSession().get(LoanDisbursement.class, id);
 }

 public void deleteLoanDisbursement(LoanDisbursement loanDisbursement) {
  sessionFactory.getCurrentSession().createQuery("DELETE FROM LOAN_DISBURSEMENT WHERE id = "+loanDisbursement.getId()).executeUpdate();
 }
}