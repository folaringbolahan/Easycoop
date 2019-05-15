package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.LoanRepayFreq;
import com.sift.admin.dao.LoanRepayFreqDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("loanRepayFreqService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanRepayFreqServiceImpl implements LoanRepayFreqService {
	 @Autowired
	 private LoanRepayFreqDao loanRepayFreqDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanRepayFreq(LoanRepayFreq loanRepayFreq) {
		 loanRepayFreqDao.addLoanRepayFreq(loanRepayFreq);
	 }

	 public List<LoanRepayFreq> listLoanRepayFreqs(){
	  return loanRepayFreqDao.listLoanRepayFreqs();
	 }
	 
	 public List<LoanRepayFreq> listLoanRepayFreqsDistinct(String id){
	  return loanRepayFreqDao.listLoanRepayFreqsDistinct(id);
	 }

	 public List<LoanRepayFreq> listLoanRepayFreqsDistinct(String name,String value){
		  return loanRepayFreqDao.listLoanRepayFreqsDistinct(name,value);
	 }

	 public LoanRepayFreq getLoanRepayFreq(int id){
	  return loanRepayFreqDao.getLoanRepayFreq(id);
	 }

	 public LoanRepayFreq getLoanRepayFreqCustomized(String name,String value){
		  return loanRepayFreqDao.getLoanRepayFreqCustomized(name,value);
	 }

	 public void deleteLoanRepayFreq(LoanRepayFreq loanRepayFreq) {
		 loanRepayFreqDao.deleteLoanRepayFreq(loanRepayFreq);
	 }
}