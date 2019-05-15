/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotVotetypesDao;
import com.sift.votes.model.VotVotetypes;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votVotetypesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotVotetypesServiceImpl implements VotVotetypesService {
     @Autowired
  private VotVotetypesDao votVotetypesDao;
     
     public List<VotVotetypes> listallvotetypes(){
        return votVotetypesDao.listallvotetypes();
     }
   public VotVotetypes getVoteTypeByid(int votetypeid){
   return votVotetypesDao.getVoteTypeByid(votetypeid);
   }
}
