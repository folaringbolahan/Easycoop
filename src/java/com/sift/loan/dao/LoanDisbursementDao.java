package com.sift.loan.dao;

import java.util.List;
import com.sift.loan.model.LoanDisbursement;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanDisbursementDao {
	 public void addLoanDisbursement(LoanDisbursement loanDisbursement);
	 public List<LoanDisbursement> listLoanDisbursements();
	 public LoanDisbursement getLoanDisbursement(int typeid);
	 public void deleteLoanDisbursement(LoanDisbursement loanDisbursement);
}