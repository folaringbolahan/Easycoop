/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotMemberFileDao;
import com.sift.votes.model.VotMemberFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votMemberFileService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotMemberFileServiceImpl implements VotMemberFileService {
    @Autowired
    VotMemberFileDao votMemberFileDao;
    
    
    public void saveMemberFile(VotMemberFile votmemberfile){
    votMemberFileDao.saveMemberFile(votmemberfile);
    } 
}
