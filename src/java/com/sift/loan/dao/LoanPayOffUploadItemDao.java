package com.sift.loan.dao;

import java.util.List;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.LoanPayOffUploadItems;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadItems;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanPayOffUploadItemDao{
	public void addLoanPayOffUploadItem(LoanPayOffUploadItems item);
	public void addLoanPayOffUploadItems(List<LoanPayOffUploadItems> items);
	public List<LoanPayOffUploadItems> listLoanPayOffUploadItems();
	public List<LoanPayOffUploadItems> listLoanPayOffUploadItems(String companyId);
	public List<LoanPayOffUploadItems> listPendingLoanPayOffUploadItems();
	public List<LoanPayOffUploadItems> listPendingLoanPayOffUploadItems(String batchId);
	public List<LoanPayOffUploadItems> listFailedLoanPayOffUploadItems(String batchId,String branchId);
	public List<LoanPayOffUploadItems> listSuccessLoanPayOffUploadItems(String batchId,String branchId);
	public List<LoanPayOffUploadItems> listFailedLoanPayOffUploadItems(String batchId);
	public List<LoanPayOffUploadItems> listSuccessLoanPayOffUploadItems(String batchId);
	public LoanPayOffUploadItems getLoanPayOffUploadItem(int id);
	public void deleteLoanPayOffUploadItem(LoanPayOffUploadItems item);
}