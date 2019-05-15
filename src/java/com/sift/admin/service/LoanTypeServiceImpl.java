package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.LoanType;
import com.sift.admin.dao.LoanTypeDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("loanTypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanTypeServiceImpl implements LoanTypeService {
	 @Autowired
	 private LoanTypeDao loanTypeDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanType(LoanType loanType) {
		 loanTypeDao.addLoanType(loanType);
	 }

	 public List<LoanType> listLoanTypes() {
	  return loanTypeDao.listLoanTypes();
	 }

	 public LoanType getLoanType(int id){
	  return loanTypeDao.getLoanType(id);
	 }

	 public void deleteLoanType(LoanType loanType) {
		 loanTypeDao.deleteLoanType(loanType);
	 }
}