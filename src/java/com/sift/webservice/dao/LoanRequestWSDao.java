/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.webservice.dao;

import com.sift.loan.model.LoanRequest;
import com.sift.webservice.model.LoanRequestWS;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Olakunle Awotunbo
 */
public interface LoanRequestWSDao {
    public void saveLoanRequest(String companyId, String branchId, String memberNo, String loanType,
            double requestedAmount,String lastModifiedBy, String loanCaseId, String loanStatus,
            String requestBy, int noOfInstallments);
    public boolean saveLoan(LoanRequestWS loanRequestWS);
    public List<LoanRequestWS> getAllLoanRequest();
    public List<LoanRequestWS> listPendingLoanRequestByCompanyId(String companyId,String loanStatus);
    public LoanRequestWS getLoanRequestWS(String loanCaseID);
    public int getEasyCoopLoanId(String loanCaseID);
    public List<LoanRequestWS> getLoanRequestByMemberNo( String memberNo);
}
