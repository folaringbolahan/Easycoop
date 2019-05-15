package com.sift.admin.service;

import java.util.List;
import com.sift.admin.model.Module;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface ModuleService { 
	public void addModule(Module module);
	public List<Module> listModule(); 
	public Module getModule(String id); 
	public void deleteModule(Module module);
}