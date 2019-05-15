/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotVotequests;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface VotVotequestsDao  {
     public void save(VotVotequests votVotequests);  
    public VotVotequests getCurrentVotequestRecord();
    public VotVotequests getQuestionById(int id); 
    public List<VotVotequests> listVotequestionsByAgmId(int agmid);
}
