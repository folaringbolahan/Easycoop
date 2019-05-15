/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author kola
 */
public class VoterCustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    String namedomainseperator = ":::::::";
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)  throws AuthenticationException {
       // System.out.println("filtercalled ");
        UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
    
    private UsernamePasswordAuthenticationToken getAuthRequest(
      HttpServletRequest request) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String domainid = request.getParameter("refid");
        //System.out.println("username " + username);
        //System.out.println(" password " +  password);
        //System.out.println(" domainid " +  domainid);
        String usernameDomain = username.trim() + namedomainseperator + domainid;//String.format("%s%s%s", username.trim(),String.valueOf(Character.LINE_SEPARATOR), domainid);
        //System.out.println("usernameDomain " + usernameDomain);
        return new UsernamePasswordAuthenticationToken(usernameDomain, password);
        
        
        //return new VoterCustomAuthenticationToken(username, password, domainid);
    }
    
}
