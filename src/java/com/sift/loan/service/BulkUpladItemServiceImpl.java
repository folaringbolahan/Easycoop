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
import com.sift.loan.dao.BulkUploadDao;
import com.sift.loan.dao.BulkUploadItemDao;
import com.sift.loan.dao.ProductDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("bulkUploadItemService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BulkUpladItemServiceImpl implements BulkUploadItemService {
	 @Autowired
	 private BulkUploadItemDao bulkUploadItemDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addBulkUploadItem(RepaymentUploadItems item) {
		 bulkUploadItemDao.addBulkUploadItem(item);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addBulkUploadItems(List<RepaymentUploadItems> items){
		 bulkUploadItemDao.addBulkUploadItems(items);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanPayOffUploadItems(List<LoanPayOffUploadItems> items){
		 bulkUploadItemDao.addLoanPayOffUploadItems(items);
	 }

	 public List<RepaymentUploadItems> listBulkUploadItems() {
	  return bulkUploadItemDao.listBulkUploadItems();
	 }

	 public List<RepaymentUploadItems> listBulkUploadItems(String companyId) {
		  return bulkUploadItemDao.listBulkUploadItems(companyId);
	 }
	 
	 public List<RepaymentUploadItems> listPendingBulkUploadItems(){
		  return bulkUploadItemDao.listPendingBulkUploadItems();
	 }
	 
	 public List<RepaymentUploadItems> listPendingBulkUploadItems(String batchId){
		  return bulkUploadItemDao.listPendingBulkUploadItems(batchId);
	 }

	 public List<RepaymentUploadItems> listFailedBulkUploadItems(String batchId,String branchId){
		  return bulkUploadItemDao.listFailedBulkUploadItems(batchId,branchId);
	 }
	 
	 public List<RepaymentUploadItems> listSuccessBulkUploadItems(String batchId,String branchId){
		  return bulkUploadItemDao.listSuccessBulkUploadItems(batchId,branchId);
	 }

	 public List<RepaymentUploadItems> listFailedBulkUploadItems(String batchId){
		  return bulkUploadItemDao.listFailedBulkUploadItems(batchId);
	 }
	 
	 public List<RepaymentUploadItems> listSuccessBulkUploadItems(String batchId){
		  return bulkUploadItemDao.listSuccessBulkUploadItems(batchId);
	 }
	 public RepaymentUploadItems getBulkUploadItem(int id){
	  return bulkUploadItemDao.getBulkUploadItem(id);
	 }

	 public void deleteBulkUploadItem(RepaymentUploadItems item) {
		 bulkUploadItemDao.deleteBulkUploadItem(item);
	 }
}