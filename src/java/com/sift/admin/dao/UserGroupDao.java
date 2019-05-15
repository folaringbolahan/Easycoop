package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.UserGroup;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface UserGroupDao {
	 public void addUserGroup(UserGroup userGroup);
	 public boolean addUserGroup(UserGroup userGroup,boolean defaultMenu);
	 public List<UserGroup> listUserGroups();
	 public List<UserGroup> listUserGroupsByCompanyBranch(String coyId,String branchId);
	 public List<UserGroup> listUserGroupsByCompanyBranch(String coyId,String branchId,String accessId);
	 public List<UserGroup> listUserGroups(String accessId);
	 public List<UserGroup> listUserGroups(String accessId,String operand);
	 public UserGroup getUserGroup(int typeid);
	 public void deleteUserGroup(UserGroup userGroup);
}