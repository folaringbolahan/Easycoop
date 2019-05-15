/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.validator;
import com.sift.gl.model.Holidatesdet;
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
public class HolidatesValidator implements Validator {
  public boolean supports(Class clazz) {
    return Holidatesdet.class.isAssignableFrom(clazz);
  }  
  public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description","required.description", "Value is required.");
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "holidate","required.holidate", "Date is required.");
     SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
     Holidatesdet holi = (Holidatesdet) target;
     String dateop = holi.getHolidate();
       try {
         formatter1.parse(dateop);
        } catch(ParseException pEx ) 
        {   
          errors.rejectValue("holidate","holidatedet.holidate", "Invalid Date;format must be dd/MM/yyyy");
        } catch( NullPointerException nullEx ) 
        {   
          errors.rejectValue("holidate","holidatedet.holidate", "Invalid Date;format must be dd/MM/yyyy");
        }
  }
}
