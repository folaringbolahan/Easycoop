/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.service;

import com.sift.gl.dao.Userattemptdetailsdao;
import com.sift.gl.model.Userattempts;
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

/**
 *
 * @author yomi-pc
 */

//@Component
public class LimitLoginAuthenticationProvider  extends DaoAuthenticationProvider  {
    //@Autowired
    Userattemptdetailsdao userAttemptsDao;//   = new Userattemptdetailsdao();
    //UserDetailsService udetserv;
    ///@Autowired
	////@Qualifier("userDetailsService")
	///@Override
	///public void setUserDetailsService(UserDetailsService userDetailsService) {
	////	super.setUserDetailsService(userDetailsService);
	/////}
    
       public Userattemptdetailsdao getUserAttemptsDao() {
		return userAttemptsDao;
	}

	public void setUserAttemptsDao(Userattemptdetailsdao userAttemptsDao) {
		this.userAttemptsDao = userAttemptsDao;
	} 
    
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        try {
            ///get and set if user qualifies to be set as credentialexpired or password expired before calling authentication class
            
            userAttemptsDao.loadsettingsandsetuser(authentication.getName());
            ///
            Authentication auth = super.authenticate(authentication);
            
            //if corrent password, reset the user_attempts
            userAttemptsDao.resetFailAttempts(authentication.getName());
            return auth;
        } catch (BadCredentialsException e) {
            //invalid login, update user_attempts, set attempts+1 
            userAttemptsDao.updateFailAttempts(authentication.getName());
            throw e;

        } catch (LockedException e) {
            //this user is locked!
            String error = "";
            Userattempts userAttempts = userAttemptsDao.getUserAttempts(authentication.getName());
            if (userAttempts != null) {
                error = "User account is locked! <br><br>Username : " + authentication.getName();
            } else {
                error = e.getMessage();
            }
            throw new LockedException(error);
        }

    }
}
