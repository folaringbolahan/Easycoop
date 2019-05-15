/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotBallottypes;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("votBallottypesDao")
public class VotBallottypesDaoImpl implements VotBallottypesDao {
    
    @Autowired  
 private SessionFactory sessionFactory;  
    
  public List<VotBallottypes> listBallotypesDistinct(String ballotid){

	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotBallottypes.class);
	 criteria.add(Restrictions.eq("id",Integer.parseInt(ballotid)));
	 
	 return  (List<VotBallottypes>)criteria.list();
  
  } 
  
  @SuppressWarnings("unchecked")  
 public List<VotBallottypes> listBallotypes(){  
    return (List<VotBallottypes>) sessionFactory.getCurrentSession().createCriteria(VotBallottypes.class).list();  
 }  
}
