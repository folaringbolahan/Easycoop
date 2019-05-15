package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.Branch;
import com.sift.admin.model.UserRole;

import org.springframework.transaction.annotation.Transactional;
/**
 * @author XTOFFEL CONSULT
 *
 */
@Transactional
@Repository("userRoleDao")
public class UserRoleDaoImpl implements UserRoleDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addUserRole(UserRole userRole) {
   sessionFactory.getCurrentSession().saveOrUpdate(userRole);
 }

 @SuppressWarnings("unchecked")
 public List<UserRole> listUserRoles(){
  return (List<UserRole>) sessionFactory.getCurrentSession().createCriteria(UserRole.class).list();
 }

 @SuppressWarnings("unchecked")
 public List<UserRole> listUserRolesByGroup(String groupId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserRole.class);
	 criteria.add(Restrictions.eq("groupId", Integer.parseInt(groupId)));
     
	 return criteria.list();
 }

 public UserRole getUserRole(int id){
  return (UserRole) sessionFactory.getCurrentSession().get(UserRole.class, id);
 }

 public void deleteUserRole(UserRole userRole) {
	 //sessionFactory.getCurrentSession().createQuery("delete from UserRole where id = "+userRole.getId()).executeUpdate();
	 sessionFactory.getCurrentSession().saveOrUpdate(userRole);
 }
}