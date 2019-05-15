package com.sift.loan.service;

import java.util.List;

import com.sift.loan.model.LoanGuarantor;
import com.sift.loan.model.LoanRequestException;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanGuarantorService {
	public void addLoanGuarantor(LoanGuarantor loanGuarantor);
	public List<LoanGuarantor> listLoanGuarantors();
	public List<LoanGuarantor> listLoanGuarantorsByCaseId(String loanCaseId);
	public LoanGuarantor getLoanGuarantor(int id);
	public void deleteLoanGuarantor(LoanGuarantor loanGuarantor);
}