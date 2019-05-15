/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.validator;
import com.sift.gl.model.Accountgroupdetail;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class AccountgroupValidator implements Validator {
  public boolean supports(Class clazz) {
    return Accountgroupdetail.class.isAssignableFrom(clazz);
  }  
  public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "class","required.class", "Class is required.");
     ValidationUtils.rejectIfEmpty(errors, "description","required.description", "Description is required.");
     ValidationUtils.rejectIfEmpty(errors, "acGrpId","required.acGrpId", "Group Id is required.");
     Accountgroupdetail toaddaccountgroup = (Accountgroupdetail) target;
     String accountgroupclass = toaddaccountgroup.getClassId();
     String accountgroupdesc = toaddaccountgroup.getDescription();
     int accountgroupgrpid = 0;
     try {
      accountgroupgrpid = toaddaccountgroup.getAcGrpId();
     }
     catch (NullPointerException npex) {
         accountgroupgrpid = 0;
     }
     if ((accountgroupgrpid == 0) ) {
         errors.rejectValue("acGrpId","accountgroupdet.acGrpId" ,"Invalid Group Id;Enter a number as Group Id.");
      }
     if (accountgroupclass.length() <= 0) {
         errors.rejectValue("class","accountgroupdet.classid" ,"Invalid class;Class must be selected.");
      }
      if (accountgroupdesc.length() > 50) {
         errors.rejectValue("description","accountgroupdet.description", "Invalid Description;length must be less than or equal to 50.");
      } 
    
  }
}
