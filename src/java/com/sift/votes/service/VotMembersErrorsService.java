/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.model.VotMembersErrors;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface VotMembersErrorsService {
  public void save(VotMembersErrors votMembersErrors);
  public List<VotMembersErrors> listAllVotMembersErrorsBybatch(int agmId,String referenceNumber);
  public boolean votMembersErrorsexist(int agmId,String referenceNumber);
  public List<VotMembersErrors> listAllVotMembersErrorsBybatch(String referenceNumber);
}
