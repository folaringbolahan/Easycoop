package com.sift.admin.service;

import java.util.List;

import com.sift.admin.model.InterestType;

/**
 * @author XTOFFEL CONSULT
 *
 */
public interface InterestTypeService { 
	public void addInterestType(InterestType interestType);
	public List<InterestType> listInterestTypes(); 
	public List<InterestType> listInterestTypes(String typeCode); 
	public InterestType getInterestType(int id); 
	public void deleteInterestType(InterestType interestType);
}