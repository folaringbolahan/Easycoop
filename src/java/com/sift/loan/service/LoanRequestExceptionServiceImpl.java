package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sift.loan.model.LoanRequestException;
import com.sift.loan.dao.LoanRequestExceptionDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("loanRequestExceptionService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanRequestExceptionServiceImpl implements LoanRequestExceptionService{
	 @Autowired
	 private LoanRequestExceptionDao loanRequestExceptionDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanRequestException(LoanRequestException loanRequestException){
		 loanRequestExceptionDao.addLoanRequestException(loanRequestException);
	 }

	 public List<LoanRequestException> listLoanRequestExceptions(){
	  return loanRequestExceptionDao.listLoanRequestExceptions();
	 }

	 public List<LoanRequestException> listLoanRequestExceptionsByCaseId(String loanCaseId){
		  return loanRequestExceptionDao.listLoanRequestExceptionsByCaseId(loanCaseId);
	 }
	 

	 public LoanRequestException getLoanRequestException(int id){
	  return loanRequestExceptionDao.getLoanRequestException(id);
	 }

	 public void deleteLoanRequestException(LoanRequestException loanRequestException){
		 loanRequestExceptionDao.deleteLoanRequestException(loanRequestException);
	 }
}