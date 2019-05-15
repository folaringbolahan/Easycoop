/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotVotequests;
import com.sift.votes.model.VotCompany;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("votVotequestsDao")
public class VotVotequestsDaoImpl implements VotVotequestsDao{
 @Autowired  
 private SessionFactory sessionFactory; 
     
       public void save(VotVotequests votVotequests) {   
        
     sessionFactory.getCurrentSession().saveOrUpdate(votVotequests);
        }
       
    public VotVotequests getCurrentVotequestRecord(){ 
   Query query = sessionFactory.getCurrentSession().createQuery("from VotVotequests order by id DESC");
    query.setMaxResults(1);
   VotVotequests current = (VotVotequests) query.uniqueResult();  
   return current;
 }  
  public List<VotVotequests> listVotequestionsByAgmId(int agmid){
   Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotVotequests.class);
       criteria.add(Restrictions.eq("active", "N"));
        criteria.add(Restrictions.eq("deleted", "N"));
         criteria.add(Restrictions.eq("agmid", agmid));
            return (List<VotVotequests>) criteria.list();
            
  }
public VotVotequests getQuestionById(int id){
 return (VotVotequests) sessionFactory.getCurrentSession().get(VotVotequests.class,id);  
}
}
