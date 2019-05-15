/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member;

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
@Repository("memberEmailChangeDao")
public class MemberEmailChangeDaoImpl implements  MemberEmailChangeDao{
    @Autowired
 private SessionFactory sessionFactory;
     public void addEmail(MemberEmailChange memberEmailChange){
       sessionFactory.getCurrentSession().saveOrUpdate(memberEmailChange);
     }
 @SuppressWarnings("unchecked")
 public List<MemberEmailChange> listEmailChanges(){
 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberEmailChange.class);
   criteria.add(Restrictions.eq("approved","N"));
    criteria.addOrder(Order.desc("id")); 
    return (List<MemberEmailChange>)criteria.list();
 }
 public MemberEmailChange getPendingEmailChangesbyId (int id){
 return (MemberEmailChange) sessionFactory.getCurrentSession().get(MemberEmailChange.class, id);
 }
}
