package com.sift.votes.dao;

import com.sift.loan.model.LoanRequest;
import com.sift.votes.model.VotAgm;
import com.sift.votes.model.VotCreds;
import com.sift.votes.model.VotMembers;
import java.text.SimpleDateFormat;
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
@Repository("votecredDao")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotecredDaoImpl implements VotecredDao{

 @Autowired
 private SessionFactory sessionFactory;

 public boolean addCred(VotCreds cred) {
   org.hibernate.classic.Session session=null;
	 Transaction tx=null;  
	 boolean success=false;
	try{
		    session = sessionFactory.getCurrentSession();		    
		    session.saveOrUpdate(cred);		   
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


 
  public List<Object[]> getMembersofagmwithoutcred(Integer agmid){
	  int row=0;
 	  Query query = sessionFactory.getCurrentSession().createSQLQuery("select m.memberid,m.email,m.companyid,m.agmid,m.firstname,m.middlename,m.surname,a.description,a.startdate,a.starttime,a.enddate,a.endtime from vot_members m inner join vot_agm a on m.agmid = a.id left join vot_creds c on m.agmid = c.agmid and m.companyid = c.companyid and m.memberid = c.memberid where c.memberid is null and m.agmid=:dagmid ");  
        // Query query = sessionFactory.getCurrentSession().createQuery("from VotMembers m ,VotCreds c where m.agmid = :dagmid and c.memberid is null ");  
 	 query.setParameter("dagmid", agmid);
         // for(Iterator it=query.iterate();it.hasNext();){  
 	  //   row = Integer.valueOf(String.valueOf(it.next()));  
 	 // }  
          
          List<Object[]> list = query.list();
	  // for(Object[] arr : list){
	//	System.out.println(Arrays.toString(arr));
	 //  }
 	
 	  return list;
 }
  
 public List<Object[]> getMembersofagmnotvoted(Integer agmid){
	  int row=0;
 	  Query query = sessionFactory.getCurrentSession().createSQLQuery("select m.memberid,m.email,m.companyid,m.agmid,m.firstname,m.middlename,m.surname,a.description,a.startdate,a.starttime,a.enddate,a.endtime from vot_members m inner join vot_agm a on m.agmid = a.id inner join vot_creds c on m.agmid = c.agmid and m.companyid = c.companyid and m.memberid = c.memberid where m.concluded = 'N' and m.agmid=:dagmid  and ( curdate() BETWEEN TIMESTAMP(a.startdate, a.starttime)  AND TIMESTAMP(a.enddate, a.endtime) ) and a.closed = 'N'");  
          query.setParameter("dagmid", agmid);
          List<Object[]> list = query.list();
	  return list;
 } 
 
 public boolean updatemailedCred(Integer agmid, Integer memberid){
	boolean success=false;
	try {
		sessionFactory.getCurrentSession().createSQLQuery("update Vot_Creds set sent='Y' where agmid = "+ agmid + " and memberid = " + memberid).executeUpdate();
		success=true;
	} catch (HibernateException e) {
		success=false;
		e.printStackTrace();
	}
	return success;
 }
 
 @SuppressWarnings("unchecked")
 public List<VotAgm> listActiveAgm(){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotAgm.class);
	 criteria.add(Restrictions.eq("closed","N"));
         // now > last update date _ frequencydays
         //Calendar calendar = Calendar.getInstance();
         //Date rightnow = calendar.getTime();
         //Date currentDate = new Date();
        
         //calendar.setTime(currentDate);
         //calendar.add(Calendar.DATE, 1);
         String dateaddbuild = "CURRENT_DATE() >= (DATE_ADD(lastreminderdate,INTERVAL reminderfrequency DAY))";
         criteria.add(Restrictions.sqlRestriction(dateaddbuild));
         //rightnow.se
         //criteria.add(Restrictions.le("lastreminderdate","N"));
	 return  (List<VotAgm>)criteria.list();	 
 }
 
 
 public List<Object[]> getMemberbyemailofagmnotvoted(String membermail){
	  int row=0;
 	  Query query = sessionFactory.getCurrentSession().createSQLQuery("select m.memberid,m.email,m.companyid,m.agmid,m.firstname,m.middlename,m.surname,a.description,a.startdate,a.starttime,a.enddate,a.endtime from vot_members m inner join vot_agm a on m.agmid = a.id left join vot_creds c on m.agmid = c.agmid and m.companyid = c.companyid and m.memberid = c.memberid where m.concluded = 'N' and m.email=:dmembermail and a.closed = 'N'");  
          query.setParameter("dmembermail", membermail);
          List<Object[]> list = query.list();
	  return list;
 }
 
 public List<Object[]> getMemberbyemailofagmnotvoted(Integer memberid){
	  int row=0;
 	  Query query = sessionFactory.getCurrentSession().createSQLQuery("select m.memberid,m.email,m.companyid,m.agmid,m.firstname,m.middlename,m.surname,a.description,a.startdate,a.starttime,a.enddate,a.endtime from vot_members m inner join vot_agm a on m.agmid = a.id left join vot_creds c on m.agmid = c.agmid and m.companyid = c.companyid and m.memberid = c.memberid where m.concluded = 'N' and m.memberid =:dmemberid and a.closed = 'N'");  
          query.setParameter("dmemberid", memberid);
          List<Object[]> list = query.list();
          /////
          System.out.println("check 4");
	  return list;
 }
 
 
 public boolean updateCred(VotCreds votcred){
	boolean success=false;
	try {
            String tempDate = null;
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
            tempDate = formatter1.format(votcred.getCreatedate());
           /////
          //System.out.println("check 5");
		sessionFactory.getCurrentSession().createSQLQuery("update Vot_Creds set tokencrpy='" + votcred.getTokencrpy() + "',deleted='N',sent='N',locked=0,enabled=1,accountnonexpired=1,accountnonlocked=1,credentialsnonexpired=1,lastmodified={t '" + tempDate + "'} where agmid = "+ votcred.getAgmid() + " and membermail = '" + votcred.getMembermail() + "'").executeUpdate();
		success=true;
	} catch (HibernateException e) {
		success=false;
		e.printStackTrace();
	}
	return success;
 }
}