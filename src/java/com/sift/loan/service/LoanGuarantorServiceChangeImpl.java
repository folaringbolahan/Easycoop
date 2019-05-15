/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.loan.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.loan.model.LoanGuarantorChange;
import com.sift.loan.model.LoanRequestException;
import com.sift.loan.dao.LoanGuarantorChangeDao;
import java.util.ArrayList;
/**
 *
 * @author Nelson Akpos
 */
@Service("loanGuarantorServiceChange")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanGuarantorServiceChangeImpl implements LoanGuarantorServiceChange {
    @Autowired
	 private LoanGuarantorChangeDao loanGuarantorChangeDao;
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanGuarantorChanged(LoanGuarantorChange loanGuarantorChange) {
		 loanGuarantorChangeDao.addLoanGuarantorChanged(loanGuarantorChange);
	 }
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanGuarantorChanged(ArrayList<LoanGuarantorChange> list){
		 loanGuarantorChangeDao.addLoanGuarantorChanged(list);
	 }

	 public List<LoanGuarantorChange> listLoanGuarantorsChanged() {
	  return loanGuarantorChangeDao.listLoanGuarantorsChanged();
	 }
	 
	 public List<LoanGuarantorChange> listLoanGuarantorsChangedByCaseId(String loanCaseId){
		 return loanGuarantorChangeDao.listLoanGuarantorsChangedByCaseId(loanCaseId);
	 }

          public List<LoanGuarantorChange> listLoanGuarantorsChangedByBranchId(String branchId){
		 return loanGuarantorChangeDao.listLoanGuarantorsChangedByBranchId(branchId);
	 }
	 public LoanGuarantorChange getLoanGuarantorChanged(String loanCaseId){
	  return loanGuarantorChangeDao.getLoanGuarantorChanged(loanCaseId);
	 }

	 public void deleteLoanGuarantorChanged(LoanGuarantorChange loanGuarantorChange) {
		 loanGuarantorChangeDao.deleteLoanGuarantorChanged(loanGuarantorChange);
	 }
        public List<LoanGuarantorChange> listAllLoanGuarantorsChangedByCaseId(String loanCaseId){
         return loanGuarantorChangeDao.listLoanGuarantorsChangedByCaseId(loanCaseId);
        }

        
}
