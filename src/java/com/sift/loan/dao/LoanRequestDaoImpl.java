package com.sift.loan.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sift.admin.bean.AddressEntriesBean;
import com.sift.admin.model.Company;
import com.sift.loan.model.BulkLoanRePayment;
import com.sift.loan.model.LoanGuarantor;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.LoanRequestException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;

/**
 * @author Chris Faseun
 *
 */
@Repository("loanRequestDao")
public class LoanRequestDaoImpl implements LoanRequestDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addLoanRequest(LoanRequest loanRequest) {
   sessionFactory.getCurrentSession().saveOrUpdate(loanRequest);
 }
 
 public boolean approveLoanRequest(LoanRequest loanRequest,boolean overrideException){
	 org.hibernate.classic.Session session=null;
	 Transaction tx=null;  
	 boolean success=false;
	
	 try{
		    session = sessionFactory.getCurrentSession();		    
		    session.saveOrUpdate(loanRequest);	
		    
		    success=true;
		    
		    if(overrideException){
		      //update all exceptions raised to overriden
		    }	    
	}catch(HibernateException e){
		try{
			if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	}catch(RuntimeException e){
		try{
			if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	}finally{
		  tx=null;
	}	

	return success;
 }
 
 public boolean addLoanRequest(LoanRequest loanRequest,ArrayList<LoanRequestException> lreList,ArrayList<LoanGuarantor> lgList){
	 org.hibernate.classic.Session session=null;
	 Transaction tx=null;  
	 boolean success=false;
	
	 try{
		    session = sessionFactory.getCurrentSession();		    
		    session.saveOrUpdate(loanRequest);		   
		    
		    if(lreList!=null && lreList.size()>0){
			    for(LoanRequestException item: lreList){
				   session.saveOrUpdate(item); 
			    }
		    }

		    if(lgList!=null && lgList.size()>0){
			    for(LoanGuarantor item: lgList){
				   session.saveOrUpdate(item); 
			    }
		    }	
		    
		    success=true;
	}catch(HibernateException e){
		try{
			if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		success=false;
		throw e;
	}catch(RuntimeException e){
		try{
			if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		success=false;
		throw e;
	}finally{
		  tx=null;
	}	 
	
	return success;
 }
 
 
 public boolean addLoanRequest(LoanRequest loanRequest,ArrayList<LoanRepaymentSchedule> lrsList){
	 org.hibernate.classic.Session session=null;
	 Transaction tx=null; 
	 boolean success=false;
	
	 try{
		    double balanceInterest=0.0;
		    double balancePrincipal=0.0;
		    double balanceTotal=0.0;
		    //double totalInterest=0.0;
		 
		    if(lrsList!=null && lrsList.size()>0){
			    for(LoanRepaymentSchedule item: lrsList){
			    	balanceInterest+=item.getAmountInterest(); 
			    	balancePrincipal+=item.getAmountPrincipal();
			    }
			    
			    balanceTotal=balancePrincipal + balanceInterest;
			    
			    loanRequest.setBalanceInterest(balanceInterest);
			    loanRequest.setBalancePrincipal(balancePrincipal);
			    loanRequest.setBalanceTotal(balanceTotal);
			    loanRequest.setLoanIntTotal(balanceInterest);
		    }    
		 
		    session = sessionFactory.getCurrentSession();		    
		    session.saveOrUpdate(loanRequest);		   
		    
		    if(lrsList!=null && lrsList.size()>0){
			    for(LoanRepaymentSchedule item: lrsList){
				   session.saveOrUpdate(item); 
			    }
		    } 
		    
		    success=true;
	 }catch(HibernateException e){
		try{
			if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	 }catch(RuntimeException e){
		try{
			if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	 }finally{
		tx=null;
	 }	  
	 
	 return success;
 }

 @SuppressWarnings("unchecked")
 public List<LoanRequest> listLoanRequests(){
     return (List<LoanRequest>) sessionFactory.getCurrentSession().createCriteria(LoanRequest.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanRequest> listLoanRequestsByCompanyBranch(String companyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequest.class);
	 
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 	 
	 return  (List<LoanRequest>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanRequest> listLoanRequestsByMember(String memberId,boolean currentOnly){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequest.class);
	 if(currentOnly){
             //criteria.add(Restrictions.ne("loanStatus","C"));
             Criterion statusA = Restrictions.eq("loanStatus", "A");
             Criterion statusD = Restrictions.eq("loanStatus","D");
             LogicalExpression orExp = Restrictions.or(statusA, statusD);
             criteria.add(orExp);
         } //running loan Loan Status :{P/R/C}
	 criteria.add(Restrictions.eq("memberNo",memberId));
	 
	 return  (List<LoanRequest>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanRequest> listLoanRequestsByMember(String memberId,boolean currentOnly,String companyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequest.class);
	 //if(currentOnly){criteria.add(Restrictions.ne("loanStatus","C"));} //running loan Loan Status :{P/R/C}
	 if(currentOnly){criteria.add(Restrictions.in("loanStatus",new String[]{"A","D"}));} //running loan Loan Status :{A/D}
	 criteria.add(Restrictions.eq("memberNo",memberId));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 
	 return  (List<LoanRequest>)criteria.list();
 }
  
 @SuppressWarnings("unchecked")
 public List<LoanRequest> listLoanRequestsByMember(String memberId,boolean currentOnly,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequest.class);
	 //if(currentOnly){criteria.add(Restrictions.ne("loanStatus","C"));} //running loan Loan Status :{P/R/C}
	 if(currentOnly){criteria.add(Restrictions.in("loanStatus",new String[]{"A","D"}));} //running loan Loan Status :{P/R/C}
	 criteria.add(Restrictions.eq("memberNo",memberId));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 
	 return  (List<LoanRequest>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanRequest> listPendingLoanRequestByCompanyId(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequest.class);
	 criteria.add(Restrictions.eq("requestStatus","E"));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 
	 return  (List<LoanRequest>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanRequest> listPendingLoanRequestByCompanyId(String companyId,String loanStatus){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequest.class);
	 criteria.add(Restrictions.eq("loanStatus",loanStatus));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 
	 return  (List<LoanRequest>)criteria.list();	 
 }
 
 @SuppressWarnings("unchecked")
 public List<LoanRequest> listPendingLoanRequestByBranchId(String branchId,String loanStatus){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequest.class);
	 criteria.add(Restrictions.eq("requestStatus",loanStatus));
	 criteria.add(Restrictions.ne("loanStatus","C"));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 
	 return  (List<LoanRequest>)criteria.list();	 
 }
 
 public LoanRequest getLoanRequest(int id){
	 return (LoanRequest) sessionFactory.getCurrentSession().get(LoanRequest.class, id);
 }
 
 @SuppressWarnings("unchecked")
 public LoanRequest getLoanRequestByCaseId(String caseid){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequest.class);
	 criteria.add(Restrictions.eq("loanCaseId",caseid));
	 
	 return  criteria.list().size()==0?null:(LoanRequest)criteria.list().get(0);
 }
 
 public boolean doUpdateLoanBalance(LoanRequest loanRequest,LoanRepaymentSchedule schedule,boolean reset){
	 int KTCount=0;
	 
	 if(reset){
		 KTCount=sessionFactory.getCurrentSession().createQuery("update from LoanRequest set  balancePrincipal=0,balanceInterest=0.0,balanceTotal=0.0 where id = "+loanRequest.getId()).executeUpdate();
	 }else{
		 KTCount=sessionFactory.getCurrentSession().createQuery("update from LoanRequest set  balancePrincipal=balancePrincipal -"+schedule.getAmountPrincipal()+",balanceInterest=balanceInterest-"+schedule.getAmountInterest()+",balanceTotal=balanceTotal-"+schedule.getActualRepaymentAmount()+" where id = "+loanRequest.getId()).executeUpdate();
	 }
	 
	 return KTCount>0?true:false;
 }
 
 public boolean doUpdateLoanBalance(LoanRequest loanRequest,BulkLoanRePayment bulkLoanRePayment,boolean reset){
	 int KTCount=0;
	 
	 if(reset){
		 KTCount=sessionFactory.getCurrentSession().createQuery("update from LoanRequest set  balancePrincipal=0,balanceInterest=0.0,balanceTotal=0.0 where id = "+loanRequest.getId()).executeUpdate();
	 }else{
		 KTCount=sessionFactory.getCurrentSession().createQuery("update from LoanRequest set  balancePrincipal=balancePrincipal -"+bulkLoanRePayment.getRepayTotPrl()+",balanceInterest=balanceInterest-"+bulkLoanRePayment.getRepayTotInt()+",balanceTotal=balanceTotal-"+bulkLoanRePayment.getRepayTotAmt()+" where id = "+loanRequest.getId()).executeUpdate();
	 }
	 
	 return KTCount>0?true:false;
 }
 
 public void doApproveLoan(LoanRequest loanRequest) {
	 int KTCount=sessionFactory.getCurrentSession().createQuery("update from LoanRequest set requestStatus='"+loanRequest.getRequestStatus()+"' where id = "+loanRequest.getId()).executeUpdate();
 }
 
 public boolean doDisburseLoan(LoanRequest loanRequest){
	boolean success=false;
	
	try {
		sessionFactory.getCurrentSession().createQuery("update from LoanRequest set disburseDate=now(),disburseBy='"+loanRequest.getDisburseBy()+"',loanStatus='"+loanRequest.getLoanStatus()+"',requestStatus='"+loanRequest.getRequestStatus()+"' where id = "+loanRequest.getId()).executeUpdate();
		success=true;
	} catch (HibernateException e) {
		success=false;
		e.printStackTrace();
	}
	
	return success;
 }
 
 public int getLoanRequestsCountByMember(String memberId){
	  int row=0;
 	  Query query = sessionFactory.getCurrentSession().createQuery("select count(id) from LoanRequest where requestStatus in ('A','D') and memberNo='"+memberId+"'");  
     
 	  for(Iterator it=query.iterate();it.hasNext();){  
 	     row = Integer.valueOf(String.valueOf(it.next()));  
 	  }  
 	
 	  return row;
 }
 
 public Long getLoanScheduleID(String loanCaseId){
 	  long row=0;
 	  Query query = sessionFactory.getCurrentSession().createQuery("select max(id) from LoanRepaymentSchedule where loanCaseId='"+loanCaseId+"'");  
     
 	  for(Iterator it=query.iterate();it.hasNext();){  
 	     row = Long.valueOf(String.valueOf(it.next()));  
 	  }  
 	
 	  return row;
 }

 public void deleteLoanRequest(LoanRequest loanRequest) {
      sessionFactory.getCurrentSession().createQuery("delete from LoanRequest where id = "+loanRequest.getId()).executeUpdate();
 }
}