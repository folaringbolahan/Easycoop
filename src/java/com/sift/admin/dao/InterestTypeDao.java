package com.sift.admin.dao;

import java.util.List;

import com.sift.admin.model.InterestType;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface InterestTypeDao {
	 public void addInterestType(InterestType interestType);
	 public List<InterestType> listInterestTypes();
	 public List<InterestType> listInterestTypes(String typeCode);
	 public InterestType getInterestType(int typeid);
	 public void deleteInterestType(InterestType interestType);
}