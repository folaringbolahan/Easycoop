/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.model.VotCompany;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface VotCompanyService { 
public List<VotCompany> listCompanies();
 public void addCompanySetup(VotCompany votcompany);
 public VotCompany getVotCompany(int id);
  public VotCompany getLastRecord();
}
