/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotAgmDeactivationDao;
import com.sift.votes.model.VotAgmDeactivation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votAgmDeactivationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotAgmDeactivationServiceImpl implements VotAgmDeactivationService {
      @Autowired
	 private VotAgmDeactivationDao votAgmDeactivationDao;
    public List<VotAgmDeactivation> listAgmsforDeactivation(){
    return  votAgmDeactivationDao.listAgmsforDeactivation();
    }
    
}
