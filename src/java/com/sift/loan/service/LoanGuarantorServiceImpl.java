package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.loan.model.LoanGuarantor;
import com.sift.loan.model.LoanRequestException;
import com.sift.loan.dao.LoanGuarantorDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("loanGuarantorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanGuarantorServiceImpl implements LoanGuarantorService {
	 @Autowired
	 private LoanGuarantorDao loanGuarantorDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanGuarantor(LoanGuarantor loanGuarantor) {
		 loanGuarantorDao.addLoanGuarantor(loanGuarantor);
	 }

	 public List<LoanGuarantor> listLoanGuarantors() {
	  return loanGuarantorDao.listLoanGuarantors();
	 }
	 
	 public List<LoanGuarantor> listLoanGuarantorsByCaseId(String loanCaseId){
		 return loanGuarantorDao.listLoanGuarantorsByCaseId(loanCaseId);
	 }

	 public LoanGuarantor getLoanGuarantor(int id){
	  return loanGuarantorDao.getLoanGuarantor(id);
	 }

	 public void deleteLoanGuarantor(LoanGuarantor loanGuarantor) {
		 loanGuarantorDao.deleteLoanGuarantor(loanGuarantor);
	 }
}