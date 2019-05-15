/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("MemberProfileUpdateDao")
public class MemberProfileUpdateDaoImpl implements MemberProfileUpdateDao {
   @Autowired
 private SessionFactory sessionFactory; 
   
   @SuppressWarnings("unchecked")
 public List<MemberProfileUpdate> listMemberProfileUpdate(int branchid,int companyid){
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberProfileUpdate.class);
       criteria.add(Restrictions.eq("approved", "N"));
       criteria.add(Restrictions.eq("branchid", branchid));
       criteria.add(Restrictions.eq("companyid", companyid));
        return (List<MemberProfileUpdate>) criteria.list();
 }
   
    public MemberProfileUpdate getPendingProfilebyId(int id){
  return (MemberProfileUpdate) sessionFactory.getCurrentSession().get(MemberProfileUpdate.class, id);
 }
    
}
