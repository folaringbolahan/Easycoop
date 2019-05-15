package com.sift.loan.service;

import java.util.List;

import com.sift.loan.model.LoanRepayment;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanRepaymentService {
	public void addLoanRepayment(LoanRepayment loanRepayment);
	public List<LoanRepayment> listLoanRepayments();
	public LoanRepayment getLoanRepayment(int id);
	public void deleteLoanRepayment(LoanRepayment loanRepayment);
}