/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.webservice.dao;


import com.sift.webservice.model.UserWS;
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
@Repository("UserWSDao")
public class UserDaoImplWS implements UserWSDao {
    
     @Autowired
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
     public List<UserWS> getAllCoopMail(String companyid,String branchid,String accesslevel){
	     System.out.println("i am here ");
          Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserWS.class);
	 criteria.add(Restrictions.eq("companyId",companyid))
                 .add(Restrictions.eq("branchId",branchid))
                 .add(Restrictions.eq("groupId",accesslevel));
          System.out.println("the size of the list is "+ criteria.list().size());
	 return criteria.list();
      }
 
}
