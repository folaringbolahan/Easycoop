/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.admin.dao;

import com.sift.admin.model.Country;
import com.sift.admin.model.IdentificationDoc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Awotunbo
 */
@Repository("identificationDocDao")
@Transactional
public class IdentificationDocDaoImpl implements IdentificationDocDao{

    @Autowired
    SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory(){
                return sessionFactory;
    }
    
    @Override
    public boolean addOrUpdateIdentificationDoc(IdentificationDoc IdentificationDoc) {
        boolean success = false;   
        try {
            this.sessionFactory.getCurrentSession().saveOrUpdate(IdentificationDoc);            
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return success;
    }

    @Override
    public boolean deleteIdentificationDoc(int docId) {
        boolean success = false;
        IdentificationDoc doc = (IdentificationDoc) sessionFactory.getCurrentSession().load(IdentificationDoc.class, docId);
        if(null != doc ){
            this.sessionFactory.getCurrentSession().delete(doc);
            success = true;
        }
        return success;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public List<IdentificationDoc> listIdentificationDocByCountry(int countryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IdentificationDoc.class);
        criteria.add(Restrictions.eq("countryId", countryId));        
        
        return criteria.list();
    }

  
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<IdentificationDoc> listAllIdentificationDoc() {
         ArrayList<IdentificationDoc> list = null;
        IdentificationDoc idenDoc = null;
        
        String sql = "SELECT b. identification_doc_id,b.Identification_doc_name , b.Identification_doc_desc, b.country_Id ,"
                + " c.country_Name   FROM "
                + "identification_doc AS b, Countries AS c WHERE"
                + " b.country_Id = c.id ORDER BY b.country_Id ASC";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;        
        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<IdentificationDoc>();
            }
            idenDoc = new IdentificationDoc();

            Object[] row = results.next();            
            
            idenDoc.setIdentificationDocId((Integer) row[i++]);   
            idenDoc.setIdentificationdocname((String) row[i++]);
            idenDoc.setIdentificationdocdesc((String) row[i++]);
            idenDoc.setCountryId((Integer) row[i++]);            
            idenDoc.setCountryName((String) row[i++]);

            list.add(idenDoc);            
        }

        return list;
   
    }
    
    /*
    @Override
    public List<IdentificationDoc> listAllIdentificationDoc() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IdentificationDoc.class);
        criteria.addOrder(Order.asc("countryId"));
        
        return criteria.list();       
    }
    */

     @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public IdentificationDoc getIdentificationDocById(int docId) {
        return (IdentificationDoc) this.sessionFactory.getCurrentSession().get(IdentificationDoc.class, docId);
    }

    @Override
    public String getCountryName(int countryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Country.class);
        criteria.add(Restrictions.eq("id", countryId));
        criteria.setProjection(Projections.property("countryName")); // countryname alone
        List result =  criteria.list();
        String countryName = "";
        for (Iterator it = result.iterator(); it.hasNext(); ) {        
        countryName = String.valueOf(it.next());  
        }
        return countryName;
    }
    
}
