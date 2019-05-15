package com.sift.votes.dao;

import com.sift.votes.model.VotVoteresults;
import java.util.ArrayList;
import java.util.List;

public interface VoteDao {
         public boolean addVotes(ArrayList<VotVoteresults> votres,Integer agmid, Integer voterid);
         public boolean updatevoterconcluded(Integer agmid, Integer voterid);
}