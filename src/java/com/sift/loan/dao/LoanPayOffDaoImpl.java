package com.sift.loan.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.loan.model.LoanPayOff;

/**
 * @author Chris Faseun
 *
 */
@Repository("loanPayOffDao")
public class LoanPayOffDaoImpl implements LoanPayOffDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addLoanPayOff(LoanPayOff loanPayOff) {
   sessionFactory.getCurrentSession().saveOrUpdate(loanPayOff);
 }

 @SuppressWarnings("unchecked")
 public List<LoanPayOff> listLoanPayOffs(){
  return (List<LoanPayOff>) sessionFactory.getCurrentSession().createCriteria(LoanPayOff.class).list();
 }

 public LoanPayOff getLoanPayOff(int id){
  return (LoanPayOff) sessionFactory.getCurrentSession().get(LoanPayOff.class, id);
 }

 public void deleteLoanPayOff(LoanPayOff loanPayOff) {
  sessionFactory.getCurrentSession().createQuery("delete from LoanPayOff where id = "+loanPayOff.getId()).executeUpdate();
 }
}