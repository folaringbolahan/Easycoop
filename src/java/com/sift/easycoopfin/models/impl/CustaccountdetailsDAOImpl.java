/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.Custaccountdetails;
import com.sift.easycoopfin.models.CustaccountdetailsCriteria;
import com.sift.easycoopfin.models.dao.CustaccountdetailsDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.orm.PersistentException;
import org.orm.PersistentSession;

/**
 *
 * @author logzy
 */
public class CustaccountdetailsDAOImpl implements CustaccountdetailsDAO {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(CustaccountdetailsDAOImpl.class);

    @Override
    public Custaccountdetails createCustaccountdetails() {
        return new com.sift.easycoopfin.models.Custaccountdetails();
    }

    @Override
    public List<Custaccountdetails> listAllAccountsByCriteria(CustaccountdetailsCriteria custaccountdetailsCriteria) {
        return custaccountdetailsCriteria.listAllCustaccountdetails();
    }

    @Override
    public List<Custaccountdetails> listAllAccountsByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listAllAccountsByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listProductsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }finally{
        
                com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().close();
         
        }
    }

    @Override
    public List<Custaccountdetails> listAllAccountsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.Custaccountdetails as Custaccountdetails");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List list = query.list();
            return (List<Custaccountdetails>) list;
        } catch (Exception e) {
            _logger.error("listAccountsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }

    }

    public Custaccountdetails loadAccountByCriteria(CustaccountdetailsCriteria custaccountdetailsCriteria) {
         Custaccountdetails[] accounts = listAccountByCriteria(custaccountdetailsCriteria);
        if (accounts == null || accounts.length == 0) {
            return null;
        }
        return accounts[0];
    }

    public Custaccountdetails[] listAccountByCriteria(CustaccountdetailsCriteria custaccountdetailsCriteria) {
       return custaccountdetailsCriteria.listCustaccountdetails();
    }
}
