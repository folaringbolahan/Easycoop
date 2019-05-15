/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.service;

import com.sift.gl.GendataService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 *
 * @author yomi-pc
 */

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        String wbservname = "";
        String wbservpwd = "";
        String wbservrole = "";
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        String mySQLString;
        mySQLString = "select * FROM tblwebserv a where a.app = 'internal'"  ;
        ResultSet agRecSet;
        try 
         { 
        agRecSet = dbobj.retrieveRecset(mySQLString);
         while (agRecSet.next()) {
            wbservname = agRecSet.getString("user");
            wbservpwd = agRecSet.getString("pwd");
            wbservrole = agRecSet.getString("role");  
           // System.out.println("Have been called " + wbservname + wbservpwd );
         }
        }
       catch (SQLException ex) {
          System.out.println(ex.getMessage());
       } 
       finally {
           if (dbobj != null) {
            dbobj.closecon();
           }
        } 
        
        if (name.equals(wbservname) && password.equals(wbservpwd)) {
            List<GrantedAuthority> grantedAuths = new ArrayList();
           ////Collection<GrantedAuthority> grantedAuths = new SimpleGrantedAuthority(wbservrole);
            grantedAuths.add(new SimpleGrantedAuthority(wbservrole));
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
            return auth;
        } else {
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}