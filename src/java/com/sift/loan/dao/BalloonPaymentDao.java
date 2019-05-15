package com.sift.loan.dao;

import java.util.List;
import com.sift.loan.model.BalloonPayment;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface BalloonPaymentDao {
	 public void addBalloonPayment(BalloonPayment balloonPayment);
	 public List<BalloonPayment> listBalloonPayments();
	 public BalloonPayment getBalloonPayment(int id);
	 public void deleteBalloonPayment(BalloonPayment balloonPayment);
}