/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.dao.VotResolanswertypesDao;
import com.sift.votes.model.VotResolanswertypes;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Service("votResolanswertypesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotResolanswertypesServiceImpl implements VotResolanswertypesService {
   @Autowired
   private VotResolanswertypesDao votResolanswertypesDao;
    public List<VotResolanswertypes> listresolanswertypes(){
    return  votResolanswertypesDao.listresolanswertypes();
    }
}
