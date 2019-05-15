/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.webservice.dao;

/**
 *
 * @author Olakunle Awotunbo
 */

import com.sift.loan.model.LoanRequest;
import com.sift.webservice.model.LoanRequestWS;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("LoanRequestWSDao")
public class LoanRequestDaoImplWS implements LoanRequestWSDao{
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void saveLoanRequest(String companyId, String branchId, String memberNo, 
            String loanType, double requestedAmount, String lastModifiedBy,
            String loanCaseId, String loanStatus, String requestBy, int noOfInstallments) {
        
        LoanRequestWS lqWS = new LoanRequestWS();
        
        lqWS.setCompanyId(companyId);
        lqWS.setBranchId(branchId);
        lqWS.setMemberNo(memberNo);
        lqWS.setLoanType(loanType);        
        lqWS.setRequestedAmount(requestedAmount);
        lqWS.setLastModifiedBy(lastModifiedBy);
        lqWS.setLoanCaseId(loanCaseId);
        lqWS.setLoanStatus(loanStatus);
        lqWS.setRequestBy(requestBy);
        lqWS.setNoOfInstallments(noOfInstallments);
        this.sessionFactory.getCurrentSession().save(lqWS);
        
    }
    
    //@Override
    @SuppressWarnings("unchecked")
    @Override
    public boolean saveLoan(LoanRequestWS loanRequestWS){
        boolean success = false;   
        try {
            this.sessionFactory.getCurrentSession().save(loanRequestWS);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return success;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<LoanRequestWS> getAllLoanRequest() {
        return this.sessionFactory.getCurrentSession().createQuery("from LoanRequestWS").list();
    }
    
    //nelson akpomuje
     @Override
    @SuppressWarnings("unchecked")
    public List<LoanRequestWS> getLoanRequestByMemberNo(String memberNo) {
          Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequestWS.class);
          criteria.add(Restrictions.or(Restrictions.eq("loanStatus","A"),Restrictions.eq("loanStatus","D")));
          criteria.add(Restrictions.eq("memberNo",memberNo));
         return  (List<LoanRequestWS>)criteria.list();
    }
    @SuppressWarnings("unchecked")
     public List<LoanRequestWS> listPendingLoanRequestByCompanyId(String companyId,String loanStatus){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequestWS.class);
	 criteria.add(Restrictions.eq("loanStatus",loanStatus));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 
	 return  (List<LoanRequestWS>)criteria.list();	 
    }

    
    @Override
    public LoanRequestWS getLoanRequestWS(String loanCaseID) {
         Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanRequestWS.class);
	 criteria.add(Restrictions.eq("loanCaseId",loanCaseID));
	 
	 return  criteria.list().size()==0?null:(LoanRequestWS)criteria.list().get(0);
        
    }
     
     
    
     
    @Override
    public int getEasyCoopLoanId(String loanCaseID) {
        int row=0;
         Query query = sessionFactory.getCurrentSession().
                 createQuery("SELECT easyCoopLoanId FROM LoanRequestWS WHERE loanCaseId ='"+loanCaseID+"'");
         // iterate through the query
         for(Iterator it = query.iterate(); it.hasNext();){
             row = Integer.valueOf(String.valueOf(it.next()));
         }	 
	 return  row;        
        
    }
   
    
}
