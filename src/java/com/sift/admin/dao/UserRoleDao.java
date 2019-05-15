package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.UserRole;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface UserRoleDao {
	 public void addUserRole(UserRole userRole);
	 public List<UserRole> listUserRoles();
	 public List<UserRole> listUserRolesByGroup(String id);
	 public UserRole getUserRole(int typeid);
	 public void deleteUserRole(UserRole userRole);
}