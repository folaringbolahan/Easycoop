package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.UserRole;
import com.sift.admin.dao.UserRoleDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("userRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserRoleServiceImpl implements UserRoleService {
	 @Autowired
	 private UserRoleDao userRoleDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addUserRole(UserRole userRole) {
		 userRoleDao.addUserRole(userRole);
	 }

	 public List<UserRole> listUserRoles(){
	  return userRoleDao.listUserRoles();
	 }

	 public List<UserRole> listUserRoles(String id) {
	   return userRoleDao.listUserRolesByGroup(id);
	 }

	 public UserRole getUserRole(int id){
	  return userRoleDao.getUserRole(id);
	 }

	 public void deleteUserRole(UserRole userRole) {
		 userRoleDao.deleteUserRole(userRole);
	 }
}