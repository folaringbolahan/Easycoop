package com.sift.loan.bean;

import java.util.ArrayList;
import java.util.List;

import com.sift.loan.model.RepaymentUploadErrors;
import com.sift.loan.model.RepaymentUploadItems;

public class LoanRepayValidationBean {
	List<LoanRepayUploadBean> 		uploadedListAll;
    List<LoanRepayUploadBean> 		uploadedListSuccess;
    List<LoanRepayUploadBean> 		uploadedListFailed;
    List<LoanRepaymentScheduleBean> payableList;
    List<RepaymentUploadItemsBean>  payableBeans;
    List<RepaymentUploadItems>  	payableModels;
    List<RepaymentUploadErrors>  	errorBeans;
    
	public List<LoanRepaymentScheduleBean> getPayableList() {
		return payableList;
	}
	public void setPayableList(List<LoanRepaymentScheduleBean> payableList) {
		this.payableList = payableList;
	}
	public List<LoanRepayUploadBean> getUploadedListAll() {
		return uploadedListAll;
	}
	public void setUploadedListAll(List<LoanRepayUploadBean> uploadedListAll) {
		this.uploadedListAll = uploadedListAll;
	}
	public List<LoanRepayUploadBean> getUploadedListSuccess() {
		return uploadedListSuccess;
	}
	public void setUploadedListSuccess(List<LoanRepayUploadBean> uploadedListSuccess) {
		this.uploadedListSuccess = uploadedListSuccess;
	}
	public List<LoanRepayUploadBean> getUploadedListFailed() {
		return uploadedListFailed;
	}
	public void setUploadedListFailed(List<LoanRepayUploadBean> uploadedListFailed) {
		this.uploadedListFailed = uploadedListFailed;
	}
	public List<RepaymentUploadItemsBean> getPayableBeans() {
		return payableBeans;
	}
	public void setPayableBeans(List<RepaymentUploadItemsBean> payableBeans) {
		this.payableBeans = payableBeans;
	}
	public List<RepaymentUploadItems> getPayableModels() {
		return payableModels;
	}
	public void setPayableModels(List<RepaymentUploadItems> payableModels) {
		this.payableModels = payableModels;
	}
	public List<RepaymentUploadErrors> getErrorBeans() {
		return errorBeans;
	}
	public void setErrorBeans(List<RepaymentUploadErrors> errorBeans) {
		this.errorBeans = errorBeans;
	}       
}