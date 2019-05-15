/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotMemberMail;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface VotMemberMailDao {
  public void addVotMemberMail(VotMemberMail addDetails);   
  public List<VotMemberMail> listVotMailChangesForApprv();
  public boolean checkVotMailExistence(String email);
    
}
