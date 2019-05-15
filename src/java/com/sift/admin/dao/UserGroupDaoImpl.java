package com.sift.admin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sift.admin.model.UserGroup;
import com.sift.admin.model.Usergrpmdl;


/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("userGroupDao")
public class UserGroupDaoImpl implements UserGroupDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addUserGroup(UserGroup userGroup) {
	   sessionFactory.getCurrentSession().saveOrUpdate(userGroup);
 }
 
 public boolean addUserGroup(UserGroup userGroup,boolean defaultMenu) {
   boolean success=false;
   
   try{
	   sessionFactory.getCurrentSession().saveOrUpdate(userGroup);
	   
	   if(defaultMenu){
		   String query1=   " INSERT INTO `usergrpmdl`(`ID`, `usergroup`, `menu`, `branchid`,`companyid`,`menue`) " +
							" VALUES         (NULL, '"+userGroup.getCode()+"', 'AL1', '"+userGroup.getBranchId()+"', '"+userGroup.getCompanyId()+"', NULL)," +
							"				  (NULL, '"+userGroup.getCode()+"', 'RP1', '"+userGroup.getBranchId()+"', '"+userGroup.getCompanyId()+"', NULL);"; // +
		
		   sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate(); 	
	   }	   
	   success=true;
	}catch(HibernateException e){
		e.printStackTrace();
		success=false;
	}
	
	return success;
 }

 @SuppressWarnings("unchecked")
 public List<UserGroup> listUserGroups(){
  return (List<UserGroup>) sessionFactory.getCurrentSession().createCriteria(UserGroup.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<UserGroup> listUserGroups(String accessId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserGroup.class);
	 criteria.add(Restrictions.eq("accessId",accessId));
	 
	 return (List<UserGroup>) criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<UserGroup> listUserGroupsByCompanyBranch(String coyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserGroup.class);
	 criteria.add(Restrictions.eq("companyId",coyId));
	 criteria.add(Restrictions.eq("branchId",branchId));

	 return (List<UserGroup>) criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<UserGroup> listUserGroupsByCompanyBranch(String coyId,String branchId,String accessId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserGroup.class);
	 criteria.add(Restrictions.eq("companyId",coyId));
	 criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("accessId",accessId));

	 return (List<UserGroup>) criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<UserGroup> listUserGroups(String accessId,String operand){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserGroup.class);
	 
	 if("EQ".equalsIgnoreCase(operand.trim())){
		 criteria.add(Restrictions.eq("accessId",accessId));
	 }else if("GT".equalsIgnoreCase(operand.trim())){
		 criteria.add(Restrictions.gt("accessId",accessId));
	 }else if("GE".equalsIgnoreCase(operand.trim())){
		 criteria.add(Restrictions.ge("accessId",accessId));
	 }else if("NE".equalsIgnoreCase(operand.trim())){
		 criteria.add(Restrictions.ne("accessId",accessId));
	 }else if("LT".equalsIgnoreCase(operand.trim())){
		 criteria.add(Restrictions.lt("accessId",accessId));
	 }
	 
	 return (List<UserGroup>) criteria.list();
 }


 public UserGroup getUserGroup(int id){
  return (UserGroup) sessionFactory.getCurrentSession().get(UserGroup.class, id);
 }

 public void deleteUserGroup(UserGroup userGroup) {
	 sessionFactory.getCurrentSession().createQuery("delete from UserGroup where id = "+userGroup.getId()).executeUpdate();
 }
}