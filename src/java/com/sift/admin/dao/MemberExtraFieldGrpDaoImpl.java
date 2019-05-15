/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.dao;

import com.sift.admin.model.MemberExtraFieldGrp;
import java.util.List;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("memberExtraFieldGrpDao")
public class MemberExtraFieldGrpDaoImpl implements MemberExtraFieldGrpDao{
     @Autowired
 private SessionFactory sessionFactory; 
    
     
     public void addMemberExtraFieldGrp(MemberExtraFieldGrp memberExtraFieldGrp){
   sessionFactory.getCurrentSession().saveOrUpdate(memberExtraFieldGrp);
 }
    
    @SuppressWarnings("unchecked")
 public List<MemberExtraFieldGrp> ListMemberExtraFieldGrps(){
       return sessionFactory.getCurrentSession().createCriteria(MemberExtraFieldGrp.class).list();
    //return (List<MemberExtraFieldGrp>) sessionFactory.getCurrentSession().createCriteria(MemberExtraFieldGrp.class);
   
     
    
}
     public void deleteMemberExtraFieldGrp(MemberExtraFieldGrp memberExtraFieldGrp) {
	 sessionFactory.getCurrentSession().createQuery("DELETE FROM MemberExtraFieldGrp WHERE ID = "+memberExtraFieldGrp.getId()).executeUpdate();
 }


public MemberExtraFieldGrp getMemberExtraFieldGrp(int id){
  return (MemberExtraFieldGrp) sessionFactory.getCurrentSession().get(MemberExtraFieldGrp.class, id);
 }
}