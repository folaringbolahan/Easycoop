/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.loan.dao;

import com.sift.loan.bean.LoanGuarantorChangeBean;
import java.util.List;

import com.sift.loan.model.LoanGuarantorChange;
import com.sift.loan.model.LoanRequestException;
import java.util.ArrayList;
/**
 *
 * @author Nelson Akpos
 */
public interface LoanGuarantorChangeDao {
         public void addLoanGuarantorChanged(LoanGuarantorChange loanGuarantorChange);
         public void addLoanGuarantorChanged(ArrayList<LoanGuarantorChange> list);
	 public List<LoanGuarantorChange> listLoanGuarantorsChanged();
         public List<LoanGuarantorChange> listLoanGuarantorsChangedByBranchId(String branchId);
	 public List<LoanGuarantorChange> listLoanGuarantorsChangedByCaseId(String loanCaseId);
	 public LoanGuarantorChange getLoanGuarantorChanged(String typeid);
	 public void deleteLoanGuarantorChanged(LoanGuarantorChange loanGuarantorChange);
     
    
}
