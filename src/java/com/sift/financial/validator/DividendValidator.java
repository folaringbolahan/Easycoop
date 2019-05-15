/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.validator;

import com.sift.financial.member.*;
import static com.sift.financial.utility.customutil.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author baydel200
 */
public class DividendValidator implements Validator {
    
  // @Autowired
   private DividendDAO dividendDao;
   
   @Autowired
   @Value("${notFailState}")
   private String notFailState; 

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(Dividend.class);
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validate(Object o, Errors errors) {
        
        Dividend comp = (Dividend)o;
        
        if(comp.getAction().equals("ADD") || comp.getAction().equals("EDIT"))
        {
            
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dividendType", "error.dividend.empty.dividendType","Kindly select a dividend type");
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "divYear", "error.dividend.empty.divYear","Please enter year of dividend");
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "divPeriod", "error.dividend.empty.divPeriod","Kindly enter the dividend period");
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "divNumber", "error.dividend.empty.divNumber","Kindly enter the dividend number");
                   //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "formula", "error.dividend.empty.formula","Kindly select the Excess account for the Stock");

                   //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "divoperand", "error.dividend.empty.divoperand","Please select if Stock is required for member registration");
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "divValue", "error.dividend.empty.divValue","You have not entered value for the dividend ");
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "formula", "error.dividend.empty.formula","KIndly indicate if dividend is derived by formula or otherwise");

                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "divDeclarationDate", "error.dividend.empty.divDeclarationDate","Kindly enter the dividend declaration date");
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "divDateRecord", "error.dividend.empty.divDateRecord","Kindly enter the dividend record date");
                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "divPayDate", "error.dividend.empty.divPayDate","Kindly enter the dividend payment date");

                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "banks", "error.dividend.empty.banks","Please select dividend payment bank");

                   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dividendPayAccount", "error.dividend.empty.dividendPayAccount","Please select dividend payment account @ bank");
        }
        
        if(!errors.hasErrors())
        {
         // check if declaration date is not a future date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
            Date today = new Date();
            
             System.out.println("Today :: " + sdf.format(today));
             System.out.println("DivDeclarationDate:: " + sdf.format(comp.getDivDeclarationDate()));
            
            if(truncateDate(today).compareTo(comp.getDivDeclarationDate())< 0)
            {
                System.out.println("Today :: " + sdf.format(today));
                System.out.println("DivDeclarationDate:: " + sdf.format(comp.getDivDeclarationDate()));
                
                errors.rejectValue("divDeclarationDate", "error.dividend.invalid.divDeclarationDate","Dividend Declaration Date cannot be a future Date Today :: " + sdf.format(today) + "  DivDeclarationDate:: " + sdf.format(comp.getDivDeclarationDate()));
            }
            
          //record date cannot be a future date
            
            System.out.println("Today :: " + sdf.format(today));
            System.out.println("DivDateRecord:: " + sdf.format(comp.getDivDateRecord()));
                
            if(truncateDate(today).compareTo(comp.getDivDateRecord())< 0)
            {
                System.out.println("Today :: " + sdf.format(today));
                System.out.println("DivDateRecord:: " + sdf.format(comp.getDivDateRecord()));
                
             errors.rejectValue("divDateRecord", "error.dividend.invalid.divDateRecord","Dividend Record Date cannot be a future Date Today :: " + sdf.format(today) + " DivDateRecord:: " + sdf.format(comp.getDivDateRecord()));
            }
            
          //payment date has today or future
            
            if(truncateDate(today).compareTo(comp.getDivPayDate())> 0)
            {
             errors.rejectValue("divPayDate", "error.dividend.invalid.divPayDate","Dividend Payment Date must be a future Date or today");
            }
            
            if(!isNumber(comp.getDivYear()))
            {
                errors.rejectValue("divYear", "error.dividend.invalid.divYear","Dividend year must be a valid year ");
            }
            
             if(!isNumber(comp.getDivPeriod()))
            {
                errors.rejectValue("divYear", "error.dividend.invalid.divPeriod","Dividend Period must be a number");
            }
             
            if(!isNumber(comp.getDivNumber()))
            {
                errors.rejectValue("divNumber", "error.dividend.invalid.divNumber","Dividend Number must be a number");
            }
            
          //applies only if ADD not EDIT
            if(comp.getAction().equals("ADD"))
            {
            //check if period and year has been defined before and approved or rejectyed
                
               Dividend divObj = null;
                
               try
               {
                   System.out.println("comp.getDivYear() " + comp.getDivYear());
                   System.out.println("comp.getCompanyId() " + comp.getCompanyId());
                   System.out.println("comp.getDivPeriod() " + comp.getDivPeriod());
                   System.out.println("notFailState " + notFailState);
                   
                   divObj = getDividendDao().findByYearPeriodCompStatState(comp.getDivYear(), comp.getCompanyId(), comp.getDivPeriod(),notFailState);
                   
               }
               catch(Exception ex)
               {
                    errors.rejectValue("divYear", "error.dividend.invalid.divYear","Issues retrieving existing data ");
                    ex.printStackTrace();
               }
                
               if(divObj!=null)
               {
                     errors.rejectValue("divYear", "error.dividend.invalid.divYear","An active dividend Record already exists for this Year and Period");
               }
               
              
               divObj = null;
               
               try
               {
                  
                   System.out.println("notFailState " + notFailState);
                   
                   divObj = getDividendDao().findByDivNumberCompStatState(comp.getDivNumber(), comp.getCompanyId(), notFailState);
                   
               }
               catch(Exception ex)
               {
                    errors.rejectValue("divNumber", "error.dividend.invalid.divNumber","Issues retrieving existing data ");
                    ex.printStackTrace();
               }
                
               if(divObj!=null)
               {
                     errors.rejectValue("divNumber", "error.dividend.invalid.divNumber","An active dividend Record already exists for this Dividend Number");
               }
               
            }
            
            
        }
    }

    public DividendDAO getDividendDao() {
        return dividendDao;
    }
    
     @Autowired
    public void setDividendDao(DividendDAO dividendDao) {
        this.dividendDao = dividendDao;
    }
    
}
