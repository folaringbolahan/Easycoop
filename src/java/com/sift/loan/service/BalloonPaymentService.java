package com.sift.loan.service;

import java.util.List;

import com.sift.loan.model.BalloonPayment;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface BalloonPaymentService {
	public void addBalloonPayment(BalloonPayment balloonPayment);
	public List<BalloonPayment> listBalloonPayments();
	public BalloonPayment getBalloonPayment(int id);
	public void deleteBalloonPayment(BalloonPayment balloonPayment);
}