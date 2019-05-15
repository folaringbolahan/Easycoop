package com.sift.loan.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.loan.model.BulkLoanRePayment;

/**
 * @author Chris Faseun
 *
 */
@Repository("bulkLoanRePaymentDao")
public class BulkLoanRePaymentDaoImpl implements BulkLoanRePaymentDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addBulkLoanRePayment(BulkLoanRePayment bulkLoanRePayment) {
   sessionFactory.getCurrentSession().saveOrUpdate(bulkLoanRePayment);
 }

 @SuppressWarnings("unchecked")
 public List<BulkLoanRePayment> listBulkLoanRePayments(){
  return (List<BulkLoanRePayment>) sessionFactory.getCurrentSession().createCriteria(BulkLoanRePayment.class).list();
 }

 public BulkLoanRePayment getBulkLoanRePayment(int id){
  return (BulkLoanRePayment) sessionFactory.getCurrentSession().get(BulkLoanRePayment.class, id);
 }

 public void deleteBulkLoanRePayment(BulkLoanRePayment bulkLoanRePayment) {
  sessionFactory.getCurrentSession().createQuery("DELETE FROM LOAN_REPAYMENT WHERE id = "+bulkLoanRePayment.getId()).executeUpdate();
 }
}