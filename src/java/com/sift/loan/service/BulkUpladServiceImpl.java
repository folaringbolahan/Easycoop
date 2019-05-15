package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sift.loan.model.FileUpload;
import com.sift.loan.dao.BulkUploadDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("bulkUploadService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BulkUpladServiceImpl implements BulkUploadService {
	 @Autowired
	 private BulkUploadDao bulkUploadDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addBulkUpload(FileUpload fileUpload) {
		  bulkUploadDao.addBulkUpload(fileUpload);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void markBatchForPayment(FileUpload fileUpload){
		  bulkUploadDao.markBatchForPayment(fileUpload);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void enterBatchUpload(FileUpload fileUpload){
		 bulkUploadDao.enterBatchUpload(fileUpload);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void authorizeBatchUpload(FileUpload fileUpload){
		 bulkUploadDao.authorizeBatchUpload(fileUpload);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void cancelBatchUpload(FileUpload fileUpload){
		 bulkUploadDao.cancelBatchUpload(fileUpload);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void rejectBatchUpload(FileUpload fileUpload){
		 bulkUploadDao.rejectBatchUpload(fileUpload);
	 }

	 public List<FileUpload> listBulkUploads(){
		  return bulkUploadDao.listBulkUploads();
	 }

	 public List<FileUpload> listBulkUploads(String companyId){
		  return bulkUploadDao.listBulkUploads(companyId);
	 }
	 
	 public List<FileUpload> listBulkUploads(String companyId,String branchId){
		  return bulkUploadDao.listBulkUploads(companyId,branchId);
	 }
	 
	 public List<FileUpload> listBulkUploadsForAuth(String companyId,String branchId,String processingStatus){
		 return bulkUploadDao.listBulkUploadsForAuth(companyId,branchId,processingStatus);
	 }
	 
	 public List<FileUpload> listPendingBulkUploads(){
		  return bulkUploadDao.listPendingBulkUploads();
	 }
	 
	 public List<FileUpload> listPendingBulkUploadsByType(String uploadType){
		 return bulkUploadDao.listPendingBulkUploadsByType(uploadType);
	 }
	 
         public List<FileUpload> listPendingBulkUploadsByType(String uploadType,String companyId,String branchId){
		 return bulkUploadDao.listPendingBulkUploadsByType(uploadType,companyId,branchId);
	 }
         
	 public List<FileUpload> listPendingBulkUploads(String paymentStatus){
		  return bulkUploadDao.listPendingBulkUploads(paymentStatus);
	 }
	 
         public List<FileUpload> listPendingBulkUploads(String paymentStatus,String companyId,String branchId){
		  return bulkUploadDao.listPendingBulkUploads(paymentStatus, companyId, branchId);
	 }
         
	 public List<FileUpload> listPendingBulkUploads(String statusField,String statusValue){
		  return bulkUploadDao.listPendingBulkUploads(statusField,statusValue);
	 }
	 
	 public FileUpload getBulkUpload(int id){
		  return bulkUploadDao.getBulkUpload(id);
	 }

	 public void deleteBulkUpload(FileUpload fileUpload) {
		  bulkUploadDao.deleteBulkUpload(fileUpload);
	 }
}