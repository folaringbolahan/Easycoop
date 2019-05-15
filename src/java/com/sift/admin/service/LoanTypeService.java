package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.LoanType;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanTypeService { 
	public void addLoanType(LoanType loanType);
	public List<LoanType> listLoanTypes(); 
	public LoanType getLoanType(int id); 
	public void deleteLoanType(LoanType loanType);
}