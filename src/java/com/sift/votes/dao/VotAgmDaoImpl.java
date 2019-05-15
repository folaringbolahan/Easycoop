/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotAgm;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("votAgmDao")
public class VotAgmDaoImpl implements VotAgmDao {
    @Autowired
 private SessionFactory sessionFactory;
    
  public void addAgmSetup(VotAgm votAgm){
   sessionFactory.getCurrentSession().saveOrUpdate(votAgm);
 }  
 public List<VotAgm> listEasycoopAgm(){  
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotAgm.class);
       criteria.add(Restrictions.eq("importsource", "I"));
            return (List<VotAgm>) criteria.list();
 }   
  public List<VotAgm> listExternalAgm(){  
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotAgm.class);
       criteria.add(Restrictions.eq("importsource", "E"));
            return (List<VotAgm>) criteria.list();
 }   
 public List<VotAgm> listAllAgm(){  
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotAgm.class);
      return (List<VotAgm>) criteria.list();
 } 
 public VotAgm getAgmdetails(Integer agmid){  
       return (VotAgm) sessionFactory.getCurrentSession().get(VotAgm.class,agmid);
 } 
 
 public List<VotAgm> listInactiveAgms(){
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotAgm.class);
    criteria.add(Restrictions.eq("active", "N"));
    return (List<VotAgm>) criteria.list();
}
public List<VotAgm> listActiveAgms(){
   Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotAgm.class);
       criteria.add(Restrictions.eq("closed", "N"));
            return (List<VotAgm>) criteria.list();
  }
 public List<VotAgm> listAllAgms(){
    return (List<VotAgm>) sessionFactory.getCurrentSession().createCriteria(VotAgm.class).list();  
  }
 public VotAgm getAgmById(int agmid){
  return (VotAgm) sessionFactory.getCurrentSession().get(VotAgm.class,agmid);  
 }
 
 public List<VotAgm> listClosedAgms(){
 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotAgm.class);
       criteria.add(Restrictions.eq("closed", "N"));
            return (List<VotAgm>) criteria.list();
 } 
 public boolean updateReminderdate(Integer agmid){
	boolean success=false;
	try {
            String tempDate = null;
            Calendar rightNow = Calendar.getInstance();
            java.util.Date entrydate = rightNow.getTime();
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
            tempDate = formatter1.format(entrydate);
		sessionFactory.getCurrentSession().createSQLQuery("update Vot_Agm set lastreminderdate={t '" + tempDate + "'} where id = "+ agmid ).executeUpdate();
		success=true;
	} catch (HibernateException e) {
		success=false;
		e.printStackTrace();
	}
	return success;
 }
  //Drop down list for  internal members
 
 //Dropdown list for external members
  public List<VotAgm> listActiveExternalAgm(){  
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotAgm.class);
       criteria.add(Restrictions.eq("importsource", "E"));
       criteria.addOrder(Order.desc("id")); 
           
            return (List<VotAgm>) criteria.list();
 } 
   
  public List<VotAgm> listActiveEasycoopAgm(){  
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotAgm.class);
       criteria.add(Restrictions.eq("importsource", "I"));
       return (List<VotAgm>) criteria.list();
 }   
}
