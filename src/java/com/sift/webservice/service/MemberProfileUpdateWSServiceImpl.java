/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.webservice.service;

import com.sift.webservice.dao.MemberProfileUpdateWSDao;
import com.sift.webservice.model.MemberProfileUpdateWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("MemberProfileUpdateWSService")
@Transactional
public class MemberProfileUpdateWSServiceImpl implements MemberProfileUpdateWSService {
     @Autowired
    private MemberProfileUpdateWSDao memberProfileUpdateWSDao;
     
    public void saveMemberUpdate(MemberProfileUpdateWS memberProfileUpdateWS){
       memberProfileUpdateWSDao.saveMemberUpdate(memberProfileUpdateWS);
             }
    
}
