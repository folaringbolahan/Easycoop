/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.impl;
import com.sift.easycoopfin.models.Currency;
import com.sift.easycoopfin.models.Products;
import java.util.List;
import org.hibernate.Query;
import org.orm.*;
/**
 *
 * @author logzy
 */
public class CurrencyDAOImpl implements com.sift.easycoopfin.models.dao.CurrencyDAO{
  private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(CurrencyDAOImpl.class);
   
    public List<Currency> listCurrencyByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listCurrencyByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listCurrencyByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }
     public List<Currency> listCurrencyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.Currency as Currency");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List<Currency> list = query.list();
            //return (Products[]) list.toArray(new Products[list.size()]);
            return list;
        } catch (Exception e) {
            _logger.error("listCurrencyByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }
}
