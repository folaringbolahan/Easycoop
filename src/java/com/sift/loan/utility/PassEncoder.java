package com.sift.loan.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PassEncoder{
	 public PassEncoder(){}
	 
     public String doEndcodePass(String  password){
    	    BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
    		String hashedPassword = passEncoder.encode(password);    	
    		
    		return hashedPassword;    	 
     }
     
     public boolean doMatchPass(String  password,String encodedPass){
 	    BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
	
 		return passEncoder.matches(password, encodedPass);    	 
     }
     
     public static void main(String[] args){    	 
    	 String paswd=new PassEncoder().doEndcodePass("Ab123456");
    	 String paswd2=new PassEncoder().doEndcodePass("Abc@1234");
    	 boolean isMatch=new PassEncoder().doMatchPass("Abc@1234",paswd);
    	 boolean isMatch2=new PassEncoder().doMatchPass("Abc@1234",paswd2);
    	 
    	 System.out.println("Pass/Lenght:=" + paswd + "/" + paswd.length());   
    	 System.out.println("Pass2/Lenght2:=" + paswd2 + "/" + paswd2.length()); 
    	 System.out.println("Match1:=" + isMatch);
    	 System.out.println("Match2:=" + isMatch2);
     }
}