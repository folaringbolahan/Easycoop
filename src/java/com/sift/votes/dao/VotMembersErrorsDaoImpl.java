/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotFileUpload;
import com.sift.votes.model.VotMembers;
import com.sift.votes.model.VotMembersErrors;
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
@Repository("votMembersErrorsDao")
public class VotMembersErrorsDaoImpl implements  VotMembersErrorsDao {
    @Autowired  
 private SessionFactory sessionFactory; 
    
    @Override
    public void save(VotMembersErrors votMembersErrors) {   
         System.out.println("...saving vomemberserrors at this point");
     sessionFactory.getCurrentSession().saveOrUpdate(votMembersErrors);
        } 

    public List<VotMembersErrors> listAllVotMembersErrorsBybatch(int agmId,String referenceNumber) {
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotMembersErrors.class);
       criteria.add(Restrictions.eq("agmid", agmId));
        criteria.add(Restrictions.eq("referenceid", referenceNumber));
        //System.out.println("i am inside the list method agm " + agmId + " ref : " + referenceNumber);
         List<VotMembersErrors> dcriterialist = (List<VotMembersErrors>) criteria.list();
         //  System.out.println("i am inside the list method 3 size = " + dcriterialist.size());
         return dcriterialist;
            //return (List<VotFileUpload>) criteria.list();
    }
    public boolean votMembersErrorsexist(int agmId,String referenceNumber){
        boolean errorsexist = false;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotMembersErrors.class);
        criteria.add(Restrictions.eq("agmid", agmId));
        criteria.add(Restrictions.eq("referenceid", referenceNumber));
        List<VotMembersErrors> dcriterialist = (List<VotMembersErrors>) criteria.list();
         if (dcriterialist.size()>0) {
           errorsexist = true; 
        }
         
         System.out.println("i am inside the votMembersErrorsexist size = " + dcriterialist.size() + " agmid " + agmId + " referenceNumber " + referenceNumber  + " errorsexist " + errorsexist);
       
        return errorsexist;
    }
    
    public List<VotMembersErrors> listAllVotMembersErrorsBybatch(String referenceNumber) {
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotMembersErrors.class);
       criteria.add(Restrictions.eq("referenceid", referenceNumber));
       List<VotMembersErrors> dcriterialist = (List<VotMembersErrors>) criteria.list();
       return dcriterialist;
     }
}
