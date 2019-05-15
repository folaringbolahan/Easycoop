package com.sift.admin.dao;

import java.util.List;
import com.sift.admin.model.Module;
/**
 * @author XTOFFEL CONSULT
 *
 */
public interface ModuleDao{
	 public void addModule(Module addDetails);
	 public List<Module> listModule();
	 public Module getModule(String id);
	 public void deleteModule(Module addDetails);
}