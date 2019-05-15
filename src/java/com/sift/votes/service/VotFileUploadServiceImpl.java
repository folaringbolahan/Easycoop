/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotFileUploadDao;
import com.sift.votes.model.VotFileUpload;
import com.sift.votes.model.VotFileUploadCriteria;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votFileUploadService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotFileUploadServiceImpl implements VotFileUploadService{
    @Autowired
 private VotFileUploadDao votFileUploadDao;
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public void save(VotFileUpload votfileUpload){
      votFileUploadDao.save(votfileUpload);
 }
     
      public List<VotFileUpload> listAllVotFileUploadByCriteria(int companyId,String referenceNumber){
    return  votFileUploadDao.listAllVotFileUploadByCriteria(companyId,referenceNumber);
     
     }
       public List<VotFileUpload> listAllVotFileUploadByAgmCriteria(int agmId,String referenceNumber){
    return  votFileUploadDao.listAllVotFileUploadByAgmCriteria(agmId,referenceNumber);
     
     }
       public List<VotFileUpload> listAllVotFileUploadByStatus(int status){
    return  votFileUploadDao.listAllVotFileUploadByStatus(status);
     
     }
        public List<VotFileUpload> listAllFileUploadByCriteria(VotFileUploadCriteria votfileUpload){
         return votFileUploadDao.listAllFileUploadByCriteria(votfileUpload);
        }
}
