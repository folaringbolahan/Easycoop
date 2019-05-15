/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
/**
 *
 * @author kola
 */
public class VoterCustomAuthenticationToken extends UsernamePasswordAuthenticationToken{
    private String domainid;

    public VoterCustomAuthenticationToken(Object principal, Object credentials, String domainid) {
        super(principal, credentials);
        this.domainid = domainid;
        super.setAuthenticated(false);
    }

    public VoterCustomAuthenticationToken(Object principal, Object credentials, String domainid, 
        Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.domainid = domainid;
        super.setAuthenticated(true); // must use super, as we override
    }

    public String getDomainid() {
        return this.domainid;
    }
}

