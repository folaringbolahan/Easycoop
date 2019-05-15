package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.loan.model.BulkLoanRePayment;
import com.sift.loan.dao.BulkLoanRePaymentDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("bulkLoanRePaymentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BulkLoanRePaymentServiceImpl implements BulkLoanRePaymentService {
	 @Autowired
	 private BulkLoanRePaymentDao bulkLoanRePaymentDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addBulkLoanRePayment(BulkLoanRePayment bulkLoanRePayment) {
		 bulkLoanRePaymentDao.addBulkLoanRePayment(bulkLoanRePayment);
	 }

	 public List<BulkLoanRePayment> listBulkLoanRePayments() {
	  return bulkLoanRePaymentDao.listBulkLoanRePayments();
	 }	 


	 public BulkLoanRePayment getBulkLoanRePayment(int id){
	  return bulkLoanRePaymentDao.getBulkLoanRePayment(id);
	 }

	 public void deleteBulkLoanRePayment(BulkLoanRePayment bulkLoanRePayment) {
		 bulkLoanRePaymentDao.deleteBulkLoanRePayment(bulkLoanRePayment);
	 }
}