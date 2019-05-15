/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.webservice.service;

import com.sift.webservice.dao.LoanRequestWSDao;
import com.sift.webservice.dao.UserWSDao;
import com.sift.webservice.model.UserWS;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("UserServiceWS")
@Transactional
public class UserImplWS implements UserServiceWS {
    
     @Autowired
    private UserWSDao userWSDao;
     
     @SuppressWarnings("unchecked")
    @Transactional
    public List<UserWS> getAllCoopMail(String companyid,String branchid,String accesslevel){
   return  userWSDao.getAllCoopMail(companyid, branchid,accesslevel);
  
  }
}
