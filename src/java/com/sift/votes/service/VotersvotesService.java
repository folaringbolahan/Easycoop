/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.model.VotVoteoptions;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.task.TaskExecutor;

/**
 *
 * @author kola
 */

public interface VotersvotesService {
    
    // first method
    // run thru the database and pick all members whoise agmid tally with the one passed as parameter and do 
    // left join with cred table - if null in cred generate password and store details in  cred
    
    // this should run in controller or another service class when this is completed - spin another call yo firstnotification
    // service and 
    public boolean addvotes(Integer agmid,Integer coyid, Integer voterid,Integer unitsofvotes,HttpServletRequest request);
    public boolean addvotessv(Integer agmid,Integer coyid, Integer voterid,Integer unitsofvotes,HttpServletRequest request);
}
