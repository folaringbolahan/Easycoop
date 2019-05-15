package com.sift.admin.dao;

import java.util.List;
import com.sift.admin.model.Usergrpmdl;
/**
 * @author XTOFFEL CONSULT
 *
 */
public interface UsergrpmdlDao{
	 public void addUsergrpmdl(Usergrpmdl addDetails);
	 public void addUsergrpmdl(Usergrpmdl usergrpmdl,String options[]);
	 public void addUsergrpmdl(Usergrpmdl usergrpmdl,String options[],String reportMenus[]);
	 public List<Usergrpmdl> listUsergrpmdl();
	 public List<Usergrpmdl> listUsergrpmdl(String usergroup);
	 public List<Usergrpmdl> listUsergrpmdl(String companyId,String branchId); 
	 public List<Usergrpmdl> listUsergrpmdl(String companyId,String branchId,String usergroup);
	 public Usergrpmdl getUsergrpmdl(String id);
	 public void deleteUsergrpmdl(Usergrpmdl addDetails);
}