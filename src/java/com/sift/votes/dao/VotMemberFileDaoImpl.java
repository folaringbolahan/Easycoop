/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotMemberFile;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("votMemberFileDao")
public class VotMemberFileDaoImpl implements VotMemberFileDao{
     @Autowired  
 private SessionFactory sessionFactory; 
     
  public void saveMemberFile(VotMemberFile votmemberfile){
   sessionFactory.getCurrentSession().saveOrUpdate(votmemberfile);
  }
}
