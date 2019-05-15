/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.dao;

import com.sift.admin.model.Bank;
import com.sift.admin.model.Country;
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
@Repository("bankDao")
@Transactional
public class BankDaoImpl implements BankDao {

    @Autowired
    public SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public boolean addOrUpdateBank(Bank bank) {
        boolean success = false;
        try {
            this.sessionFactory.getCurrentSession().saveOrUpdate(bank);
            //sessionFactory.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean deleteBank(int bankId) {
        boolean success = false;
        Bank bank = (Bank) sessionFactory.getCurrentSession().load(Bank.class, bankId);
        if (null != bank) {
            this.sessionFactory.getCurrentSession().delete(bank);
            success = true;
        }
        return success;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Bank> listBankByCountry(int countryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bank.class);
        criteria.add(Restrictions.eq("countryId", countryId));

        return criteria.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Bank getBankById(int id) {
        return (Bank) this.sessionFactory.getCurrentSession().get(Bank.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Bank> listAllBanks() {
        ArrayList<Bank> list = null;
        Bank bank = null;
        
        String sql = "SELECT b.bank_Id , b.bank_Name, b.country_Id ,"
                + " b.bank_Code , c.country_Name   FROM "
                + "banks AS b, Countries AS c WHERE"
                + " b.country_Id = c.id ORDER BY b.country_Id ASC";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;
        
        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<Bank>();
            }
            bank = new Bank();

            Object[] row = results.next();

            bank.setBankId((Integer) row[i++]);
            bank.setBankName((String) row[i++]);
            bank.setCountryId((Integer) row[i++]);
            bank.setBankCode((String) row[i++]);
            bank.setCountryName((String) row[i++]);
            
            list.add(bank);            
        }

        return list;

        
    }

    /*
     @Override
     public List<Bank> listAllBanks() {
     Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bank.class);        
     criteria.addOrder(Order.asc("countryId"));
        
     return criteria.list();     
     }
     */
    @Override
    public String getCountryName(int countryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Country.class);
        criteria.add(Restrictions.eq("id", countryId));
        criteria.setProjection(Projections.property("countryName")); // countryname alone
        List result = criteria.list();
        String countryName = "";
        for (Iterator it = result.iterator(); it.hasNext();) {
            //Object[] myResult = (Object[]) it.next();
            countryName = String.valueOf(it.next());
        }
        return countryName;
    }

}
