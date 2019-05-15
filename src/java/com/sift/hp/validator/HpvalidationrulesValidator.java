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
public class HpvalidationrulesValidator implements Validator {
    private Integer companyid;
    private String wkmode="N";
  public boolean supports(Class clazz) {
    return Hprequestdetails.class.isAssignableFrom(clazz);
  }  
   public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmpty(errors, "code","required.code", "Code is required.");
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productcode","required.productcode", "Product is required.");
      ValidationUtils.rejectIfEmpty(errors, "description","required.description", "Description is required.");
     ValidationUtils.rejectIfEmpty(errors, "validationtype","required.validationtype", "Ruletype is required.");
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "formula","required.formula", "Formula for validation is required.");
      ValidationUtils.rejectIfEmpty(errors, "resultcond","required.resultcond", "Conditional Operator is required.");
    
       
      }
       
    
}
