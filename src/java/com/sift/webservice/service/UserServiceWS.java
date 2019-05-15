/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.webservice.service;

import com.sift.webservice.model.UserWS;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface UserServiceWS {
 public List<UserWS> getAllCoopMail(String companyid,String branchid,String accesslevel); 
    
}
