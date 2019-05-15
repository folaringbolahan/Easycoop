package com.sift.admin.service;

import java.util.List;
import com.sift.admin.model.Usergrpmdl;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface UsergrpmdlService { 
	public void addUsergrpmdl(Usergrpmdl usergrpmdl);
	public void addUsergrpmdl(Usergrpmdl usergrpmdl,String options[]);
	public void addUsergrpmdl(Usergrpmdl usergrpmdl,String options[],String reportMenus[]);
	public List<Usergrpmdl> listUsergrpmdl(); 
	public List<Usergrpmdl> listUsergrpmdl(String usergroup);
	public List<Usergrpmdl> listUsergrpmdl(String companyId,String branchId); 
	public List<Usergrpmdl> listUsergrpmdl(String companyId,String branchId,String usergroup);
	public Usergrpmdl getUsergrpmdl(String id); 
	public void deleteUsergrpmdl(Usergrpmdl usergrpmdl);
}