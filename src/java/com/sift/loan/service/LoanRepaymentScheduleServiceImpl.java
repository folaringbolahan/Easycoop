package com.sift.loan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.loan.model.BalloonPayment;
import com.sift.loan.model.BulkLoanRePayment;
import com.sift.loan.model.LoanPayOff;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.bean.LoanRepaymentScheduleBean;
import com.sift.loan.dao.LoanRepaymentScheduleDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("loanRepaymentScheduleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoanRepaymentScheduleServiceImpl implements LoanRepaymentScheduleService{
	 @Autowired
	 private LoanRepaymentScheduleDao loanRepaymentScheduleDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addLoanRepaymentSchedule(LoanRepaymentSchedule loanRepaymentSchedule){
		 loanRepaymentScheduleDao.addLoanRepaymentSchedule(loanRepaymentSchedule);
	 }

	 public List<LoanRepaymentSchedule> listLoanRepaymentSchedule(){
	  return loanRepaymentScheduleDao.listLoanRepaymentSchedule();
	 }

	 public List<LoanRepaymentSchedule> listLoanRepaymentScheduleByLoanCaseId(String caseId){
		  return loanRepaymentScheduleDao.listLoanRepaymentScheduleByLoanCaseId(caseId);
	 }
	 
	 public List<LoanRepaymentSchedule> listPendingLoanRepaymentScheduleByLoanCaseId(String caseId){
		 return loanRepaymentScheduleDao.listPendingLoanRepaymentScheduleByLoanCaseId(caseId);
	 }

	 public LoanRepaymentSchedule getLoanRepaymentSchedule(int id){
	      return loanRepaymentScheduleDao.getLoanRepaymentSchedule(id);
	 }

	 public LoanRepaymentSchedule getNextLoanScheduleForRepayment(String caseId){
		  return loanRepaymentScheduleDao.getNextLoanScheduleForRepayment(caseId);
	 }

	 public void deleteLoanRepaymentSchedule(LoanRepaymentSchedule loanRepaymentSchedule){
		 loanRepaymentScheduleDao.deleteLoanRepaymentSchedule(loanRepaymentSchedule);
	 }
	 
	 public boolean doBulkRepayment(List<LoanRepaymentScheduleBean> list){
		 return loanRepaymentScheduleDao.doBulkRepayment(list);
	 }

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean doBalloonRepayment(String caseId){
		 return loanRepaymentScheduleDao.doBalloonRepayment(caseId);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean doBalloonRepayment(String caseId,BalloonPayment balloonPayment){
		 return loanRepaymentScheduleDao.doBalloonRepayment(caseId,balloonPayment);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean doLoanPayOff(String caseId){
		 return loanRepaymentScheduleDao.doLoanPayOff(caseId);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean doLoanPayOff(String caseId,LoanPayOff payOff){
		 return loanRepaymentScheduleDao.doLoanPayOff(caseId,payOff);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean doBulkLoanRepayment(String scheduleIds,BulkLoanRePayment bulkPayment,boolean closeLoan){
		 return loanRepaymentScheduleDao.doBulkLoanRepayment(scheduleIds,bulkPayment,closeLoan);
	 }
}