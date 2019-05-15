package com.sift.loan.service;

import java.util.List;

import com.sift.loan.bean.LoanRepaymentScheduleBean;
import com.sift.loan.model.BalloonPayment;
import com.sift.loan.model.LoanPayOff;
import com.sift.loan.model.BulkLoanRePayment;
import com.sift.loan.model.LoanRepaymentSchedule;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanRepaymentScheduleService {
	public void addLoanRepaymentSchedule(LoanRepaymentSchedule loanRepaymentSchedule);
	public List<LoanRepaymentSchedule> listLoanRepaymentSchedule();
	public List<LoanRepaymentSchedule> listLoanRepaymentScheduleByLoanCaseId(String caseId);
	public List<LoanRepaymentSchedule> listPendingLoanRepaymentScheduleByLoanCaseId(String caseId);
	public LoanRepaymentSchedule getLoanRepaymentSchedule(int id);
	public LoanRepaymentSchedule getNextLoanScheduleForRepayment(String caseId);
	public void deleteLoanRepaymentSchedule(LoanRepaymentSchedule loanRepaymentSchedule);
	public boolean doBulkRepayment(List<LoanRepaymentScheduleBean> list);
	public boolean doBalloonRepayment(String caseId);
	public boolean doBalloonRepayment(String caseId,BalloonPayment bulkRePayment);
	public boolean doLoanPayOff(String caseId);
	public boolean doLoanPayOff(String caseId,LoanPayOff payOff);
	public boolean doBulkLoanRepayment(String scheduleIds,BulkLoanRePayment bulkRePayment,boolean closeLoan);
}