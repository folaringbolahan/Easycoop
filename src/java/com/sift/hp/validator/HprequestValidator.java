/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.validator;
import com.sift.gl.validator.*;
import com.sift.gl.GendataService;
import com.sift.gl.model.Account;
import com.sift.hp.model.Hprequestdetails;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class HprequestValidator implements Validator {
    private Integer companyid;
    private String wkmode="N";
  public boolean supports(Class clazz) {
    return Hprequestdetails.class.isAssignableFrom(clazz);
  }  
  public void setCoyid(Integer coyid){
      this.companyid = coyid;
  }
  public void setMode(String wkmode){
      this.wkmode = wkmode;
  }
  public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmpty(errors, "memberno","required.memberno", "Member is required.");
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product","required.product", "Product is required.");
      ValidationUtils.rejectIfEmpty(errors, "txndatestr","required.txndatestr", "Transaction date is required.");
     ValidationUtils.rejectIfEmpty(errors, "cashprice","required.cashprice", "Cash price is required.");
     ValidationUtils.rejectIfEmpty(errors, "downpaymentamount","required.downpaymentamount", "Downpayment Amount is required.");
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "interestrate","required.interestrate", "Interest Rate is required.");
      ValidationUtils.rejectIfEmpty(errors, "repaymentperiodinmonths","required.repaymentperiodinmonths", "Repayment Period is required.");
     ValidationUtils.rejectIfEmpty(errors, "repaymentfrequency","required.repaymentfrequency", "Repayment Frequency is required.");
     ValidationUtils.rejectIfEmpty(errors, "interestcalcmtd","required.interestcalcmtd", "Interest calculation method is required.");
   /*  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product","required.product", "Product is required.");
      ValidationUtils.rejectIfEmpty(errors, "txndatestr","required.txndatestr", "Transaction date is required.");
     ValidationUtils.rejectIfEmpty(errors, "cashprice","required.cashprice", "Cash price is required.");*/
     Hprequestdetails toaddaccount = (Hprequestdetails) target;
      String dateop = toaddaccount.getTxndatestr();
     
      if ((toaddaccount.getDownpaymentamount()>toaddaccount.getCashprice())||(toaddaccount.getDownpaymentamount()< 0)) {
        errors.rejectValue("downpaymentamount","hprequestdet.downpaymentamount", "Invalid Down Payment Amount; must be less than Cash Price and > 0 ");   
      }
      if ((toaddaccount.getInterestrate()>100)||(toaddaccount.getInterestrate()< 0)) {
        errors.rejectValue("interestrate","hprequestdet.interestrate", "Invalid Interest rate; must be less than 100 and > 0");   
      }
      if (toaddaccount.getInterestamt()< 0) {
        errors.rejectValue("interestamt","hprequestdet.interestamt", "Invalid Interest amount; must not be less than 0");   
      }
      if (toaddaccount.getCashprice()< 0) {
        errors.rejectValue("cashprice","hprequestdet.cashprice", "Invalid Cash Price; must not be less than 0");   
      }
      if (toaddaccount.getHpprice()< 0) {
        errors.rejectValue("hpprice","hprequestdet.hpprice", "Invalid Hire Purchase Price; must not be less than 0");   
      }
      if (toaddaccount.getRepaymentperiodinmonths()< 0) {
        errors.rejectValue("repaymentperiodinmonths","hprequestdet.repaymentperiodinmonths", "Invalid Repayment period; must not be less than 0");   
      }
      if ((toaddaccount.getInterestcalcmtd().equalsIgnoreCase("ACE")) && (toaddaccount.getHpprice()== 0.0)) {
        errors.rejectValue("hpprice","hprequestdet.hpprice", "Please enter Hire Purchase Price");   
      }
      
       SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
       try {
         formatter1.parse(dateop);
        } catch(ParseException pEx ) 
        {   
          errors.rejectValue("txndatestr","hprequestdet.txndatestr", "Invalid Date.;format must be dd/MM/yyyy");
        } catch( NullPointerException nullEx ) 
        {   
          errors.rejectValue("txndatestr","hprequestdet.txndatestr", "Invalid Date.;format must be dd/MM/yyyy");
        }
      }
       
    
}
