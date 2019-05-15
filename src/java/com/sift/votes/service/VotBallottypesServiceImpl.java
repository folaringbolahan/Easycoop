/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotBallottypesDao;
import com.sift.votes.model.VotBallottypes;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votBallottypesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotBallottypesServiceImpl implements VotBallottypesService {
    @Autowired
  private VotBallottypesDao votBallottypesDao;
     public List<VotBallottypes> listBallotypesDistinct(String ballotid){
		 return votBallottypesDao.listBallotypesDistinct(ballotid);
	 }
   public List<VotBallottypes>listBallotypes(){
     return votBallottypesDao.listBallotypes();
   } 
}
