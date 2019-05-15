package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.UserRole;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface UserRoleService { 
	public void addUserRole(UserRole userRole);
	public List<UserRole> listUserRoles(); 
	public List<UserRole> listUserRoles(String id); 
	public UserRole getUserRole(int id); 
	public void deleteUserRole(UserRole userRole);
}