/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.validator;
import com.sift.gl.model.AllItempara;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class PeriodcriteriaValidator implements Validator {
  public boolean supports(Class clazz) {
    return AllItempara.class.isAssignableFrom(clazz);
  }  
  public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "year","required.year", "Year is required.");
     ValidationUtils.rejectIfEmpty(errors, "periodId","required.periodId", "Period is required.");
     AllItempara toaddaccountyrprd = (AllItempara) target;
     Integer accountyr = 0;
     Integer accountprd = 0;
     int accountgroupgrpid = 0;
     try {
      accountyr = toaddaccountyrprd.getYear();
     }
     catch (NullPointerException npex) {
         errors.rejectValue("year","reportdet.year" ,"Invalid Year;Enter a number as Year.");
     }
     try {
      accountprd = toaddaccountyrprd.getPeriodId();
     }
     catch (NullPointerException npex) {
         errors.rejectValue("periodId","reportdet.periodId" ,"Invalid Period;Enter a number as Period.");
     }
     if ((accountyr == 0) ) {
         errors.rejectValue("year","reportdet.year" ,"Invalid Year;Enter a number as Year.");
     }
     if ((accountprd == 0)) {
         errors.rejectValue("periodId","reportdet.periodId" ,"Invalid Period;Enter a number as Period.");
     }
      
  }
}
