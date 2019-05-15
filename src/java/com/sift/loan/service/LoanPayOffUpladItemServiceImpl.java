package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.LoanPayOffUploadItems;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadItems;
//import com.sift.loan.dao.LoanPayOffUploadDao;
import com.sift.loan.dao.LoanPayOffUploadItemDao;
import com.sift.loan.dao.ProductDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("loanPayOffUploadItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanPayOffUpladItemServiceImpl implements LoanPayOffUploadItemService {
	 @Autowired
	 private LoanPayOffUploadItemDao loanPayOffUploadItemDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanPayOffUploadItem(LoanPayOffUploadItems item) {
		 loanPayOffUploadItemDao.addLoanPayOffUploadItem(item);
	 }

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanPayOffUploadItems(List<LoanPayOffUploadItems> items){
		 loanPayOffUploadItemDao.addLoanPayOffUploadItems(items);
	 }

	 public List<LoanPayOffUploadItems> listLoanPayOffUploadItems() {
	  return loanPayOffUploadItemDao.listLoanPayOffUploadItems();
	 }

	 public List<LoanPayOffUploadItems> listLoanPayOffUploadItems(String companyId) {
		  return loanPayOffUploadItemDao.listLoanPayOffUploadItems(companyId);
	 }
	 
	 public List<LoanPayOffUploadItems> listPendingLoanPayOffUploadItems(){
		  return loanPayOffUploadItemDao.listPendingLoanPayOffUploadItems();
	 }
	 
	 public List<LoanPayOffUploadItems> listPendingLoanPayOffUploadItems(String batchId){
		  return loanPayOffUploadItemDao.listPendingLoanPayOffUploadItems(batchId);
	 }

	 public List<LoanPayOffUploadItems> listFailedLoanPayOffUploadItems(String batchId,String branchId){
		  return loanPayOffUploadItemDao.listFailedLoanPayOffUploadItems(batchId,branchId);
	 }
	 
	 public List<LoanPayOffUploadItems> listSuccessLoanPayOffUploadItems(String batchId,String branchId){
		  return loanPayOffUploadItemDao.listSuccessLoanPayOffUploadItems(batchId,branchId);
	 }
	 
	 public List<LoanPayOffUploadItems> listFailedLoanPayOffUploadItems(String batchId){
		  return loanPayOffUploadItemDao.listFailedLoanPayOffUploadItems(batchId);
	 }
	 
	 public List<LoanPayOffUploadItems> listSuccessLoanPayOffUploadItems(String batchId){
		  return loanPayOffUploadItemDao.listSuccessLoanPayOffUploadItems(batchId);
	 }
	 
	 public LoanPayOffUploadItems getLoanPayOffUploadItem(int id){
	  return loanPayOffUploadItemDao.getLoanPayOffUploadItem(id);
	 }

	 public void deleteLoanPayOffUploadItem(LoanPayOffUploadItems item) {
		 loanPayOffUploadItemDao.deleteLoanPayOffUploadItem(item);
	 }
}