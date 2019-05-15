/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.loan.service;
import com.sift.loan.bean.LoanGuarantorChangeBean;
import com.sift.loan.model.LoanGuarantor;
import java.util.List;

import com.sift.loan.model.LoanGuarantorChange;
import com.sift.loan.model.LoanRequestException;
import java.util.ArrayList;
/**
 *
 * @author Nelson Akpos
 */
public interface LoanGuarantorServiceChange {
          public void addLoanGuarantorChanged(LoanGuarantorChange loanGuarantorChange);
        public void addLoanGuarantorChanged(ArrayList<LoanGuarantorChange> list);
	public List<LoanGuarantorChange> listLoanGuarantorsChanged();
	public List<LoanGuarantorChange> listLoanGuarantorsChangedByCaseId(String loanCaseId);
        public List<LoanGuarantorChange> listLoanGuarantorsChangedByBranchId(String branchId);
	public LoanGuarantorChange getLoanGuarantorChanged(String loanCaseId);
	public void deleteLoanGuarantorChanged(LoanGuarantorChange loanGuarantorChange);
        public List<LoanGuarantorChange> listAllLoanGuarantorsChangedByCaseId(String loanCaseId);

        
}
