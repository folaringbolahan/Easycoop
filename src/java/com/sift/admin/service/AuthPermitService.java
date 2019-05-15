package com.sift.admin.service;

import java.util.List;
import com.sift.admin.model.AuthPermit;

/**
 * @author XTOFFEL CONSULT
 */
public interface AuthPermitService { 
	public boolean addAuthPermit(AuthPermit authPermit);
	public List<AuthPermit> listAuthPermit(); 
	public List<AuthPermit> listAuthPermit(Integer companyId,Integer branchId);
	public List<AuthPermit> listAuthPermit(Integer companyId,Integer branchId,String accessLevelCode);
	public AuthPermit getAuthPermit(int id); 
	public boolean deleteAuthPermit(AuthPermit authPermit);
}