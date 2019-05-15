/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotFileUpload;
import com.sift.votes.model.VotFileUploadCriteria;
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
@Repository("votFileUploadDao")
public class VotFileUploadDaoImpl implements VotFileUploadDao {
      @Autowired  
 private SessionFactory sessionFactory; 
      private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VotFileUploadDaoImpl.class);
     
    public void save(VotFileUpload votfileUpload) {
        
     sessionFactory.getCurrentSession().saveOrUpdate(votfileUpload);
        } 
    

   
    
    public List<VotFileUpload> listAllVotFileUploadByCriteria(int companyId,String referenceNumber) {
        System.out.println("i am inside the list method");
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotFileUpload.class);
       criteria.add(Restrictions.eq("companyId", companyId));
        criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
            return (List<VotFileUpload>) criteria.list();
    }
    public List<VotFileUpload> listAllVotFileUploadByStatus(int status) {
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotFileUpload.class);
       criteria.add(Restrictions.eq("status", status));
       
            return (List<VotFileUpload>) criteria.list();
    }
    
     @Override
    public List<VotFileUpload> listAllFileUploadByCriteria(VotFileUploadCriteria votfileUpload) {
        return (List<VotFileUpload>) sessionFactory.getCurrentSession().createCriteria(VotFileUpload.class).list();  
    }
     
     public List<VotFileUpload> listAllVotFileUploadByAgmCriteria(int agmId,String referenceNumber) {
       // System.out.println("i am inside the list method");
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotFileUpload.class);
       criteria.add(Restrictions.eq("branchId", agmId));
        criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
        //System.out.println("i am inside the list method agm " + agmId + " ref : " + referenceNumber);
         List<VotFileUpload> dcriterialist = (List<VotFileUpload>) criteria.list();
         //  System.out.println("i am inside the list method 3 size = " + dcriterialist.size());
         return dcriterialist;
            //return (List<VotFileUpload>) criteria.list();
    }
}
