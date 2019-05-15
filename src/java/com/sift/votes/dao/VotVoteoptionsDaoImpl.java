/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotVoteoptions;
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
@Repository("votVoteoptionsDao")
public class VotVoteoptionsDaoImpl implements VotVoteoptionsDao {
  @Autowired
  SessionFactory sessionFactory;
public void save(VotVoteoptions votVoteoptions){
    sessionFactory.getCurrentSession().saveOrUpdate(votVoteoptions);
}
public List<VotVoteoptions> listOptionsById(int id){
Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotVoteoptions.class);
       criteria.add(Restrictions.eq("voteid", id));
             return (List<VotVoteoptions>) criteria.list();
}
 public VotVoteoptions getVotVoteoptions(int id){  
    return (VotVoteoptions) sessionFactory.getCurrentSession().get(VotVoteoptions.class,id);  
 } 
    
}
