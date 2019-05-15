package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.loan.model.LoanDisbursement;
import com.sift.loan.dao.LoanDisbursementDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("loanDisbursementService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanDisbursementServiceImpl implements LoanDisbursementService {
	 @Autowired
	 private LoanDisbursementDao loanDisbursementDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanDisbursement(LoanDisbursement loanDisbursement) {
		 loanDisbursementDao.addLoanDisbursement(loanDisbursement);
	 }

	 public List<LoanDisbursement> listLoanDisbursements() {
	  return loanDisbursementDao.listLoanDisbursements();
	 }

	 public LoanDisbursement getLoanDisbursement(int id){
	  return loanDisbursementDao.getLoanDisbursement(id);
	 }

	 public void deleteLoanDisbursement(LoanDisbursement loanDisbursement) {
		 loanDisbursementDao.deleteLoanDisbursement(loanDisbursement);
	 }
}