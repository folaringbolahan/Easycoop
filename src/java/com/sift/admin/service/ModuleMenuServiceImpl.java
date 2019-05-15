package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.ModuleMenu;
import com.sift.admin.dao.ModuleMenuDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("moduleMenuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ModuleMenuServiceImpl implements ModuleMenuService{
	 @Autowired
	 private ModuleMenuDao moduleMenuDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addModuleMenu(ModuleMenu moduleMenu){
		 moduleMenuDao.addModuleMenu(moduleMenu);
	 }

	 public List<ModuleMenu> listModuleMenu(){
	  return moduleMenuDao.listModuleMenu();
	 }
	 
	 public List<ModuleMenu> listModuleMenu(String accessCode){
		  return moduleMenuDao.listModuleMenu(accessCode);
	 }

	 public ModuleMenu getModuleMenu(String id){
	  return moduleMenuDao.getModuleMenu(id);
	 }

	 public void deleteModuleMenu(ModuleMenu moduleMenu){
		 moduleMenuDao.deleteModuleMenu(moduleMenu);
	 }
}