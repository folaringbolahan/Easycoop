/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.dao;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.MemberCategory;
import org.hibernate.HibernateException;
/**
 *
 * @author Nelson Akpos
 */
@Repository("memberCategoryDao")
public class MemberCategoryDaoImpl implements MemberCategoryDao {
   @Autowired
 private SessionFactory sessionFactory;
   
   
   public void addMemberCategory(MemberCategory memberCategory){
   sessionFactory.getCurrentSession().saveOrUpdate(memberCategory);
 }
   
 @SuppressWarnings("unchecked")
 public List<MemberCategory> listMemberCategories(){
  return (List<MemberCategory>) sessionFactory.getCurrentSession().createCriteria(MemberCategory.class).list();
 }
 
 public void deleteMemberCategory(MemberCategory memberCategory) {
	 sessionFactory.getCurrentSession().createQuery("DELETE FROM MemberCategory WHERE ID = "+memberCategory.getId()).executeUpdate();
 }


public MemberCategory getMemberCategory(int id){
  return (MemberCategory) sessionFactory.getCurrentSession().get(MemberCategory.class, id);
 }
}
