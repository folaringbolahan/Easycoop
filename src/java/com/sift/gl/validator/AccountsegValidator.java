/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.validator;
import com.sift.gl.model.Accountsegmentdetails;
import java.util.List;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class AccountsegValidator implements Validator {
    private int counter = 0;
  public boolean supports(Class clazz) {
    return Accountsegmentdetails.class.isAssignableFrom(clazz);
  }  
  //public void setarrayval(int ii) {
  //  this.counter = ii;  
 // }
  public void validate(Object target, Errors errors) {
     //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "length","required.class", "Class is required.");
   /*  ValidationUtils.rejectIfEmpty(errors, "name","required.name", "Description is required.");
     //ValidationUtils.rejectIfEmpty(errors, "acGrpId","required.acGrpId", "Group Id is required.");
     Accountsegmentdetails dobj = (Accountsegmentdetails) target; 
    // String accountgroupdesc = dobj.getName();
     //dobj.getInuse();
      if ((inuse == true)&&(accountseglen== 0)) {
         errors.rejectValue("length","accsegdets[" + counter + "].length" ,"Invalid Length;Enter a number as Length.");
      }
      if (accountgroupdesc.length() > 50) {
         errors.rejectValue("name","accsegdets[" + counter + "].name", "Invalid Description;length must be less than or equal to 50.");
      } 
    **/
      int accountseglen = 0;
       boolean inuse = false;
      List<Accountsegmentdetails> acseglist = (List<Accountsegmentdetails>) target;
    
    //List<User> lstUser = form.getLstUser();
    List<Accountsegmentdetails> accountsegs = acseglist;  //.getAccsegdets();
 
    if (accountsegs == null) {
       return;
    }
 
    for (int i = 0; i < accountsegs.size(); i++) {
 
        Accountsegmentdetails accountseg = accountsegs.get(i);
        inuse = accountseg.getInuse();
        try
        {    
          accountseglen = Integer.parseInt(accountseg.getLength().toString());
        }
        catch (Exception ex) 
        {
          accountseglen = 0;  
        }    
        if ((inuse == true)&&(accountseglen== 0)) {
         errors.rejectValue("accsegdets[" + i + "].length","accsegdets[" + i + "].length" ,"Invalid Length;Enter a number as Length.");
        }
        if (accountseg.getName().length() > 50) {
         errors.rejectValue("accsegdets[" + i + "].name","accsegdets[" + i + "].name", "Invalid Description;length must be less than or equal to 50.");
        } 
        if (accountseg.getName() == null || "".equals(accountseg.getName().trim())) {
             errors.rejectValue("accsegdets[" + i + "].name", "accsegdets[" + i + "].name", "Invalid Description;Description cannot be empty.");
        }
 
    }
      
      
      
  }
}
