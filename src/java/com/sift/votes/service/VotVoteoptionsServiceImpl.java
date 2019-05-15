/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotVoteoptionsDao;
import com.sift.votes.model.VotVoteoptions;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votVoteoptionsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotVoteoptionsServiceImpl implements VotVoteoptionsService {
    @Autowired
    VotVoteoptionsDao votVoteoptionsDao;
 public void save(VotVoteoptions votVoteoptions){
   votVoteoptionsDao.save(votVoteoptions);
 }
 public List<VotVoteoptions> listOptionsById(int id){
   return votVoteoptionsDao.listOptionsById(id);
 }
 
  public VotVoteoptions getVotVoteoptions(int id){
  return votVoteoptionsDao.getVotVoteoptions(id);
  }
}
