package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.LoanPayOffUploadErrors;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadErrors;
//import com.sift.loan.dao.LoanPayOffUploadDao;
import com.sift.loan.dao.LoanPayOffUploadErrorDao;
import com.sift.loan.dao.ProductDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("loanPayOffUploadErrorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanPayOffUploadErrorServiceImpl implements LoanPayOffUploadErrorService {
	 @Autowired
	 private LoanPayOffUploadErrorDao loanPayOffUploadErrorDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanPayOffUploadError(LoanPayOffUploadErrors item) {
		 loanPayOffUploadErrorDao.addLoanPayOffUploadError(item);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanPayOffUploadErrors(List<LoanPayOffUploadErrors> items){
		 loanPayOffUploadErrorDao.addLoanPayOffUploadErrors(items);
	 }

	 public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors() {
	      return loanPayOffUploadErrorDao.listLoanPayOffUploadErrors();
	 }

	 public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors(String batchId){
		 return loanPayOffUploadErrorDao.listLoanPayOffUploadErrors(batchId);
	 }
	 
	 public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors(String branchId,String batchId){
		 return loanPayOffUploadErrorDao.listLoanPayOffUploadErrors(branchId,batchId);
	 }
	 
	 public List<LoanPayOffUploadErrors> listPendingLoanPayOffUploadErrors(){
		  return loanPayOffUploadErrorDao.listPendingLoanPayOffUploadErrors();
	 }
	 
	 public LoanPayOffUploadErrors getLoanPayOffUploadError(int id){
	      return loanPayOffUploadErrorDao.getLoanPayOffUploadError(id);
	 }

	 public void deleteLoanPayOffUploadError(LoanPayOffUploadErrors item) {
		 loanPayOffUploadErrorDao.deleteLoanPayOffUploadError(item);
	 }
}