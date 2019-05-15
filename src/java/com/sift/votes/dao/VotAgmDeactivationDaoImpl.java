/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotAgmDeactivation;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("votAgmDeactivationDao")
public class VotAgmDeactivationDaoImpl implements VotAgmDeactivationDao {
     @Autowired
 private SessionFactory sessionFactory;
     
  public List<VotAgmDeactivation> listAgmsforDeactivation(){
   Criteria criteria = sessionFactory.getCurrentSession().createCriteria(VotAgmDeactivation.class);
       criteria.add(Restrictions.eq("status", 0));
       criteria.addOrder(Order.desc("createdate")); 
            return (List<VotAgmDeactivation>) criteria.list();
  }
}
