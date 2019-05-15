package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.Module;
import com.sift.admin.dao.ModuleDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("moduleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ModuleServiceImpl implements ModuleService{
	 @Autowired
	 private ModuleDao moduleDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addModule(Module module){
		 moduleDao.addModule(module);
	 }

	 public List<Module> listModule(){
	  return moduleDao.listModule();
	 }

	 public Module getModule(String id){
	  return moduleDao.getModule(id);
	 }

	 public void deleteModule(Module module){
		 moduleDao.deleteModule(module);
	 }
}