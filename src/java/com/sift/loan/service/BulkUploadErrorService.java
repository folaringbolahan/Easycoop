package com.sift.loan.service;

import java.util.List;

import com.sift.loan.model.FileUpload;
import com.sift.loan.model.LoanPayOffUploadErrors;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadErrors;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface BulkUploadErrorService{
	public void addBulkUploadError(RepaymentUploadErrors item);
	public void addBulkUploadErrors(List<RepaymentUploadErrors> items);
	public void addLoanPayOffUploadErrors(List<LoanPayOffUploadErrors> items);
	public List<RepaymentUploadErrors> listBulkUploadErrors();
	public List<RepaymentUploadErrors> listBulkUploadErrors(String companyId);
	public List<RepaymentUploadErrors> listBulkUploadErrors(String companyId,String batchId);
	public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors(String companyId,String batchId);
	public List<RepaymentUploadErrors> listPendingBulkUploadErrors();
	public RepaymentUploadErrors getBulkUploadError(int id);
	public void deleteBulkUploadError(RepaymentUploadErrors item);
}