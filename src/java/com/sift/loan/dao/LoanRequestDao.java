package com.sift.loan.dao;

import java.util.ArrayList;
import java.util.List;

import com.sift.loan.model.BulkLoanRePayment;
import com.sift.loan.model.LoanGuarantor;
import com.sift.loan.model.LoanRepayment;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.LoanRequestException;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanRequestDao {
	 public void addLoanRequest(LoanRequest loanRequest);
	 public boolean approveLoanRequest(LoanRequest loanRequest,boolean overrideException);
	 public boolean addLoanRequest(LoanRequest loanRequest,ArrayList<LoanRepaymentSchedule> lrsList);
	 public boolean addLoanRequest(LoanRequest loanRequest,ArrayList<LoanRequestException> lreList,ArrayList<LoanGuarantor> lgList);
	 public List<LoanRequest> listLoanRequests();
	 public List<LoanRequest> listLoanRequestsByCompanyBranch(String companyId,String branchId);
	 public List<LoanRequest> listLoanRequestsByMember(String memberId,boolean currentOnly);
	 public List<LoanRequest> listLoanRequestsByMember(String memberId,boolean currentOnly,String companyId,String branchId);
	 public int getLoanRequestsCountByMember(String memberId);
	 public List<LoanRequest> listPendingLoanRequestByCompanyId(String companyId);
	 public List<LoanRequest> listPendingLoanRequestByCompanyId(String companyId,String loanStatus);
	 public List<LoanRequest> listPendingLoanRequestByBranchId(String branchId,String loanStatus);
	 public LoanRequest getLoanRequest(int id);
	 public LoanRequest getLoanRequestByCaseId(String caseid);
	 public boolean doDisburseLoan(LoanRequest loanRequest);
	 public void deleteLoanRequest(LoanRequest loanRequest);
	 public boolean doUpdateLoanBalance(LoanRequest loanRequest,LoanRepaymentSchedule schedule,boolean reset);	
	 public boolean doUpdateLoanBalance(LoanRequest loanRequest,BulkLoanRePayment bulkLoanRePayment,boolean reset);
}