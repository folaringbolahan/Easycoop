package com.sift.loan.dao;

import java.util.List;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.Product;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface BulkUploadDao {
	 public void addBulkUpload(FileUpload fileUpload);
	 public void markBatchForPayment(FileUpload fileUpload);
	 public void enterBatchUpload(FileUpload fileUpload);
	 public void authorizeBatchUpload(FileUpload fileUpload);
	 public void cancelBatchUpload(FileUpload fileUpload);
	 public void rejectBatchUpload(FileUpload fileUpload);
	 public List<FileUpload> listBulkUploads();
	 public List<FileUpload> listBulkUploads(String companyId);
	 public List<FileUpload> listBulkUploads(String companyId,String branchId);
	 public List<FileUpload> listBulkUploadsForAuth(String companyId,String branchId,String processingStatus);
	 public List<FileUpload> listPendingBulkUploads();
	 public List<FileUpload> listPendingBulkUploads(String paymentStatus);
         public List<FileUpload> listPendingBulkUploads(String paymentStatus,String companyId,String branchId);
	 public List<FileUpload> listPendingBulkUploadsByType(String uploadType);
         public List<FileUpload> listPendingBulkUploadsByType(String uploadType,String companyId,String branchId);
	 public List<FileUpload> listPendingBulkUploads(String statusField,String statusValue);
	 public FileUpload getBulkUpload(int id);
	 public void deleteBulkUpload(FileUpload fileUpload);
}