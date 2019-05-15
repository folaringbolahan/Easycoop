package com.sift.admin.dao;

import java.util.List;
import com.sift.admin.bean.UserBean;
import com.sift.admin.model.User;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface UserDao {
	 public boolean addUser(User user);
	 public List<User> listUsers();
	 public List<User> listUsersByCompanyBranch(String companyId,String branchId); 
	 public List<User> listUsersByGroup(String usergroup);
	 public List<UserBean> listUserBeansByGroup(String usergroup);
	 public List<User> listUsersByGroupByCoy(String companyId,String usergroup);
	 public List<User> listBranchAdminsForCoy(String companyId,String usergroup);
	 public List<UserBean> listBranchAdminsForCoyBeans(String companyId,String usergroup);
	 public List<User> listUsersByGroupByCoyByBranch(String companyId,String branchId,int accessId);
	 public List<UserBean> listUserBeansByGroupByCoyByBranch(String companyId,String branchId,int accessId);
	 public List<UserBean> listInActiveUserBeansForBranch(String branchId);
	 public List<User> listInActiveBAUserForCoy(String companyId);
	 public List<UserBean> listInActiveBAUserBeanForCoy(String companyId);
	 public List<User> listUsersByCompany(String companyId);
	 public User getUser(int typeid);
	 public User getUserByEmail(String emailId);
	 public void deleteUser(User user);
	 public boolean resetUserLogon(User user);
	 public boolean updateUserLogon(User user);
	 public boolean activateUser(User user);
	 public boolean deactivateUser(User user);
	 public boolean enableUser(User user);
	 public boolean disableUser(User user);
}