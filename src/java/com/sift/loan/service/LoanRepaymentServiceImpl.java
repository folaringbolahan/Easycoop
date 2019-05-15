package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.loan.model.LoanRepayment;
import com.sift.loan.dao.LoanRepaymentDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("loanRepaymentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanRepaymentServiceImpl implements LoanRepaymentService {
	 @Autowired
	 private LoanRepaymentDao loanRepaymentDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanRepayment(LoanRepayment loanRepayment) {
		 loanRepaymentDao.addLoanRepayment(loanRepayment);
	 }

	 public List<LoanRepayment> listLoanRepayments() {
	  return loanRepaymentDao.listLoanRepayments();
	 }	 


	 public LoanRepayment getLoanRepayment(int id){
	  return loanRepaymentDao.getLoanRepayment(id);
	 }

	 public void deleteLoanRepayment(LoanRepayment loanRepayment) {
		 loanRepaymentDao.deleteLoanRepayment(loanRepayment);
	 }
}