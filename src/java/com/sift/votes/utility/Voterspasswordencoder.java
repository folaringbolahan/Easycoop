/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.utility;

//import com.sun.jersey.core.util.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.codec.Base64;
//import org.apache.catalina.util.Base64;

/**
 *
 * @author kola
 */
public class Voterspasswordencoder {
    
   
    
  public String encodePass(String  password){
    	    BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
    	    String hashedPassword = passEncoder.encode(password);    	
            return hashedPassword;    	 
     }
     
     public boolean matchPass(String  password,String encodedPass){
 	    BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
	    return passEncoder.matches(password, encodedPass);    	 
     }
     
}
