package com.sift.loan.bean;

import java.util.ArrayList;
import java.util.List;

import com.sift.loan.model.LoanPayOffUploadErrors;
import com.sift.loan.model.LoanPayOffUploadItems;
import com.sift.loan.model.RepaymentUploadErrors;
import com.sift.loan.model.RepaymentUploadItems;

public class LoanPayOffValidationBean{
	List<LoanPayOffUploadBean> 		uploadedListAll;
    List<LoanPayOffUploadBean> 		uploadedListSuccess;
    List<LoanPayOffUploadBean> 		uploadedListFailed;
    List<LoanPayOffUploadItems>  	payableModels;
    List<LoanPayOffUploadErrors>  	errorBeans;
    
	public List<LoanPayOffUploadBean> getUploadedListAll(){
		return uploadedListAll;
	}
	
	public void setUploadedListAll(List<LoanPayOffUploadBean> uploadedListAll) {
		this.uploadedListAll = uploadedListAll;
	}
	
	public List<LoanPayOffUploadBean> getUploadedListSuccess() {
		return uploadedListSuccess;
	}
	
	public void setUploadedListSuccess(
			List<LoanPayOffUploadBean> uploadedListSuccess) {
		this.uploadedListSuccess = uploadedListSuccess;
	}
	public List<LoanPayOffUploadBean> getUploadedListFailed() {
		return uploadedListFailed;
	}
	public void setUploadedListFailed(List<LoanPayOffUploadBean> uploadedListFailed) {
		this.uploadedListFailed = uploadedListFailed;
	}
	public List<LoanPayOffUploadItems> getPayableModels() {
		return payableModels;
	}
	public void setPayableModels(List<LoanPayOffUploadItems> payableModels) {
		this.payableModels = payableModels;
	}
	public List<LoanPayOffUploadErrors> getErrorBeans() {
		return errorBeans;
	}
	public void setErrorBeans(List<LoanPayOffUploadErrors> errorBeans) {
		this.errorBeans = errorBeans;
	}
}