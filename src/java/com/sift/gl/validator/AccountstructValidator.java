/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.validator;
import com.sift.gl.model.Accountstructuresdetails;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class AccountstructValidator implements Validator {
  public boolean supports(Class clazz) {
    return Accountstructuresdetails.class.isAssignableFrom(clazz);
  }  
  public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "structurecode","required.structurecode", "Code is required.");
     ValidationUtils.rejectIfEmpty(errors, "description","required.description", "Description is required.");
     Accountstructuresdetails toaddaccountgroup = (Accountstructuresdetails) target;
     String accountgroupclass = toaddaccountgroup.getStructurecode();
     String accountgroupdesc = toaddaccountgroup.getDescription();
     int segid1 = toaddaccountgroup.getSeg1();
     int segid2 = toaddaccountgroup.getSeg2();
     int segid3 = toaddaccountgroup.getSeg3();
     int segid4 = toaddaccountgroup.getSeg4();
     int segid5 = toaddaccountgroup.getSeg5();
     int segid6 = toaddaccountgroup.getSeg6();
     int segid7 = toaddaccountgroup.getSeg7();
     int segid8 = toaddaccountgroup.getSeg8();
     int segid9 = toaddaccountgroup.getSeg9();
     int segid10 = toaddaccountgroup.getSeg10();
    /// try {
    //  accountgroupgrpid = toaddaccountgroup.getAcGrpId();
    // }
    // catch (NullPointerException npex) {
   //      accountgroupgrpid = 0;
   //  }
     ///if ((accountgroupgrpid == 0) ) {
     ///    errors.rejectValue("acGrpId","accountstructdet.acGrpId" ,"Invalid Group Id;Enter a number as Group Id.");
     /// }
     if (accountgroupclass.length() <= 0) {
         errors.rejectValue("structurecode","accountstructdet.structurecode" ,"Invalid code;Code must be supplied.");
      }
      if (accountgroupdesc.length() > 50) {
         errors.rejectValue("description","accountstructdet.description", "Invalid Description;length must be less than or equal to 50.");
      } 
      if ((segid1 == 0)&&(segid2 == 0)&&(segid3 == 0)&&(segid4 == 0)&&(segid5 == 0)&&(segid6 == 0)&&(segid7 == 0)&&(segid8 == 0)&&(segid9 == 0)&&(segid10 == 0)) {
         errors.rejectValue("seg1","accountstructdet.seg1", "Invalid Segment;You must select at least one Account segment.");
      } 
  }
}
