/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.votes.model.VotCompany;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("votCompanyDao")
public class VotCompanyDaoImpl implements VotCompanyDao {
    @Autowired  
 private SessionFactory sessionFactory;  
    
  @SuppressWarnings("unchecked")  
 public List<VotCompany> listCompanies(){  
      List<VotCompany> vc_list = sessionFactory.getCurrentSession().createCriteria(VotCompany.class).list();  
      System.out.println("size from dao "+ vc_list.size());
    return (List<VotCompany>) sessionFactory.getCurrentSession().createCriteria(VotCompany.class).list();  
 }   
   public VotCompany getVotCompany(int id){  
    return (VotCompany) sessionFactory.getCurrentSession().get(VotCompany.class,id);  
 } 
  public void addCompanySetup(VotCompany votcompany){
   sessionFactory.getCurrentSession().saveOrUpdate(votcompany);
  }
  public VotCompany getLastRecord(){ 
   Query query = sessionFactory.getCurrentSession().createQuery("from VotCompany order by companyid DESC");
    query.setMaxResults(1);
   VotCompany last = (VotCompany) query.uniqueResult();  
   return last;
 }  
}
