/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.model.VotFileUpload;
import com.sift.votes.model.VotFileUploadCriteria;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface VotFileUploadService {
  public void save(VotFileUpload votfileUpload);
   public List<VotFileUpload> listAllVotFileUploadByCriteria(int companyId,String referenceNumber);
   public List<VotFileUpload> listAllVotFileUploadByAgmCriteria(int agmId,String referenceNumber);
   public List<VotFileUpload> listAllVotFileUploadByStatus(int status);
     public List<VotFileUpload> listAllFileUploadByCriteria(VotFileUploadCriteria votfileUpload);
   
}
