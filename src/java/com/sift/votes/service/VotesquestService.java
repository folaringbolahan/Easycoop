package com.sift.votes.service;

import java.util.ArrayList;
import java.util.List;

import com.sift.votes.model.Votpagesession;

/**
 * @author 
 */
public interface VotesquestService{
	
       public Votpagesession buildVotpagession(Integer dagmid,Integer usrnoofvoteunits);
    
}