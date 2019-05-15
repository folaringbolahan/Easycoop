/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.webservice.service;

import com.sift.webservice.dao.LoanRequestWSDao;
import com.sift.webservice.model.LoanRequestWS;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service("loanRequestWSService")
@Transactional
// Propagation.REQUIRED is set as default
public class LoanRequestServiceImplWS implements LoanRequestServiceWS{
    
    @Autowired
    private LoanRequestWSDao loanRequestWSDao;
    
    @Override
    @Transactional
    public void saveLoanRequest(String companyId, String branchId, String memberNo, 
            String loanType, double requestedAmount, String lastModifiedBy,
            String loanCaseId, String loanStatus, String requestBy, int noOfInstallments) {
        
        
        loanRequestWSDao.saveLoanRequest(companyId, branchId, memberNo, loanType,  requestedAmount, lastModifiedBy,
                loanCaseId, loanStatus, requestBy, noOfInstallments);
        
    }
    
    @SuppressWarnings("unchecked")
    @Transactional
    public List<LoanRequestWS> getAllLoanRequest() {
        return loanRequestWSDao.getAllLoanRequest();
    }

    @Override
    @Transactional
    public boolean saveLoan(LoanRequestWS loanRequestWS) {
        return loanRequestWSDao.saveLoan(loanRequestWS);
    }

    @Override
    @Transactional(readOnly=true)
    public List<LoanRequestWS> listPendingLoanRequestByCompanyId(String companyId,String loanStatus){
		 return loanRequestWSDao.listPendingLoanRequestByCompanyId(companyId,loanStatus);
    }

    @Override
    @Transactional
    public LoanRequestWS getLoanRequestWS(String loanCaseID) {
        return loanRequestWSDao.getLoanRequestWS(loanCaseID);
    }
    
    @Override
    @Transactional
    public int getEasyCoopLoanId(String loanCaseID) {
        return loanRequestWSDao.getEasyCoopLoanId(loanCaseID);
    }
     //nelson akpomuje
    @SuppressWarnings("unchecked")
    @Transactional
    public List<LoanRequestWS> getLoanRequestByMemberNo(String memberNo) {
        return loanRequestWSDao.getLoanRequestByMemberNo(memberNo);
    }
}

