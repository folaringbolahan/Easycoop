package com.sift.loan.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.loan.bean.LoanRepaymentScheduleBean;
import com.sift.loan.model.BalloonPayment;
import com.sift.loan.model.BulkLoanRePayment;
import com.sift.loan.model.LoanPayOff;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.Product;
import com.sift.loan.service.LoanRequestService;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.EazyCoopUtility;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("loanRepaymentScheduleDao")
public class LoanRepaymentScheduleDaoImpl implements LoanRepaymentScheduleDao{
 @Autowired
 private LoanRequestService loanRequestService;
 
 @Autowired
 private ProductService productService;

 @Autowired
 private SessionFactory sessionFactory;

 EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

 public void addLoanRepaymentSchedule(LoanRepaymentSchedule loanRepaymentSchedule){
   sessionFactory.getCurrentSession().saveOrUpdate(loanRepaymentSchedule);
 }

 @SuppressWarnings("unchecked")
 public List<LoanRepaymentSchedule> listLoanRepaymentSchedule(){
  return (List<LoanRepaymentSchedule>) sessionFactory.getCurrentSession().createCriteria(LoanRepaymentSchedule.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanRepaymentSchedule> listLoanRepaymentScheduleByLoanCaseId(String caseId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRepaymentSchedule.class);
	 criteria.add(Restrictions.eq("loanCaseId",caseId));

	 Order order=Order.asc("expectedRepaymentDate");
     criteria.addOrder(order);
	 
	 return  (List<LoanRepaymentSchedule>)criteria.list();	 
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanRepaymentSchedule> listPendingLoanRepaymentScheduleByLoanCaseId(String caseId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRepaymentSchedule.class);
	 criteria.add(Restrictions.eq("loanCaseId",caseId));
	 criteria.add(Restrictions.eq("paymentStatus","N"));
	 //criteria.add(Restrictions.isNull("paymentStatus"));
	 
	 Order order=Order.asc("expectedRepaymentDate");
     criteria.addOrder(order);
	 
	 return  (List<LoanRepaymentSchedule>)criteria.list();	 
 }

 @SuppressWarnings("unchecked")
 public LoanRepaymentSchedule getLoanRepaymentSchedule(int id){
	 LoanRepaymentSchedule obj=  (LoanRepaymentSchedule)sessionFactory.getCurrentSession().get(LoanRepaymentSchedule.class, id);
     LoanRequest reqObj=loanRequestService.getLoanRequestByCaseId(obj.getLoanCaseId());
     Product pBean=productService.getProductByTypeCode(reqObj.getLoanType(),obj.getCompanyId(),obj.getBranchId());
     
	 int delayDays=eazyCoopUTIL.getDaysDiff(obj.getExpectedRepaymentDate(), eazyCoopUTIL.currentDate());
	 double scheduleAmount=obj.getAmountPrincipal();
	 
	 double penaltyIncurred=eazyCoopUTIL.calculatePenalty(scheduleAmount,delayDays,pBean.getPenalty());
	 obj.setPenaltyIncurred(penaltyIncurred);
	 
	 return obj; 
 } 
  
 @SuppressWarnings("unchecked")
 public LoanRepaymentSchedule getNextLoanScheduleForRepayment(String caseId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRepaymentSchedule.class);
	 criteria.add(Restrictions.eq("loanCaseId",caseId));
	 criteria.add(Restrictions.eq("paymentStatus","N"));
	 //criteria.add(Restrictions.isNull("paymentStatus"));
	 
	 Order order=Order.asc("id");
     criteria.addOrder(order);
	 
	 return  (LoanRepaymentSchedule)criteria.list().get(0);	 
 }

 public void deleteLoanRepaymentSchedule(LoanRepaymentSchedule loanRepaymentSchedule) {
	 sessionFactory.getCurrentSession().createQuery("DELETE FROM LoanRepaymentSchedule WHERE id = "+loanRepaymentSchedule.getId()).executeUpdate();
 } 
 
