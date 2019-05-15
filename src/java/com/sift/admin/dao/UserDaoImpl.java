package com.sift.admin.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.bean.UserBean;
import com.sift.admin.model.Company;
import com.sift.admin.model.User;
import com.sift.loan.utility.HelperUtility;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao{
 @Autowired
 private SessionFactory sessionFactory;
 
 HelperUtility helperUTIL=new HelperUtility(); 

 public boolean addUser(User user){
   boolean success=false;
   
   try{
	   sessionFactory.getCurrentSession().saveOrUpdate(user);
	   success=true;
   }catch(Exception ex){
	   ex.printStackTrace();
   }
   
   return success;
 }

 @SuppressWarnings("unchecked")
 public List<User> listUsers(){
    return (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class).list();
 }
 
 @SuppressWarnings("unchecked")
 public List<User> listUsersByCompanyBranch(String companyId,String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("branchId",branchId));

	 return (List<User>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<User> listBranchAdminsForCoy(String companyId,String usergroup){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("groupId",usergroup));

	 return (List<User>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<UserBean> listBranchAdminsForCoyBeans(String companyId,String usergroup){
	 return helperUTIL.getUserBeanListByCoopByGroup(sessionFactory, companyId,usergroup);
 }
 
 @SuppressWarnings("unchecked")
 public List<User> listUsersByGroup(String usergroup){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
	 criteria.add(Restrictions.eq("groupId",usergroup));

	 return (List<User>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<UserBean> listUserBeansByGroup(String usergroup){
	 return helperUTIL.getUserBeanListByGroup(sessionFactory,usergroup);
 }
 
 public List<User> listUsersByGroupByCoy(String companyId,String usergroup){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
	 criteria.add(Restrictions.eq("groupId",usergroup));
	 criteria.add(Restrictions.eq("companyId",companyId));

	 return (List<User>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<User> listUsersByGroupByCoyByBranch(String companyId,String branchId,int accessId){
	 //String query=" select * from users where companyid="+companyId+" and branch="+branchId+" and accesslevel in (select code from usergroups where accessid="+accessId+" and companyid="+companyId+" and branchid="+branchId+")";
	 String query=" from User where companyId="+companyId+" and branchId="+branchId+" and groupId in (select code from UserGroup where accessId="+accessId+" and companyId="+companyId+" and branchId="+branchId+")";
	 return (List<User>)sessionFactory.getCurrentSession().createQuery(query).list();
 } 
 
 @SuppressWarnings("unchecked")
 public List<UserBean> listUserBeansByGroupByCoyByBranch(String companyId,String branchId,int accessId){
	 return helperUTIL.getUserBeanList(sessionFactory, companyId, branchId, accessId);
 } 
 
 @SuppressWarnings("unchecked")
 public List<UserBean> listInActiveUserBeansForBranch(String branchId){
	 return helperUTIL.getInActiveUserBeanList(sessionFactory,branchId);
 }
 
 @SuppressWarnings("unchecked")
 public List<User> listInActiveBAUserForCoy(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
	 criteria.add(Restrictions.eq("isBranchUser",1));
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("enabled",0));

	 return (List<User>)criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<UserBean> listInActiveBAUserBeanForCoy(String companyId){
	 return helperUTIL.getInActiveBAUserBeanList(sessionFactory,companyId);
 }

 public User getUser(int id){
	System.out.println("id=:"+id);
    return (User) sessionFactory.getCurrentSession().get(User.class, id);
 } 

 public User getUserByEmail(String emailId){  
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
	 criteria.add(Restrictions.eq("email",emailId.trim()));
	 
	 System.out.println("emailId/size=:"+emailId+"/"+emailId.length());
	 System.out.println("List size=:"+criteria.list().size());
	 
	 return (User)criteria.list().get(0);
 } 
 
 public void deleteUser(User user){
	 sessionFactory.getCurrentSession().createQuery("delete from User where id = "+user.getId()).executeUpdate();
 }
 
 public boolean resetUserLogon(User user){
	 return sessionFactory.getCurrentSession().createQuery("update User set mustChangePass='Y',password='"+user.getPassword()+"',credentialsNonExpired=1,LastPasswordDate = now(),accountNonExpired=1,lastmodified=now()  where upper(email) = '"+user.getEmail().toUpperCase().trim()+ "'").executeUpdate()>0?true:false;
 }
 
 public boolean updateUserLogon(User user){
	 return sessionFactory.getCurrentSession().createQuery("update User set mustChangePass='N',password='"+user.getPassword()+"',credentialsNonExpired=1,LastPasswordDate = now(),accountNonExpired=1,lastmodified=now() where upper(email) = '"+user.getEmail().toUpperCase().trim()+ "'").executeUpdate()>0?true:false;
 }
 
 public boolean activateUser(User user){
	 return sessionFactory.getCurrentSession().createQuery("update User set enabled=1,mustChangePass='Y',password='"+user.getPassword()+"',credentialsNonExpired=1,LastPasswordDate = now(),accountNonExpired=1 where upper(email) = '"+user.getEmail().toUpperCase().trim()+ "'").executeUpdate()>0?true:false;
 }
 
 public boolean deactivateUser(User user){
	 return sessionFactory.getCurrentSession().createQuery("update User set enabled=0 where upper(email) = '"+user.getEmail().toUpperCase().trim()+ "'").executeUpdate()>0?true:false;
 }
 
 public boolean disableUser(User user){
	 return sessionFactory.getCurrentSession().createQuery("update User set enabled=0 where upper(email) = '"+user.getEmail().toUpperCase().trim()+ "'").executeUpdate()>0?true:false;
 }
 
 public boolean enableUser(User user){
	 return sessionFactory.getCurrentSession().createQuery("update User set enabled=1 where upper(email) = '"+user.getEmail().toUpperCase().trim()+ "'").executeUpdate()>0?true:false;
 }

    @Override
    public List<User> listUsersByCompany(String companyId) {
         Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);	 
	 criteria.add(Restrictions.eq("companyId",companyId));

	 return (List<User>)criteria.list();
    }
}