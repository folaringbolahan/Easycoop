package com.sift.loan.dao;

import java.util.List;
import com.sift.loan.model.LoanPayOff;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanPayOffDao {
	 public void addLoanPayOff(LoanPayOff loanPayOff);
	 public List<LoanPayOff> listLoanPayOffs();
	 public LoanPayOff getLoanPayOff(int id);
	 public void deleteLoanPayOff(LoanPayOff loanPayOff);
}