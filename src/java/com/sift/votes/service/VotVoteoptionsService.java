/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.model.VotVoteoptions;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface VotVoteoptionsService {
  public void save(VotVoteoptions votVoteoptions);  
 public List<VotVoteoptions> listOptionsById(int id);
 public VotVoteoptions getVotVoteoptions(int id);
}
