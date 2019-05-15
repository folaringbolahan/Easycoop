/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotElectionanswertypesDao;
import com.sift.votes.model.VotElectionanswertypes;
import com.sift.votes.model.VotResolanswertypes;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votElectionanswertypesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotElectionanswertypesServiceImpl implements VotElectionanswertypesService {
    @Autowired
   private VotElectionanswertypesDao  votElectionanswertypesDao;
    
     public List<VotElectionanswertypes> listallanswertypes(){
     return votElectionanswertypesDao.listallanswertypes();
     }
    
}
