/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotResolanswertypes;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Nelson Akpos
 */
@Repository("votResolanswertypesDao")
public class VotResolanswertypesDaoImpl implements VotResolanswertypesDao{
    @Autowired
    private SessionFactory sessionFactory;
  public List<VotResolanswertypes> listresolanswertypes(){
   return (List<VotResolanswertypes>) sessionFactory.getCurrentSession().createCriteria(VotResolanswertypes.class).list();
  }  
}
