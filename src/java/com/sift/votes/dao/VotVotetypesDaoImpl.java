/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotVotetypes;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("votVotetypesDao")
public class VotVotetypesDaoImpl implements VotVotetypesDao {
     @Autowired  
 private SessionFactory sessionFactory; 
     
  @SuppressWarnings("unchecked") 
  public List<VotVotetypes> listallvotetypes(){
    return (List<VotVotetypes>) sessionFactory.getCurrentSession().createCriteria(VotVotetypes.class).list();  
 
  }
  public VotVotetypes getVoteTypeByid (int votetypeid) {
   return (VotVotetypes) sessionFactory.getCurrentSession().get(VotVotetypes.class,votetypeid); 
  }  
}
