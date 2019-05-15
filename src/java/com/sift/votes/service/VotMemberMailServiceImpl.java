/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotMemberMailDao;
import com.sift.votes.model.VotMemberMail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votMemberMailService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotMemberMailServiceImpl implements VotMemberMailService {
    
     @Autowired
     private VotMemberMailDao votmembermailDao;
     
  public void addVotMemberMail(VotMemberMail addDetails){
      votmembermailDao.addVotMemberMail(addDetails);
  }  
  public List<VotMemberMail> listVotMailChangesForApprv(){
      return votmembermailDao.listVotMailChangesForApprv();
  }
  public boolean checkVotMailExistence(String email){
     return votmembermailDao.checkVotMailExistence(email);
  }   
}
