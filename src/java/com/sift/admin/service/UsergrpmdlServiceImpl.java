package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.Usergrpmdl;
import com.sift.admin.dao.UsergrpmdlDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("usergrpmdlService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UsergrpmdlServiceImpl implements UsergrpmdlService{
	 @Autowired
	 private UsergrpmdlDao usergrpmdlDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addUsergrpmdl(Usergrpmdl usergrpmdl){
		 usergrpmdlDao.addUsergrpmdl(usergrpmdl);
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addUsergrpmdl(Usergrpmdl usergrpmdl,String options[]){
		  usergrpmdlDao.addUsergrpmdl(usergrpmdl,options);
	 }

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addUsergrpmdl(Usergrpmdl usergrpmdl,String options[],String reportMenus[]){
		  usergrpmdlDao.addUsergrpmdl(usergrpmdl,options,reportMenus);
	 }
	 
	 public List<Usergrpmdl> listUsergrpmdl(){
		  return usergrpmdlDao.listUsergrpmdl();
	 }
	 
	 public List<Usergrpmdl> listUsergrpmdl(String usergroup){
		  return usergrpmdlDao.listUsergrpmdl(usergroup);
	 }
	 
	 public List<Usergrpmdl> listUsergrpmdl(String companyId,String branchId){
		  return usergrpmdlDao.listUsergrpmdl(companyId,branchId);
	 }
	
	 public List<Usergrpmdl> listUsergrpmdl(String companyId,String branchId,String usergroup){
		  return usergrpmdlDao.listUsergrpmdl(companyId,branchId,usergroup);
	 }

	 public Usergrpmdl getUsergrpmdl(String id){
		 return usergrpmdlDao.getUsergrpmdl(id);
	 }

	 public void deleteUsergrpmdl(Usergrpmdl usergrpmdl){
		 usergrpmdlDao.deleteUsergrpmdl(usergrpmdl);
	 }
}