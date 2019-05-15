package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.UserGroup;
import com.sift.admin.dao.UserGroupDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("userGroupService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserGroupServiceImpl implements UserGroupService {
	 @Autowired
	 private UserGroupDao userGroupDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addUserGroup(UserGroup userGroup) {
		 userGroupDao.addUserGroup(userGroup);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addUserGroup(UserGroup userGroup,boolean defaultMenu) {
		 return userGroupDao.addUserGroup(userGroup,defaultMenu);
	 }

	 public List<UserGroup> listUserGroups(){
	     return userGroupDao.listUserGroups();
	 }
	 
	 public List<UserGroup> listUserGroupsByCompanyBranch(String coyId,String branchId){
		 return userGroupDao.listUserGroupsByCompanyBranch(coyId,branchId);
	 }
	 
	 public List<UserGroup> listUserGroupsByCompanyBranch(String coyId,String branchId,String accessId){
		 return userGroupDao.listUserGroupsByCompanyBranch(coyId,branchId,accessId);
	 }
	 
	 public List<UserGroup> listUserGroups(String accessId){
		  return userGroupDao.listUserGroups(accessId);
	 }
	 
	 public List<UserGroup> listUserGroups(String accessId,String operand){
		 return userGroupDao.listUserGroups(accessId,operand);
	 }

	 public UserGroup getUserGroup(int id){
	  return userGroupDao.getUserGroup(id);
	 }

	 public void deleteUserGroup(UserGroup userGroup) {
		 userGroupDao.deleteUserGroup(userGroup);
	 }
}