package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.loan.model.BalloonPayment;
import com.sift.loan.dao.BalloonPaymentDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("balloonPaymentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BalloonPaymentServiceImpl implements BalloonPaymentService {
	 @Autowired
	 private BalloonPaymentDao balloonPaymentDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addBalloonPayment(BalloonPayment balloonPayment) {
		 balloonPaymentDao.addBalloonPayment(balloonPayment);
	 }

	 public List<BalloonPayment> listBalloonPayments() {
	  return balloonPaymentDao.listBalloonPayments();
	 }	 


	 public BalloonPayment getBalloonPayment(int id){
	  return balloonPaymentDao.getBalloonPayment(id);
	 }

	 public void deleteBalloonPayment(BalloonPayment balloonPayment) {
		 balloonPaymentDao.deleteBalloonPayment(balloonPayment);
	 }
}