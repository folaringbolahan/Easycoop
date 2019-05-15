/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotCompany;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface VotCompanyDao {
  public List<VotCompany> listCompanies();  
   public void addCompanySetup(VotCompany addDetails);
   public VotCompany getVotCompany(int id);
   public VotCompany getLastRecord();
}
