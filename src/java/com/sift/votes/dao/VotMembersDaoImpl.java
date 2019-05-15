/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotMembers;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("votMembersDao")
public class VotMembersDaoImpl implements  VotMembersDao {
    
      @Autowired  
 private SessionFactory sessionFactory; 
      private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VotMembersDaoImpl.class);
       public void save(VotMembers votmembers) {   
         System.out.println("...saving vomembers at this point");
     sessionFactory.getCurrentSession().saveOrUpdate(votmembers);
        } 
    public List<VotMembers > listApprovedAgmMembers(int agmid){  
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotMembers .class);
       criteria.add(Restrictions.eq("concluded", "N"));
       criteria.add(Restrictions.eq("agmid", agmid));
       return (List<VotMembers >) criteria.list();
 }  
    public boolean getMemberValidity(String email,int agmid){
         
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotMembers .class);
       criteria.add(Restrictions.eq("email", email));
      criteria.add(Restrictions.eq("agmid", agmid));
    
            
          int list= criteria.list().size();
          System.out.println("the list size for member validity "+ list);
       if(list==0){
             return false;
             }
       else {
              return true;
             }
       
      }
         
  public List<VotMembers > listAgmMemberDetailsByAgmid(int agmid){  
     Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotMembers .class);
       criteria.add(Restrictions.eq("agmid", agmid));
            return (List<VotMembers >) criteria.list();
 }  
   public VotMembers getVotMemberDetails(int id){
  return (VotMembers) sessionFactory.getCurrentSession().get(VotMembers.class, id);
 }
}
