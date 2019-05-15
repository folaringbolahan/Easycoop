/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.services;

import com.sift.financial.member.MemberProfileUpdate;
import com.sift.financial.member.MemberProfileUpdateDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("MemberProfileUpdateService")
@Transactional
public class MemberProfileUpdateServiceImpl implements  MemberProfileUpdateService {
    
     @Autowired
     private MemberProfileUpdateDao memberProfileUpdateDao;
     
   public List<MemberProfileUpdate> listMemberProfileUpdate(int branchid,int companyid){
    return memberProfileUpdateDao.listMemberProfileUpdate(branchid, companyid);
   } 
     public MemberProfileUpdate getPendingProfilebyId(int id){
        return memberProfileUpdateDao.getPendingProfilebyId(id);
     }
}
