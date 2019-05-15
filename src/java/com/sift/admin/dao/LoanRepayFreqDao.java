package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.LoanRepayFreq;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanRepayFreqDao {
	 public void addLoanRepayFreq(LoanRepayFreq loanRepayFreq);
	 public List<LoanRepayFreq> listLoanRepayFreqs();
	 public List<LoanRepayFreq> listLoanRepayFreqsDistinct(String productId);
	 public List<LoanRepayFreq> listLoanRepayFreqsDistinct(String name,String value);
	 public LoanRepayFreq getLoanRepayFreq(int id);
	 public LoanRepayFreq getLoanRepayFreqCustomized(String name,String value);
	 public void deleteLoanRepayFreq(LoanRepayFreq loanRepayFreq);
}