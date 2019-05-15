/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.validator;
import com.sift.gl.model.Startofdaydate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class StartofdaydateValidator implements Validator {
  public boolean supports(Class clazz) {
    return Startofdaydate.class.isAssignableFrom(clazz);
  }  
  public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startdatestr","required.startdatestr", "PostDate is required.");
     SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
     Startofdaydate startdate = (Startofdaydate) target;
     String dateop = startdate.getStartdatestr();
       try {
         formatter1.parse(dateop);
        } catch(ParseException pEx ) 
        {   
          errors.rejectValue("startdatestr","startofdaydate.startdatestr", "Invalid Date;format must be dd/MM/yyyy");
        } catch( NullPointerException nullEx ) 
        {   
          errors.rejectValue("startdatestr","startofdaydate.startdatestr", "Invalid Date;format must be dd/MM/yyyy");
        }
  }
}
