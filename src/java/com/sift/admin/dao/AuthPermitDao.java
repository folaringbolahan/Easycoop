package com.sift.admin.dao;

import java.util.List;
import com.sift.admin.model.AuthPermit;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface AuthPermitDao {
	 public boolean addAuthPermit(AuthPermit authPermit);
	 public List<AuthPermit> listAuthPermit();
	 public List<AuthPermit> listAuthPermit(Integer companyId,Integer branchId);
	 public List<AuthPermit> listAuthPermit(Integer companyId,Integer branchId,String accessLevelCode);
	 public AuthPermit getAuthPermit(int typeid);
	 public boolean deleteAuthPermit(AuthPermit authPermit);
}