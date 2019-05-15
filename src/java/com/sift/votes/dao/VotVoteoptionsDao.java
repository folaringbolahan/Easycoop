/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotVoteoptions;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface VotVoteoptionsDao {
 public void save(VotVoteoptions votVoteoptions);
 public List<VotVoteoptions> listOptionsById(int id);
 public VotVoteoptions getVotVoteoptions(int id);
    
}
