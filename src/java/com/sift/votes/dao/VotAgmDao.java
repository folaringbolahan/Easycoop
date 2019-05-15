/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotAgm;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface VotAgmDao {
  public void addAgmSetup(VotAgm addDetails);   
  public List<VotAgm> listEasycoopAgm();
   public List<VotAgm> listExternalAgm();
   public List<VotAgm> listAllAgm();
   public VotAgm getAgmdetails(Integer agmid); 
   public List<VotAgm> listInactiveAgms();
   public List<VotAgm> listActiveAgms();
   public VotAgm getAgmById(int agmid);
   public List<VotAgm> listAllAgms();
   public List<VotAgm> listClosedAgms();
   public boolean updateReminderdate(Integer agmid);
   public List<VotAgm> listActiveExternalAgm();
   public List<VotAgm> listActiveEasycoopAgm();

}
