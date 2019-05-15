/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotMembersErrorsDao;
import com.sift.votes.model.VotMembersErrors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votMembersErrorsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotMembersErrorsServiceImpl implements VotMembersErrorsService {
    @Autowired
 private VotMembersErrorsDao votMemberErrorsDao;
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)  
    @Override
    public void save(VotMembersErrors votMembersErrors){
    votMemberErrorsDao.save(votMembersErrors);
    }
    public List<VotMembersErrors> listAllVotMembersErrorsBybatch(int agmId,String referenceNumber){
        return votMemberErrorsDao.listAllVotMembersErrorsBybatch(agmId, referenceNumber);
    }
    public boolean votMembersErrorsexist(int agmId,String referenceNumber){
        boolean validity =false;
        return votMemberErrorsDao.votMembersErrorsexist(agmId, referenceNumber);
    }
    public List<VotMembersErrors> listAllVotMembersErrorsBybatch(String referenceNumber){
        return votMemberErrorsDao.listAllVotMembersErrorsBybatch(referenceNumber);
    }
}
