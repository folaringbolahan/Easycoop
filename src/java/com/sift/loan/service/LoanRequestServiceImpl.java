package com.sift.loan.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.loan.model.BulkLoanRePayment;
import com.sift.loan.model.LoanGuarantor;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.LoanRequestException;
import com.sift.loan.dao.LoanRequestDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("loanRequestService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanRequestServiceImpl implements LoanRequestService{
	 @Autowired
	 private LoanRequestDao loanRequestDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanRequest(LoanRequest loanRequest){
		 loanRequestDao.addLoanRequest(loanRequest);
	 }

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void rejectLoanRequest(LoanRequest loanRequest){
		 loanRequestDao.addLoanRequest(loanRequest);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void approveLoanRequest(LoanRequest loanRequest,boolean overrideException){
		 loanRequestDao.approveLoanRequest(loanRequest,overrideException);
	 }

	 public List<LoanRequest> listLoanRequests(){
	     return loanRequestDao.listLoanRequests();
	 }
	 
	 public List<LoanRequest> listLoanRequestsByCompanyBranch(String companyId,String branchId){
		 return loanRequestDao.listLoanRequestsByCompanyBranch(companyId,branchId);
	 }

	 public List<LoanRequest> listLoanRequestsByMember(String memberId,boolean currentOnly){
	     return loanRequestDao.listLoanRequestsByMember(memberId,currentOnly);
	 }
	 
	 public List<LoanRequest> listLoanRequestsByMember(String memberId,boolean currentOnly,String companyId,String branchId){
	     return loanRequestDao.listLoanRequestsByMember(memberId,currentOnly,companyId,branchId);
	 }
	 
	 public int getLoanRequestsCountByMember(String memberId){
	     return loanRequestDao.getLoanRequestsCountByMember(memberId);
	 }

	 
	 public List<LoanRequest> listPendingLoanRequestByCompanyId(String companyId){
	     return loanRequestDao.listPendingLoanRequestByCompanyId(companyId);
	 }	 
	 
	 public List<LoanRequest> listPendingLoanRequestByCompanyId(String companyId,String loanStatus){
		 return loanRequestDao.listPendingLoanRequestByCompanyId(companyId,loanStatus);
	 }

	 public List<LoanRequest> listPendingLoanRequestByBranchId(String branchId,String loanStatus){
		 return loanRequestDao.listPendingLoanRequestByBranchId(branchId,loanStatus);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addLoanRequest(LoanRequest loanRequest,ArrayList<LoanRequestException> lreList,ArrayList<LoanGuarantor> lgList){
		 return loanRequestDao.addLoanRequest(loanRequest,lreList,lgList);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean approveLoanRequest(LoanRequest loanRequest,ArrayList<LoanRepaymentSchedule> lrsList){
		 return loanRequestDao.addLoanRequest(loanRequest,lrsList);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean doDisburseLoan(LoanRequest loanRequest){
		 return loanRequestDao.doDisburseLoan(loanRequest);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean doUpdateLoanBalance(LoanRequest loanRequest,LoanRepaymentSchedule schedule,boolean reset){
		 return loanRequestDao.doUpdateLoanBalance(loanRequest,schedule,reset);	 
	 }

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean doUpdateLoanBalance(LoanRequest loanRequest,BulkLoanRePayment bulkLoanRePayment,boolean reset){
		 return loanRequestDao.doUpdateLoanBalance(loanRequest,bulkLoanRePayment,reset);	 
	 }
	 
	 public LoanRequest getLoanRequest(int id){
	     return loanRequestDao.getLoanRequest(id);
	 }
	 
	 public LoanRequest getLoanRequestByCaseId(String caseid){
		 return loanRequestDao.getLoanRequestByCaseId(caseid);
	 }

	 public void deleteLoanRequest(LoanRequest loanRequest){
		 loanRequestDao.deleteLoanRequest(loanRequest);
	 }
}