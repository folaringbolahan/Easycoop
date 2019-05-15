/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.webservice.dao;

import com.sift.admin.model.AddressEntries;
import com.sift.webservice.model.LoanGuarantorWS;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Olakunle Awotunbo
 */
@Repository("loanGuarantorWSDAO")
public class LoanGuarantorWSDaoImpl implements LoanGuarantorWSDAO{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void saveGuarantor(LoanGuarantorWS loanGuarantor) {
       this.sessionFactory.getCurrentSession().save(loanGuarantor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LoanGuarantorWS> getAllLoanGuarantor() {
        return this.sessionFactory.getCurrentSession().createQuery("from LoanGuarantorWS").list();
         
    }

    @Override
    public String getMemberCorreAddress(String memberId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AddressEntries.class);
        String typeId = "5";
        criteria.setProjection(Projections.property("addrFieldValue")); // addrFieldValue alone
         criteria.add(Restrictions.eq("keyId",memberId));
	 criteria.add(Restrictions.eq("typeId",typeId));        
        criteria.addOrder(Order.asc("serialPos"));
        
        List result = criteria.list();
        String sep = ", ";    
        // StringUtils joins it to array list to string
        String address = StringUtils.join(result, sep);
        
        return address;
    }
    
}
