/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotCompanyDao;
import com.sift.votes.model.VotCompany;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votCompanyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotCompanyServiceImpl implements VotCompanyService  {
 @Autowired
 private VotCompanyDao votCompanyDao;
 
 
 public List<VotCompany> listCompanies() {
	  return votCompanyDao.listCompanies();
	 }
 @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
 public void addCompanySetup(VotCompany votcompany){
      votCompanyDao.addCompanySetup(votcompany);
 }
  public VotCompany getVotCompany(int id){
	  return votCompanyDao.getVotCompany(id);
	 }
 public VotCompany getLastRecord(){
  return  votCompanyDao.getLastRecord();
 
} 	 	
}
