package com.sift.loan.service;

import java.util.List;

import com.sift.loan.model.LoanDisbursement;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanDisbursementService {
	public void addLoanDisbursement(LoanDisbursement loanDisbursement);
	public List<LoanDisbursement> listLoanDisbursements();
	public LoanDisbursement getLoanDisbursement(int id);
	public void deleteLoanDisbursement(LoanDisbursement loanDisbursement);
}