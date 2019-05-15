package com.sift.loan.dao;

import java.util.List;

import com.sift.loan.bean.LoanRepaymentScheduleBean;
import com.sift.loan.model.BalloonPayment;
import com.sift.loan.model.BulkLoanRePayment;
import com.sift.loan.model.LoanPayOff;
import com.sift.loan.model.LoanRepaymentSchedule;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface LoanRepaymentScheduleDao{
	 public void addLoanRepaymentSchedule(LoanRepaymentSchedule obj);
	 public List<LoanRepaymentSchedule> listLoanRepaymentSchedule();
	 public List<LoanRepaymentSchedule> listLoanRepaymentScheduleByLoanCaseId(String caseId);
	 public List<LoanRepaymentSchedule> listPendingLoanRepaymentScheduleByLoanCaseId(String caseId);
	 public LoanRepaymentSchedule getLoanRepaymentSchedule(int id);
	 public LoanRepaymentSchedule getNextLoanScheduleForRepayment(String caseId);
	 public void deleteLoanRepaymentSchedule(LoanRepaymentSchedule obj);
	 public boolean doBulkRepayment(List<LoanRepaymentScheduleBean> list);
	 public boolean doBalloonRepayment(String caseId);
	 public boolean doBalloonRepayment(String caseId,BalloonPayment balloonPayment);
	 public boolean doLoanPayOff(String caseId);
  	 public boolean doLoanPayOff(String caseId,LoanPayOff payOff);
	 public boolean doBulkLoanRepayment(String scheduleIds,BulkLoanRePayment bulkPayment,boolean closeLoan);
}