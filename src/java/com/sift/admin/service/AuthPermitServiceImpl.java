package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sift.admin.model.AuthPermit;
import com.sift.admin.dao.AuthPermitDao;
/**
 * @author XTOFEL CONSULT
 *
 */
@Service("authPermitService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AuthPermitServiceImpl implements AuthPermitService {
	 
	 @Autowired
	 private AuthPermitDao authPermitDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean addAuthPermit(AuthPermit authPermit){
		 return authPermitDao.addAuthPermit(authPermit);
	 }

	 public List<AuthPermit> listAuthPermit(){
	     return authPermitDao.listAuthPermit();
	 }
	 
	 public List<AuthPermit> listAuthPermit(Integer companyId,Integer branchId){
	     return authPermitDao.listAuthPermit(companyId,branchId);
	 }
	 
	 public List<AuthPermit> listAuthPermit(Integer companyId,Integer branchId,String accessLevelCode){
	     return authPermitDao.listAuthPermit(companyId,branchId,accessLevelCode);
	 }

	 public AuthPermit getAuthPermit(int id){
	     return authPermitDao.getAuthPermit(id);
	 }

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public boolean deleteAuthPermit(AuthPermit authPermit) {
		 return authPermitDao.deleteAuthPermit(authPermit);
	 }
}