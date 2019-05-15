package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sift.admin.model.AuthPermit;
import com.sift.admin.model.Branch;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("authPermitDao")
public class AuthPermitDaoImpl implements AuthPermitDao{

 @Autowired
 private SessionFactory sessionFactory;

 public boolean addAuthPermit(AuthPermit authPermit) {
	boolean success=false;
	
    try{
    	sessionFactory.getCurrentSession().saveOrUpdate(authPermit);
    	success=true;
    }catch(Exception ex){
    	ex.printStackTrace();
    }
    
    return success;
 }

 @SuppressWarnings("unchecked")
 public List<AuthPermit> listAuthPermit(){
	 	return (List<AuthPermit>) sessionFactory.getCurrentSession().createCriteria(AuthPermit.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<AuthPermit> listAuthPermit(Integer companyId,Integer branchId){
	 	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AuthPermit.class);
	 	
	 	criteria.add(Restrictions.eq("companyId",companyId));
	 	criteria.add(Restrictions.eq("branchId",branchId));

	 	return  (List<AuthPermit>)criteria.list();	 
 } 
 
 @SuppressWarnings("unchecked")
 public List<AuthPermit> listAuthPermit(Integer companyId,Integer branchId,String accessLevelCode){
	 	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AuthPermit.class);
	 	
	 	criteria.add(Restrictions.eq("companyId",companyId));
	 	criteria.add(Restrictions.eq("branchId",branchId));
	 	criteria.add(Restrictions.eq("accessLevelCode",accessLevelCode));

	 	return  (List<AuthPermit>)criteria.list();	
 }

 public AuthPermit getAuthPermit(int id){
	 	return (AuthPermit) sessionFactory.getCurrentSession().get(AuthPermit.class, id);
 }

 public boolean deleteAuthPermit(AuthPermit authPermit){
	    boolean success=false;
		
	    try{
	   	 	sessionFactory.getCurrentSession().createQuery("delete from AuthPermit where id = "+authPermit.getId()).executeUpdate();
	    	success=true;
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    }
	    
	    return success;
 }
}