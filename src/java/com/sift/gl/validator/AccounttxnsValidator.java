/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.validator;
import com.sift.gl.model.Accounttxnsdetail;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class AccounttxnsValidator implements Validator {
  public boolean supports(Class clazz) {
    return Accounttxnsdetail.class.isAssignableFrom(clazz);
  }  
  public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountno","required.accountno", "Account No. is required.");
     ValidationUtils.rejectIfEmpty(errors, "startdate","required.startdate", "Start Date is required.");
     ValidationUtils.rejectIfEmpty(errors, "enddate","required.enddate", "End Date is required.");
     
  }
}
