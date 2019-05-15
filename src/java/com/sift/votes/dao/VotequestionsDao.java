package com.sift.votes.dao;

import com.sift.votes.model.VotVoteoptions;
import java.util.List;
import com.sift.votes.model.VotVotequests;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface VotequestionsDao {
         public List<VotVotequests> listVotequestions(Integer agmid);
         public List<VotVoteoptions> listVoteoptions(Integer voteid);
	 public Integer returnNoVotequestionsbystatus(Integer agmid,String approvalstatus);
}