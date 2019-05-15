/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.webservice.service;

import com.sift.webservice.model.LoanGuarantorWS;
import java.util.List;

/**
 *
 * @author Olakunle Awotunbo
 */
public interface LoanGuarantorWSService {
    public void saveGuarantor(LoanGuarantorWS loanGuarantor);
    public List<LoanGuarantorWS> getAllLoanGuarantor();
    public String getMemberCorreAddress(String memberId);
}
