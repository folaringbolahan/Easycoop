/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.validator;
import com.sift.gl.validator.*;
import com.sift.gl.GendataService;
import com.sift.gl.model.Account;
import com.sift.hp.model.Hprepymtschddetails;
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
public class HppaymntsingValidator implements Validator {
    private Integer companyid;
    private String wkmode="N";
  public boolean supports(Class clazz) {
    return Hprepymtschddetails.class.isAssignableFrom(clazz);
  }  
  
  public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmpty(errors, "instalment","required.instalment", "Instalment is required.");
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "principal","required.principal", "Principal is required.");
      ValidationUtils.rejectIfEmpty(errors, "paymentdatestr","required.paymentdatestr", "Payment date is required.");
     ValidationUtils.rejectIfEmpty(errors, "interest","required.interest", "Interest is required.");
      Hprepymtschddetails toaddaccount = (Hprepymtschddetails) target;
      String dateop = toaddaccount.getPaymentdatestr();
      boolean existent = false;
      existent = getifexists(toaddaccount.getRefid(),toaddaccount.getId());
      if (existent==false) {
        errors.rejectValue("instalment","hpscdldetres.instalment", "Attempt to Pay an Invalid Instalment. Aborting.. ");   
      }
      
       SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
       try {
         formatter1.parse(dateop);
        } catch(ParseException pEx ) 
        {   
          errors.rejectValue("paymentdatestr","hpscdldetres.paymentdatestr", "Invalid Date.;format must be dd/MM/yyyy");
        } catch( NullPointerException nullEx ) 
        {   
          errors.rejectValue("paymentdatestr","hpscdldetres.paymentdatestr", "Invalid Date.;format must be dd/MM/yyyy");
        }
      }
   
  public boolean getifexists(String refid,int did){
    GendataService dbobj = new GendataService();
    dbobj.inimkcon();
    ResultSet rs=null;
    boolean itexists = false;
       try {
         rs =  dbobj.retrieveRecset("SELECT b.* FROM qrynexthprepaymtscdl b where b.refid = '" + refid + "' and b.id = " + did);
         while (rs.next()) {  
             itexists = true;
           }  
        } catch (SQLException ex) {
         
        } 
        finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
       return itexists;
    }
}
