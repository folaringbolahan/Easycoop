/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.webservice.service;

import com.sift.webservice.dao.LoanGuarantorWSDAO;
import com.sift.webservice.model.LoanGuarantorWS;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service("loanGuarantorWSService")
public class LoanGuarantorWSServiceImpl implements LoanGuarantorWSService{
    
    @Autowired
    private LoanGuarantorWSDAO loanGuarantorDAO;

    @Override
    @Transactional
    public void saveGuarantor(LoanGuarantorWS loanGuarantor) {
        loanGuarantorDAO.saveGuarantor(loanGuarantor);
    }
    
    @Override
    @Transactional
    public List<LoanGuarantorWS> getAllLoanGuarantor() {
        return loanGuarantorDAO.getAllLoanGuarantor();
    }

    @Override
    @Transactional
    public String getMemberCorreAddress(String memberId) {
        return loanGuarantorDAO.getMemberCorreAddress(memberId);
    }
    
}
