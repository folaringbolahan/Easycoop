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
import com.sift.loan.dao.BulkUploadDao;
import com.sift.loan.dao.BulkUploadErrorDao;
import com.sift.loan.dao.ProductDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("bulkUploadErrorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BulkUploadErrorServiceImpl implements BulkUploadErrorService {
	 @Autowired
	 private BulkUploadErrorDao bulkUploadErrorDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addBulkUploadError(RepaymentUploadErrors item) {
		 bulkUploadErrorDao.addBulkUploadError(item);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addBulkUploadErrors(List<RepaymentUploadErrors> items){
		 bulkUploadErrorDao.addBulkUploadErrors(items);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanPayOffUploadErrors(List<LoanPayOffUploadErrors> items){
		 bulkUploadErrorDao.addLoanPayOffUploadErrors(items);
	 }

	 public List<RepaymentUploadErrors> listBulkUploadErrors() {
	  return bulkUploadErrorDao.listBulkUploadErrors();
	 }

	 public List<RepaymentUploadErrors> listBulkUploadErrors(String companyId) {
		  return bulkUploadErrorDao.listBulkUploadErrors(companyId);
	 }
	 
	 public List<RepaymentUploadErrors> listBulkUploadErrors(String companyId,String batchId){
		 return bulkUploadErrorDao.listBulkUploadErrors(companyId,batchId);
	 }
	 
	 public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors(String companyId,String batchId){
		 return bulkUploadErrorDao.listLoanPayOffUploadErrors(companyId,batchId);
	 }
	 
	 public List<RepaymentUploadErrors> listPendingBulkUploadErrors(){
		  return bulkUploadErrorDao.listPendingBulkUploadErrors();
	 }
	 
	 public RepaymentUploadErrors getBulkUploadError(int id){
	  return bulkUploadErrorDao.getBulkUploadError(id);
	 }

	 public void deleteBulkUploadError(RepaymentUploadErrors item) {
		 bulkUploadErrorDao.deleteBulkUploadError(item);
	 }
}