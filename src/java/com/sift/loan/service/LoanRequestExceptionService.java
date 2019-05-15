package com.sift.loan.service;

import java.util.List;
import com.sift.loan.model.LoanRequestException;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanRequestExceptionService {
	public void addLoanRequestException(LoanRequestException loanRequestException);
	public List<LoanRequestException> listLoanRequestExceptions();
	public List<LoanRequestException> listLoanRequestExceptionsByCaseId(String loanCaseId);
	public LoanRequestException getLoanRequestException(int id);
	public void deleteLoanRequestException(LoanRequestException loanRequestException);
}