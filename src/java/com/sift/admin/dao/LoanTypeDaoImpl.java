package com.sift.admin.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.LoanType;

/**
 * @author Chris Faseun
 *
 */
@Repository("loanTypeDao")
public class LoanTypeDaoImpl implements LoanTypeDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addLoanType(LoanType loanType) {
   sessionFactory.getCurrentSession().saveOrUpdate(loanType);
 }

 @SuppressWarnings("unchecked")
 public List<LoanType> listLoanTypes(){
  return (List<LoanType>) sessionFactory.getCurrentSession().createCriteria(LoanType.class).list();
 }

 public LoanType getLoanType(int id){
  return (LoanType) sessionFactory.getCurrentSession().get(LoanType.class, id);
 }

 public void deleteLoanType(LoanType loanType) {
  sessionFactory.getCurrentSession().createQuery("delete from LoanType where id = "+loanType.getId()).executeUpdate();
 }
}