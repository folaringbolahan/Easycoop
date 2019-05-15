/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.dao;

import com.sift.gl.model.MemberImp;
import com.sift.gl.model.Products;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Aowtunbo
 */

@Repository("memberImpDao")
@Transactional
public class MemberImpDaoImpl implements MemberImpDao{

    @Autowired
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    
    @Override
    public boolean addOrUpdateMemberImp(MemberImp memberImp) {
         boolean success = false;
        try {
            this.sessionFactory.getCurrentSession().saveOrUpdate(memberImp);
            //sessionFactory.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    //@SuppressWarnings("unchecked")
    public List<MemberImp> listMembersByBranch(int companyId, int branchId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberImp.class);
        int statusId = 2;
        criteria.add(Restrictions.eq("companyId", companyId));
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("statusId", statusId));
        
        return criteria.list();        
    }

    @Override
    public ArrayList<MemberImp> listAllMembers() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberImp.class);
        criteria.add(Restrictions.eq("branchId", 26));

        return (ArrayList<MemberImp>) criteria.list();
    }

    @Override
    public MemberImp getMemberById(int companyId, int branchId, String memberNo) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberImp.class);
        criteria.add(Restrictions.eq("companyId", companyId));
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("memberNo", memberNo));
        
        return criteria.list().size()==0?null:(MemberImp)criteria.list().get(0);
    }

    @Override
    public String getCountryName(int countryId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Products> listActiveProductsByBranch(int companyId, int branchId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Products.class);
       
        //criteria.setProjection(Projections.property("id"));
        //criteria.setProjection(Projections.property("name"));
        criteria.add(Restrictions.eq("companyId", companyId));
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("isActive", 1));
        
        return (List<Products>)criteria.list();
    }

    @Override
    public boolean memberExist(String memberNo) {
        //Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MemberImp.class);
        //System.out.println("Member No inside Implementation : " + memberNo);
        //criteria.add(Restrictions.eq("memberNo", memberNo));
        
        
         long row = 0;
        Query query = sessionFactory.getCurrentSession().createQuery("select count(memberNo) from MemberImp where memberNo='" + memberNo + "'");

        for (Iterator it = query.iterate(); it.hasNext();) {
            row = (Long) it.next();
        }

        //System.out.println("row=:" + row);

        return row > 0 ? true : false;
        
        
        //return criteria.list().size() > 0 ? true : false;
    }

    @Override
    public int getMemberId(int companyId, int branchId, String memberNo) {
        int row=0;
        
         Query query = sessionFactory.getCurrentSession().
                 createQuery("SELECT memberId FROM MemberImp WHERE companyId ='"+companyId+"' and branchId ='"+branchId+"'and memberNo ='"+memberNo+"'");
         // iterate through the query
         for(Iterator it = query.iterate(); it.hasNext();){
             row = Integer.valueOf(String.valueOf(it.next()));
         }	 
	 return  row;  
        
       
    }

    
    
}
