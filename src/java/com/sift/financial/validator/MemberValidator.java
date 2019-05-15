/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.validator;


import com.sift.financial.member.Member;
import com.sift.financial.utility.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author baydel200
 */
public class MemberValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(Member.class); 
    }

    @Override
    public void validate(Object o, Errors errors) {
        
        Member member = (Member)o;
        
        if(member.getAction().equals("ADD") || member.getAction().equals("EDIT"))
        {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.member.empty.firstName","Kindly enter a valid firstname for member");
            //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "middleName", "error.member.empty.defaultStock","Please select if Stock is required for member registration");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "error.member.empty.surname","Kindly enter a valid surname for member");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "error.member.empty.gender","Kindly select a valid gender for member");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dob", "error.member.empty.dob","Kindly use the calendar to enter a valid date of birth for member");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "religion", "error.member.empty.religion","Kindly select a valid religion for member");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNo1", "error.member.empty.phoneNo1","Kindly enter a valid phone number for member");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAdd1", "error.member.empty.emailAdd1","Kindly enter a valid email address for member");
            //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberCompId", "error.member.empty.memberCompId","Kindly enter a member's unique identifier at the cooperative - i.e. ID Number or registration number at Cooperative ");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberType.memberTypeId", "error.member.empty.memberType","Kindly select the member's profile type ");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identificationDoc.identificationDocId", "error.member.empty.identificationDoc","Kindly select a valid identification document for member");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identificationCode", "error.member.empty.identificationCode","Kindly enter the identification code or number on the identification document for member");
            //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taxGroups.id", "error.member.empty.taxGroups","Kindly select member's tax group");
           ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nokName", "error.member.empty.nokName","Kindly enter Next Of Kin First Name");
           ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nokSurname", "error.member.empty.nokSurname","Kindly enter Next Of Kin Surname");
           ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nokPhone", "error.member.empty.nokPhone","Kindly enter Next Of Kin phone number");
           // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addressStr", "error.member.empty.addressStr","Kindly select the Trading account for the Stock or select 'Create Acct' for new account to be created");
           // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stockStr", "error.member.empty.stockStr","Kindly select the Trading account for the Stock or select 'Create Acct' for new account to be created");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "banks.bankId", "error.member.empty.banks","Kindly select a valid bank for member");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bankAccount", "error.member.empty.bankAccount","Kindly enter member's bank account");   
        
             if(member.getDob()!=null && member.getDob().equals(""))
             {
                 
               /**  if(customutil.(member.getPhoneNo1()))
                 {
                     errors.rejectValue(null, null, null);
                 } 
                 */
             }
             
             if(member.getPhoneNo1()!=null && member.getPhoneNo1().equals(""))
             {
                 if(!customutil.isValidPhone(member.getPhoneNo1()))
                 {
                     errors.rejectValue("phoneNo1", "error.member.invalid.phoneNo1","Kindly enter a valid phone number for member");
                 } 
             }             
             if(member.getPhoneNo2()!=null && member.getPhoneNo2().equals(""))
             {
                 if(!customutil.isValidPhone(member.getPhoneNo2()))
                 {
                    errors.rejectValue("phoneNo2", "error.member.invalid.phoneNo2","Kindly enter a valid phone number for member");
                 } 
             }
              if(member.getPhoneNo3()!=null && member.getPhoneNo3().equals(""))
             {
                 if(!customutil.isValidPhone(member.getPhoneNo3()))
                 {
                  errors.rejectValue("phoneNo3", "error.member.invalid.phoneNo3","Kindly enter a valid phone number for member");
                 } 
             }
              
             if(member.getPhoneNo1()!=null && member.getPhoneNo1().equals("") && member.getPhoneNo2()!=null && member.getPhoneNo2().equals("") && member.getPhoneNo3()!=null && member.getPhoneNo3().equals(""))
             {
                 if(member.getPhoneNo1().matches(member.getPhoneNo2()) && member.getPhoneNo2().matches(member.getPhoneNo3()) )
                 {
                     errors.rejectValue("phoneNo1", "error.member.invalid.phoneNo1","You have provided same number for all phone records...kindly correct");
                 }
             }
              
             if(member.getEmailAdd1()!=null && member.getEmailAdd1().equals(""))
             {
                 if(!customutil.isValidEmail(member.getEmailAdd1()))
                 {
                     errors.rejectValue("emailAdd1", "error.member.invalid.emailAdd1","Kindly enter a valid email address for member");
                 } 
             }
             
            if(member.getEmailAdd2()!=null && member.getEmailAdd2().equals(""))
             {
                 if(!customutil.isValidEmail(member.getEmailAdd2()))
                 {
                     errors.rejectValue("emailAdd2", "error.member.invalid.emailAdd2","Kindly enter a valid email address for member");
                 } 
             }
            
            if(member.getEmailAdd3()!=null && member.getEmailAdd3().equals(""))
             {
                 if(!customutil.isValidEmail(member.getEmailAdd3()))
                 {
                     errors.rejectValue("emailAdd3", "error.member.invalid.emailAdd3","Kindly enter a valid email address for member");
                 } 
             }
            
            if(member.getEmailAdd1()!=null && member.getEmailAdd1().equals("") && member.getEmailAdd2()!=null && member.getEmailAdd2().equals("") && member.getEmailAdd3()!=null && member.getEmailAdd3().equals(""))
             {
                 if(member.getEmailAdd1().toLowerCase().matches(member.getEmailAdd2().toLowerCase()) && member.getEmailAdd2().toLowerCase().matches(member.getEmailAdd3().toLowerCase()))
                 {
                     errors.rejectValue("phoneNo1", "error.member.invalid.phoneNo1","You have provided same email address for all email records...kindly correct");
                 }
             }
            if(member.getNokPhone()!=null && member.getNokPhone().equals(""))
             {
                 if(!customutil.isValidPhone(member.getNokPhone()))
                 {
                     errors.rejectValue("nokPhone", "error.member.invalid.nokPhone","Kindly enter a valid phone number for next of kin");
                 } 
             }
            //TODO
            //Validate Stock Level and entries
        }
        
        //Lots of Todos
    }
    
}
