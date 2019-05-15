/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.validator;
import com.sift.gl.model.Settingsdet;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class SettingsValidator implements Validator {
  public boolean supports(Class clazz) {
    return Settingsdet.class.isAssignableFrom(clazz);
  }  
  public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "settingval","required.settingval", "Value is required.");
     Settingsdet toaddaccountgroup = (Settingsdet) target;
     
    
  }
}
