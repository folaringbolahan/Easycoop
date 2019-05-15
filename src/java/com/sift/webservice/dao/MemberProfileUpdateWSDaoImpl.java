/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.webservice.dao;

import com.sift.webservice.model.MemberProfileUpdateWS;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nelson Akpos
 */
@Repository("MemberProfileUpdateWSDao")
public class MemberProfileUpdateWSDaoImpl implements MemberProfileUpdateWSDao{
    @Autowired
    private SessionFactory sessionFactory;
    
     @Override
    public void saveMemberUpdate(MemberProfileUpdateWS memberProfileUpdateWS) {
       this.sessionFactory.getCurrentSession().save(memberProfileUpdateWS);
    }
    
}
