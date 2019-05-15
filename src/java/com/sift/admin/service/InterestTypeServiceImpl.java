package com.sift.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sift.admin.model.InterestType;
import com.sift.admin.dao.InterestTypeDao;

/**
 * @author XTOFEL CONSULT
 *
 */
@Service("interestTypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class InterestTypeServiceImpl implements InterestTypeService {
	 @Autowired
	 private InterestTypeDao interestTypeDao;

	 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addInterestType(InterestType interestType) {
		 interestTypeDao.addInterestType(interestType);
	 }

	 public List<InterestType> listInterestTypes() {
	  return interestTypeDao.listInterestTypes();
	 }
	 
	 public List<InterestType> listInterestTypes(String typeCode){
		  return interestTypeDao.listInterestTypes(typeCode);
	 }

	 public InterestType getInterestType(int id){
	  return interestTypeDao.getInterestType(id);
	 }

	 public void deleteInterestType(InterestType interestType) {
		 interestTypeDao.deleteInterestType(interestType);
	 }
}