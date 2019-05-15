package com.sift.loan.service;

import java.util.List;

import com.sift.loan.model.FileUpload;
import com.sift.loan.model.LoanPayOffUploadItems;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadItems;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface BulkUploadItemService{
	public void addBulkUploadItem(RepaymentUploadItems item);
	public void addBulkUploadItems(List<RepaymentUploadItems> items);
	public void addLoanPayOffUploadItems(List<LoanPayOffUploadItems> items);
	public List<RepaymentUploadItems> listBulkUploadItems();
	public List<RepaymentUploadItems> listBulkUploadItems(String companyId);
	public List<RepaymentUploadItems> listPendingBulkUploadItems();
	public List<RepaymentUploadItems> listPendingBulkUploadItems(String batchId);
	public List<RepaymentUploadItems> listFailedBulkUploadItems(String batchId,String branchId);
	public List<RepaymentUploadItems> listSuccessBulkUploadItems(String batchId,String branchId);
	public List<RepaymentUploadItems> listFailedBulkUploadItems(String batchId);
	public List<RepaymentUploadItems> listSuccessBulkUploadItems(String batchId);
	public RepaymentUploadItems getBulkUploadItem(int id);
	public void deleteBulkUploadItem(RepaymentUploadItems item);
}