package com.sift.loan.service;

import java.util.List;

import com.sift.loan.model.BulkLoanRePayment;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface BulkLoanRePaymentService {
	public void addBulkLoanRePayment(BulkLoanRePayment bulkLoanRePayment);
	public List<BulkLoanRePayment> listBulkLoanRePayments();
	public BulkLoanRePayment getBulkLoanRePayment(int id);
	public void deleteBulkLoanRePayment(BulkLoanRePayment bulkLoanRePayment);
}