 @SuppressWarnings("unchecked")
 public boolean doBulkRepayment(List<LoanRepaymentScheduleBean> list){
	 int counter=0;
	 
	 for(LoanRepaymentScheduleBean item: list){
		 String query="update LoanRepaymentSchedule set actualRepaymentAmount='"+item.getActualRepaymentAmount()+"',paymentStatus='P',active="+item.getActive()+"  where id = "+item.getId();
		 counter+=sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	 }	 
	 
	 return counter==list.size()?true:false;
 }
 
 @SuppressWarnings("unchecked")
 public boolean doBalloonRepayment(String caseId){
	 int counter=0;
	 int counter2=0;
	 
	 String query="update LoanRepaymentSchedule set actualRepaymentAmount=expectedRepaymentAmount,lastModificationDate=now(),paymentStatus='P',active=Y  where loanCaseId = '"+caseId+"'";
	 counter=sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	 
	 query="update LoanRequest set loanStatus='C' where loanCaseId = '"+caseId+"'";
	 counter2=sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
 
	 return (counter>0  && counter2>0)?true:false;
 }
 
 @SuppressWarnings("unchecked")
 public boolean doBalloonRepayment(String caseId,BalloonPayment balloonPayment){
	 int counter=0;
	 int counter2=0;
	 
	 sessionFactory.getCurrentSession().saveOrUpdate(balloonPayment);
	 
	 String query="update LoanRepaymentSchedule set actualRepaymentAmount=expectedRepaymentAmount,lastModificationDate=now(),paymentStatus='P',active='Y'  where loanCaseId = '"+caseId+"'";
	 counter=sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	 
	 query="update LoanRequest set loanStatus='C' where loanCaseId = '"+caseId+"'";
	 counter2=sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	 	 
	 return (counter>0  && counter2>0)?true:false;
 }
 
 @SuppressWarnings("unchecked")
 public boolean doLoanPayOff(String caseId){
	 int counter=0;
	 int counter2=0;
	 
	 String query="update LoanRepaymentSchedule set actualRepaymentAmount=expectedRepaymentAmount,lastModificationDate=now(),paymentStatus='P',active='Y'  where loanCaseId = '"+caseId+"'";
	 counter=sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	 
	 query="update LoanRequest set requestStatus='C',loanStatus='C' where loanCaseId = '"+caseId+"'";
	 counter2=sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
 
	 return (counter>0  && counter2>0)?true:false;
 }
 
 @SuppressWarnings("unchecked")
 public boolean doLoanPayOff(String caseId,LoanPayOff payOff){
	 int counter=0;
	 int counter2=0;
	 
	 sessionFactory.getCurrentSession().saveOrUpdate(payOff);
	 
	 String query="update LoanRepaymentSchedule set actualRepaymentAmount=expectedRepaymentAmount,actualRepaymentDate=now(),lastModifiedBy='"+payOff.getProcessor()+"',lastModificationDate=now(),paymentStatus='P',active='Y'  where loanCaseId = '"+caseId+"'";
	 counter=sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	 
	 query="update LoanRequest set requestStatus='C',loanStatus='C' where loanCaseId = '"+caseId+"'";
	 counter2=sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	 	 
	 return (counter>0  && counter2>0)?true:false;
 }
 
 @SuppressWarnings("unchecked")
 public boolean doBulkLoanRepayment(String scheduleIds,BulkLoanRePayment bulkPayment,boolean closeLoan){
	 int counter=0;
	 int counter2=0;
	 
	 sessionFactory.getCurrentSession().saveOrUpdate(bulkPayment);	 
	 String query="update LoanRepaymentSchedule set actualRepaymentAmount=expectedRepaymentAmount,lastModificationDate=now(),paymentStatus='P',active='Y'  where id IN ("+scheduleIds+")";
	 counter=sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	 
	 if(closeLoan){
		 query="update LoanRequest set loanStatus='C' where loanCaseId = '"+bulkPayment.getLoanCaseId()+"'";
		 counter2=sessionFactory.getCurrentSession().createQuery(query).executeUpdate();
	 }else{
		 counter2=1; 
	 }
	 	 
	 return (counter>0  && counter2>0)?true:false;
 }
}