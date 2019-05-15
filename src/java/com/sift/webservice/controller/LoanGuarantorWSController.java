/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.webservice.controller;

import com.sift.webservice.model.LoanGuarantorWS;
import com.sift.webservice.service.LoanGuarantorWSService;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Olakunle Awotunbo
 */
@Controller
@RequestMapping("/loanguarantor")
public class LoanGuarantorWSController {
    
    @Autowired 
    public LoanGuarantorWSService loanGuarantorService;
    
    /*
    // this controller isnot been used
    @RequestMapping(value = "/save", method = RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveGuarantor(@RequestBody LoanGuarantorWS loanGuarantor) {       
        
        String loanCaseId = randomLoancaseId();
        
        //First Guarantor
        LoanGuarantorWS lg1 = new LoanGuarantorWS();
        lg1.setAcceptanceStatus("N");
        lg1.setBranchId(loanGuarantor.getBranchId());
        lg1.setCompanyId(loanGuarantor.getCompanyId());
        lg1.setGuarantorNo(loanGuarantor.getGuarantorNo());
        lg1.setLoanCaseId(loanCaseId);
        lg1.setMemberNo(loanGuarantor.getMemberNo());
        lg1.setRequestDate(new java.util.Date());
        
        //Second Guarantor
        LoanGuarantorWS lg2 = new LoanGuarantorWS();
        lg2.setAcceptanceStatus("N");
        lg2.setBranchId(loanGuarantor.getBranchId());
        lg2.setCompanyId(loanGuarantor.getCompanyId());
        lg2.setGuarantorNo(loanGuarantor.getGuarantorComment()); //GuarantorComment used instead of second guarantor number
        lg2.setLoanCaseId(loanCaseId);
        lg2.setMemberNo(loanGuarantor.getMemberNo());
        lg2.setRequestDate(new java.util.Date());
        
         loanGuarantorService.saveGuarantor(lg1);
         loanGuarantorService.saveGuarantor(lg2);
        return "OK";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<LoanGuarantorWS> listData() {
        List<LoanGuarantorWS> loanGuarantor = loanGuarantorService.getAllLoanGuarantor();

        return loanGuarantor;
    }
    
    //not
    public String randomLoancaseId(){
        Random rand = new Random();
        int randomNo = rand.nextInt(9000) + 10000; 
        String loanCaseId = "4318082015"+ randomNo +"LON";        
        System.out.println("LoanCaseID: " + loanCaseId);
        return loanCaseId;
    }
    */
}
