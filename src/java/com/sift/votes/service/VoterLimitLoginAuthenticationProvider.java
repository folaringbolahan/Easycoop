/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VoterUserattemptdetailsdao;
import com.sift.gl.model.Userattempts;
import com.sift.votes.model.SaltedUser;
import com.sift.votes.utility.Voterspasswordencoder;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author yomi-pc
 */

//@Component
public class VoterLimitLoginAuthenticationProvider  extends DaoAuthenticationProvider  {
    //@Autowired
    VoterUserattemptdetailsdao vuserAttemptsDao;//   = new Userattemptdetailsdao();
    @Autowired
    VoterCustomUserDetailsService votuserDetailsService;
    //@Autowired
   /// private HttpServletRequest request;
    //UserDetailsService udetserv;
    ///@Autowired
	////@Qualifier("userDetailsService")
	///@Override
	///public void setUserDetailsService(UserDetailsService userDetailsService) {
	////	super.setUserDetailsService(userDetailsService);
	/////}
     private Integer memberid;
     private Integer refid;
     String namedomainseperator = ":::::::";
       public VoterUserattemptdetailsdao getVuserAttemptsDao() {
		return vuserAttemptsDao;
	}

	public void setVuserAttemptsDao(VoterUserattemptdetailsdao vuserAttemptsDao) {
		this.vuserAttemptsDao = vuserAttemptsDao;
	} 
    public VoterCustomUserDetailsService getVotuserDetailsService() {
		return votuserDetailsService;
	}

	public void setVotuserDetailsService(VoterCustomUserDetailsService votuserDetailsService) {
		this.votuserDetailsService = votuserDetailsService;
	} 
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        try {
            ///get and set if user qualifies to be set as credentialexpired or password expired before calling authentication class
            
            String username1 = "";
            int dagmid = 0;
            String[] usernameAndDomain = StringUtils.split(authentication.getName(),namedomainseperator); //String.valueOf(Character.LINE_SEPARATOR));
            if (usernameAndDomain.length == 2) {
                  username1 =usernameAndDomain[0];
                  try {
                     dagmid = Integer.parseInt(usernameAndDomain[1]);
                  }
                  catch(NumberFormatException nuex)
                  {
                      System.out.println("Invalid reference " + nuex.getMessage());
                  }    
            }
            else
            {
                username1 = authentication.getName();
            }    
            
            
            vuserAttemptsDao.loadsettingsandsetuser(username1,dagmid);
            
          ///  Integer refid = 0;
         ///   System.out.println("request.getP " + request.getParameter("refid") + " " + request.getRequestURL() + " " + request.getParameterMap().size() + "- "+ request.getHeader("refid"));
         ///   refid = Integer.parseInt(request.getParameter("refid"));
        ///    votuserDetailsService.setRefid(refid);
           /*        
            Object salt = null; 
 
            if (this.getSaltSource() != null) { 
              salt = this.getSaltSource().getSalt(getUserDetailsService().loadUserByUsername(authentication.getName())); 
            } 
            
            Voterspasswordencoder custpwdencore = (Voterspasswordencoder)this.getPasswordEncoder();
            custpwdencore.setSalt((String)salt);
            this.setPasswordEncoder(custpwdencore);
            */
            System.out.println("called1 " );
            Authentication auth = super.authenticate(authentication);
            
            //if corrent password, reset the user_attempts
            ///userAttemptsDao.resetFailAttempts(authentication.getName());
          //  System.out.println("voter authentication called");
          //  System.out.println("voter authenticated ? " + auth.isAuthenticated());
            this.memberid = votuserDetailsService.getMemberid();
            this.refid = votuserDetailsService.getRefid();
             System.out.println("called2 "  + this.memberid + " " + this.refid);
          // System.out.println("this.memberid " + this.memberid);
            return auth;
        } catch (BadCredentialsException e) {
            //invalid login, update user_attempts, set attempts+1 
            ////userAttemptsDao.updateFailAttempts(authentication.getName());
            throw e;

        } catch (LockedException e) {
            //this user is locked!
            String error = "";
            
            String username1 = "";
            String[] usernameAndDomain = StringUtils.split(authentication.getName(),namedomainseperator); //String.valueOf(Character.LINE_SEPARATOR));
            if (usernameAndDomain.length == 2) {
                  username1 =usernameAndDomain[0];
            }
            else
            {
                username1 = authentication.getName();
            }   
            
            Userattempts userAttempts = vuserAttemptsDao.getUserAttempts(username1);
            if (userAttempts != null) {
                error = "User account is locked! <br><br>Username : " + username1;
            } else {
                error = e.getMessage();
            }
            throw new LockedException(error);
        }

    }
    
     public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }
     public Integer getRefid() {
        return refid;
    }

    public void setRefid(Integer refid) {
        this.refid = refid;
    }
}
