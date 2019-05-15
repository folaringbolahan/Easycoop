package com.sift.votes.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sift.votes.model.VotVoteoptions;
import com.sift.votes.model.VotVotequests;
import org.hibernate.criterion.Order;

/**
 * @author 
 *
 */
@Repository("votequestionsDao")
public class VotequestionsDaoImpl implements VotequestionsDao{

 @Autowired
 private SessionFactory sessionFactory;
/*
 public void addBulkUpload(FileUpload fileUpload) {
   sessionFactory.getCurrentSession().saveOrUpdate(fileUpload);
 }
*/
 
 @SuppressWarnings("unchecked")
 public List<VotVotequests> listVotequestions(Integer agmid){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotVotequests.class);
	 criteria.add(Restrictions.eq("agmid",agmid));
         criteria.add(Restrictions.eq("deleted","N"));
         criteria.add(Restrictions.eq("active","Y"));
         criteria.addOrder(Order.asc("votetypeid"));
         criteria.addOrder(Order.asc("sortorder"));
	 return  (List<VotVotequests>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<VotVoteoptions> listVoteoptions(Integer voteid){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotVoteoptions.class);
	 criteria.add(Restrictions.eq("voteid",voteid));
         criteria.add(Restrictions.eq("deleted","N"));
	 return  (List<VotVoteoptions>)criteria.list();
 }
 
 public Integer returnNoVotequestionsbystatus(Integer agmid,String approvalstatus){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotVotequests.class);
	 criteria.add(Restrictions.eq("agmid",agmid));
         criteria.add(Restrictions.eq("deleted","N"));
         if (approvalstatus.equalsIgnoreCase("Y")||approvalstatus.equalsIgnoreCase("N")) 
         {
           criteria.add(Restrictions.eq("active",approvalstatus));
         }  
         Integer noofvotes = criteria.list().size();
         return noofvotes;
 }
}