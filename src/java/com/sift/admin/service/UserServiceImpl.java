package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.User;
import com.sift.admin.bean.UserBean;
import com.sift.admin.dao.UserDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService{
	 @Autowired
	 private UserDao userDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addUser(User user){
		 return userDao.addUser(user);
	 }

	 public List<User> listUsers(){
	  return userDao.listUsers();
	 }
	 
	 public List<User> listUsersByCompanyBranch(String companyId,String branchId){
		 return userDao.listUsersByCompanyBranch(companyId,branchId);
	 }
	 
	 public List<User> listUsersByGroup(String usergroup){
		 return userDao.listUsersByGroup(usergroup);
	 }
	 
	 public List<UserBean> listUserBeansByGroup(String usergroup){
		 return userDao.listUserBeansByGroup(usergroup);
	 }
	 
	 public List<User> listUsersByGroupByCoy(String companyId,String usergroup){
		 return userDao.listUsersByGroupByCoy(companyId,usergroup);
	 }
	 
	 public List<User> listBranchAdminsForCoy(String companyId,String usergroup){
		 return userDao.listBranchAdminsForCoy(companyId,usergroup);
	 }
	 
	 public List<UserBean> listBranchAdminsForCoyBeans(String companyId,String usergroup){
		 return userDao.listBranchAdminsForCoyBeans(companyId,usergroup);
	 }
	 
	 public List<UserBean> listInActiveUserBeansForBranch(String branchId){
		 return userDao.listInActiveUserBeansForBranch(branchId);
	 }
	 
	 public List<User> listInActiveBAUserForCoy(String companyId){
		 return userDao.listInActiveBAUserForCoy(companyId);
	 }
	 
	 public List<UserBean> listInActiveBAUserBeanForCoy(String companyId){
		 return userDao.listInActiveBAUserBeanForCoy(companyId);
	 }
	 
	 public List<User> listUsersByGroupByCoyByBranch(String companyId,String branchId,int accessId){
		 return userDao.listUsersByGroupByCoyByBranch(companyId,branchId,accessId);
	 }
	 
	 public List<UserBean> listUserBeansByGroupByCoyByBranch(String companyId,String branchId,int accessId){
		 return userDao.listUserBeansByGroupByCoyByBranch(companyId,branchId,accessId);
	 }
	 
	 /*public List<User> listUsersByCompany(String companyId){
		 return userDao.listUsersByCompany(companyId);
	 }*/
	 
	 public User getUserByEmail(String emailId){
		 return userDao.getUserByEmail(emailId);
	 }


	 public User getUser(int id){
	  return userDao.getUser(id);
	 }

	 public void deleteUser(User user){
		 userDao.deleteUser(user);
	 }
	 
	 public boolean resetUserLogon(User user){
		 return userDao.resetUserLogon(user);
	 }
	 
	 public boolean updateUserLogon(User user){
		 return userDao.updateUserLogon(user);
	 }
	 
	 public boolean activateUser(User user){
		 return userDao.activateUser(user);
	 }
	 
	 public boolean deactivateUser(User user){
		 return userDao.deactivateUser(user);
	 }
	 
	 public boolean enableUser(User user){
		 return userDao.enableUser(user);
	 }
	 
	 public boolean disableUser(User user){
		 return userDao.disableUser(user);
	 }	 

    @Override
    public List<User> listUsersByCompany(String companyId) {
        return userDao.listUsersByCompany(companyId);
    }
}