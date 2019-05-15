/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotVotequestsDao;
import com.sift.votes.model.VotVotequests;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votVotequestsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotVotequestsServiceImpl implements VotVotequestsService {
    @Autowired
    private VotVotequestsDao votVotequestsDao;
    public void save(VotVotequests votVotequests){
    votVotequestsDao.save(votVotequests);
    }
     public VotVotequests getCurrentVotequestRecord(){
      return votVotequestsDao.getCurrentVotequestRecord();
     }
     
     public List<VotVotequests> listVotequestionsByAgmId(int agmid){
       return votVotequestsDao.listVotequestionsByAgmId(agmid);
      }
     
     public VotVotequests getQuestionById(int id){
      return votVotequestsDao.getQuestionById(id);
     }
    
}
