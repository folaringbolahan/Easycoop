package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.loan.model.LoanPayOff;
import com.sift.loan.dao.LoanPayOffDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("loanPayOffService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanPayOffServiceImpl implements LoanPayOffService {
	 @Autowired
	 private LoanPayOffDao loanPayOffDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanPayOff(LoanPayOff loanPayOff) {
		 loanPayOffDao.addLoanPayOff(loanPayOff);
	 }

	 public List<LoanPayOff> listLoanPayOffs() {
	  return loanPayOffDao.listLoanPayOffs();
	 }	 


	 public LoanPayOff getLoanPayOff(int id){
	  return loanPayOffDao.getLoanPayOff(id);
	 }

	 public void deleteLoanPayOff(LoanPayOff loanPayOff) {
		 loanPayOffDao.deleteLoanPayOff(loanPayOff);
	 }
}