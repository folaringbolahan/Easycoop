/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotAgmDao;
import com.sift.votes.model.VotAgm;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votAgmService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotAgmServiceImpl implements VotAgmService {
     @Autowired
	 private VotAgmDao votAgmDao;
     
      @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 public void addAgmSetup(VotAgm votAgm){
		votAgmDao.addAgmSetup(votAgm);
	 }
       public List<VotAgm> listExternalAgm(){
        return votAgmDao.listExternalAgm();
       }
      public List<VotAgm> listEasycoopAgm(){
           return votAgmDao.listEasycoopAgm();
       }
      public List<VotAgm> listAllAgm(){
           return votAgmDao.listAllAgm();
       }
      public List<VotAgm> listActiveAgms(){
          return votAgmDao.listActiveAgms();
      }

      public VotAgm getAgmdetails(Integer agmid){
          return votAgmDao.getAgmdetails(agmid);
      }
      public List<VotAgm> listInactiveAgms(){
       return votAgmDao.listInactiveAgms();
       }
       public VotAgm getAgmById(int agmid){
       return votAgmDao.getAgmById(agmid);
       }
       public List<VotAgm> listAllAgms(){
       return votAgmDao.listAllAgms();
       }
       public List<VotAgm> listClosedAgms(){
         return votAgmDao.listClosedAgms();
       }
       public List<VotAgm> listActiveExternalAgm(){
          return votAgmDao.listActiveExternalAgm();
       }
       public List<VotAgm> listActiveEasycoopAgm(){
          return votAgmDao.listActiveEasycoopAgm();
       }

}
