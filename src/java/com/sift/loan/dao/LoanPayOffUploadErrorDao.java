package com.sift.loan.dao;

import java.util.List;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.LoanPayOffUploadErrors;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadErrors;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanPayOffUploadErrorDao {
	public void addLoanPayOffUploadError(LoanPayOffUploadErrors item);
	public void addLoanPayOffUploadErrors(List<LoanPayOffUploadErrors> items);
	public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors();
	public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors(String branchId);
	public List<LoanPayOffUploadErrors> listLoanPayOffUploadErrors(String branchId,String batchId);
	public List<LoanPayOffUploadErrors> listPendingLoanPayOffUploadErrors();
	public LoanPayOffUploadErrors getLoanPayOffUploadError(int id);
	public void deleteLoanPayOffUploadError(LoanPayOffUploadErrors item);
}