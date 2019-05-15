package com.sift.loan.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.loan.model.BalloonPayment;

/**
 * @author Chris Faseun
 *
 */
@Repository("balloonPaymentDao")
public class BalloonPaymentDaoImpl implements BalloonPaymentDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addBalloonPayment(BalloonPayment balloonPayment) {
   sessionFactory.getCurrentSession().saveOrUpdate(balloonPayment);
 }

 @SuppressWarnings("unchecked")
 public List<BalloonPayment> listBalloonPayments(){
  return (List<BalloonPayment>) sessionFactory.getCurrentSession().createCriteria(BalloonPayment.class).list();
 }

 public BalloonPayment getBalloonPayment(int id){
  return (BalloonPayment) sessionFactory.getCurrentSession().get(BalloonPayment.class, id);
 }

 public void deleteBalloonPayment(BalloonPayment balloonPayment) {
  sessionFactory.getCurrentSession().createQuery("DELETE FROM LOAN_REPAYMENT WHERE id = "+balloonPayment.getId()).executeUpdate();
 }
}