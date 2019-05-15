/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotElectionanswertypes;
import com.sift.votes.model.VotResolanswertypes;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("votElectionanswertypesDao")
public class VotElectionanswertypesDaoImpl implements VotElectionanswertypesDao {
   @Autowired  
 private SessionFactory sessionFactory; 
      
     public List<VotElectionanswertypes> listallanswertypes(){
      return (List<VotElectionanswertypes>) sessionFactory.getCurrentSession().createCriteria(VotElectionanswertypes.class).list();
     }
    
}
