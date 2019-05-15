/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.services;

import com.sift.financial.member.MemberEmailChange;
import com.sift.financial.member.MemberEmailChangeDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("")
@Transactional
public class MemberEmailChangeServiceImpl implements MemberEmailChangeService  {
     @Autowired
     private MemberEmailChangeDao memberEmailChangeDao;
   public void addEmail(MemberEmailChange memberEmailChange){
     memberEmailChangeDao.addEmail(memberEmailChange);
   } 
    public List<MemberEmailChange> listEmailChanges(){
    return  memberEmailChangeDao.listEmailChanges();
    }
    public MemberEmailChange getPendingEmailChangesbyId (int id){
    return  memberEmailChangeDao.getPendingEmailChangesbyId(id);
    }
}
