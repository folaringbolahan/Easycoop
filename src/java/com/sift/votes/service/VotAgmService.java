/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.model.VotAgm;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface VotAgmService {
 public void addAgmSetup(VotAgm votAgm);
  public List<VotAgm> listExternalAgm();
  public List<VotAgm> listEasycoopAgm();
  public List<VotAgm> listAllAgm();
  public VotAgm getAgmdetails(Integer agmid);
  public List<VotAgm> listInactiveAgms();
  public List<VotAgm> listActiveAgms();
  public VotAgm getAgmById(int agmid);
  public List<VotAgm> listAllAgms();
  public List<VotAgm> listClosedAgms();
  public List<VotAgm> listActiveExternalAgm();
  public List<VotAgm> listActiveEasycoopAgm();

}
