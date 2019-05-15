package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.LoanType;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanTypeDao {
	 public void addLoanType(LoanType loanType);
	 public List<LoanType> listLoanTypes();
	 public LoanType getLoanType(int typeid);
	 public void deleteLoanType(LoanType loanType);
}