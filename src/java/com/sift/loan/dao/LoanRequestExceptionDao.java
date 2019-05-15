package com.sift.loan.dao;

import java.util.List;

import com.sift.loan.model.LoanRequestException;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanRequestExceptionDao {
	 public void addLoanRequestException(LoanRequestException loanRequestException);
	 public List<LoanRequestException> listLoanRequestExceptions();
	 public List<LoanRequestException> listLoanRequestExceptionsByCaseId(String loanCaseId);
	 public LoanRequestException getLoanRequestException(int id);
	 public void deleteLoanRequestException(LoanRequestException loanRequestException);
}