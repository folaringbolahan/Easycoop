/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.validator;

import com.sift.financial.member.Member;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author baydel200
 */
public class MemberDiscValidator implements Validator { 

    @Override
    public boolean supports(Class<?> type) {
       return type.equals(Member.class); 
      
    }

    @Override
    public void validate(Object o, Errors errors) {
        
        //need to check if memeber has loan/purchases that are pending when existting
        //muts prompt for dissolution befor exiting
        //can exist for suspension
        
         Member member = (Member)o;
        
        if(member.getAction().equals("SUSPEND"))
        {
            
            
            
        }
        else  if(member.getAction().equals("EXIT"))
        {
        
        
            
            
        }
        else  if(member.getAction().equals("APPROVE SUSPENSION"))
        {
        
        
        }
        else  if(member.getAction().equals("APPROVE EXIT"))
        {
        
        
            
            
        }
        else  if(member.getAction().equals("REINSTATE"))
        {
        
        
            
            
            
            
            
        }
        
        
    }
    
    
}
