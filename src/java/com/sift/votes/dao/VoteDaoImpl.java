package com.sift.votes.dao;

import com.sift.votes.model.VotAgm;
import com.sift.votes.model.VotVoteresults;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 
 *
 */
@Repository("voteDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VoteDaoImpl implements VoteDao{

 @Autowired
 private SessionFactory sessionFactory;

 public boolean addVotes(ArrayList<VotVoteresults> lvotres,Integer agmid, Integer voterid) {
   org.hibernate.classic.Session session=null;
	 Transaction tx=null;  
	 boolean success=false;
	try{
		    session = sessionFactory.getCurrentSession();
                    if(lvotres!=null && lvotres.size()>0){
			    for(VotVoteresults item: lvotres){
				   session.saveOrUpdate(item); 
			    }
                       updatevoterconcluded(agmid,  voterid);     
		    }
                    //session.saveOrUpdate(votres);		   
		    success=true;
	}catch(HibernateException e){
		try{
			if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		success=false;
		throw e;
	}catch(RuntimeException e){
		try{
			if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		success=false;
		throw e;
	}finally{
		  tx=null;
	}	 
	return success;
 }
 
 public boolean updatevoterconcluded(Integer agmid, Integer voterid){
     boolean success = false;
     try {
		sessionFactory.getCurrentSession().createSQLQuery("update Vot_Creds set locked=1,accountNonExpired=0, accountNonLocked=0, credentialsNonExpired=0 where agmid = "+ agmid + " and memberid = " + voterid).executeUpdate();
		sessionFactory.getCurrentSession().createSQLQuery("update Vot_Members set concluded='Y' where agmid = "+ agmid + " and memberid = " + voterid).executeUpdate();
		success=true;
	} catch (HibernateException e) {
		success=false;
		e.printStackTrace();
	}
     
     return success;
 }

 
}