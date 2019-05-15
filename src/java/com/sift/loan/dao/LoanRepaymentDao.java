package com.sift.loan.dao;

import java.util.List;

import com.sift.loan.model.LoanRepayment;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanRepaymentDao {
	 public void addLoanRepayment(LoanRepayment loanRepayment);
	 public List<LoanRepayment> listLoanRepayments();
	 public LoanRepayment getLoanRepayment(int id);
	 public void deleteLoanRepayment(LoanRepayment loanRepayment);
}