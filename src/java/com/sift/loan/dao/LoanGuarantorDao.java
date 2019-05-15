package com.sift.loan.dao;

import java.util.List;

import com.sift.loan.model.LoanGuarantor;
import com.sift.loan.model.LoanRequestException;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanGuarantorDao {
	 public void addLoanGuarantor(LoanGuarantor loanGuarantor);
	 public List<LoanGuarantor> listLoanGuarantors();
	 public List<LoanGuarantor> listLoanGuarantorsByCaseId(String loanCaseId);
	 public LoanGuarantor getLoanGuarantor(int typeid);
	 public void deleteLoanGuarantor(LoanGuarantor loanGuarantor);
}