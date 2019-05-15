/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotMemberMail;
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
@Repository("votMemberMailDao")
public class VotMemberMailDaoImpl implements VotMemberMailDao {
     @Autowired
 private SessionFactory sessionFactory;
     
   public void addVotMemberMail(VotMemberMail addDetails){
      sessionFactory.getCurrentSession().saveOrUpdate(addDetails);
     }   
 
  public  boolean checkVotMailExistence(String email){
  Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotMemberMail.class);
       criteria.add(Restrictions.eq("oldemail", email));
        List<VotMemberMail> listsize = criteria.list();
        System.out.println("list size for the mail "+ listsize.size());
        if(listsize.isEmpty()){
          return false;   
         }
        else{
            return true;
        }
  }
 

  
    public List<VotMemberMail> listVotMailChangesForApprv() {
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotMemberMail.class);
       criteria.add(Restrictions.eq("status", 0));
            return (List<VotMemberMail>) criteria.list();  
    }
    
}
