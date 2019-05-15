package com.sift.admin.dao;

import java.util.List;
import com.sift.admin.model.ModuleMenu;
/**
 * @author XTOFFEL CONSULT
 *
 */
public interface ModuleMenuDao{
	 public void addModuleMenu(ModuleMenu addDetails);
	 public List<ModuleMenu> listModuleMenu();
	 public List<ModuleMenu> listModuleMenu(String accessCode);
	 public ModuleMenu getModuleMenu(String id);
	 public void deleteModuleMenu(ModuleMenu addDetails);
}