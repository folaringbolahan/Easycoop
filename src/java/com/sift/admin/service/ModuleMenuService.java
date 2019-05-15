package com.sift.admin.service;

import java.util.List;
import com.sift.admin.model.ModuleMenu;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface ModuleMenuService { 
	public void addModuleMenu(ModuleMenu moduleMenu);
	public List<ModuleMenu> listModuleMenu(); 
	public List<ModuleMenu> listModuleMenu(String accessCode); 
	public ModuleMenu getModuleMenu(String id); 
	public void deleteModuleMenu(ModuleMenu moduleMenu);
}