/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.dao;

import com.sift.admin.model.MemberExtraField;
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
@Repository("MemberExtraFieldDao")
public class MemberExtraFieldDaoImpl implements MemberExtraFieldDao {
   @Autowired
 private SessionFactory sessionFactory;

 public void addMemberExtraField(MemberExtraField memberExtraField){
   sessionFactory.getCurrentSession().saveOrUpdate(memberExtraField);
 }
 @SuppressWarnings("unchecked")
 public List<MemberExtraField> listAllMemberExtraField(){
    return (List<MemberExtraField>) sessionFactory.getCurrentSession().createCriteria(MemberExtraField.class).list();
 }

@SuppressWarnings("unchecked")
 public List<MemberExtraField> listAllMemberExtraFieldForApproval(){
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberExtraField.class);
       criteria.add(Restrictions.eq("active", "N"));
        return (List<MemberExtraField>) criteria.list();
 }

 @SuppressWarnings("unchecked")
 public List<MemberExtraField> listMemberExtraField(){
     Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberExtraField.class);
       criteria.add(Restrictions.eq("grouped", "Y"));
        return (List<MemberExtraField>) criteria.list();
 }
   public List<MemberExtraField> listMemberExtraFieldGrouped(){
     Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberExtraField.class);
     criteria.add(Restrictions.eq("active", "Y"));
       criteria.add(Restrictions.eq("grouped", "Y"));
        return (List<MemberExtraField>) criteria.list();
 }

 public MemberExtraField getMemberExtraField(int id){
  return (MemberExtraField) sessionFactory.getCurrentSession().get(MemberExtraField.class, id);
 }

 public void deleteMemberExtraField(MemberExtraField memberExtraField) {
	 sessionFactory.getCurrentSession().createQuery("DELETE FROM MemberExtraField WHERE ID = "+memberExtraField.getId()).executeUpdate();
 } 
public void approveMemberExtraField(MemberExtraField memberExtraField) {
	 sessionFactory.getCurrentSession().createQuery("UPDATE MemberExtraField set active='Y' WHERE ID = "+memberExtraField.getId()).executeUpdate();
 } 

  

   
}
