/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.ProductAccountVal;
import com.sift.easycoopfin.models.ProductAccountValCriteria;
import com.sift.easycoopfin.models.dao.ProductAccountValDAO;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.orm.PersistentException;
import org.orm.PersistentSession;

/**
 *
 * @author logzy
 */
public class ProductAccountValDAOImpl implements ProductAccountValDAO{

   private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(ProductAccountValDAOImpl.class);
	public ProductAccountVal loadProductAccountByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadProductAccountByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadProductAccountByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal getProductAccountByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getProductAccountByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getProductAccountByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal loadProductAccountByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadProductAccountByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadProductAccountByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal getProductAccountByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getProductAccountByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getProductAccountByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal loadProductAccountByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (ProductAccountVal) session.load(com.sift.easycoopfin.models.ProductAccountVal.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadProductAccountByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal getProductAccountByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (ProductAccountVal) session.get(com.sift.easycoopfin.models.ProductAccountVal.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getProductAccountByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal loadProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (ProductAccountVal) session.load(com.sift.easycoopfin.models.ProductAccountVal.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal getProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (ProductAccountVal) session.get(com.sift.easycoopfin.models.ProductAccountVal.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal[] listProductAccountByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listProductAccountByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listProductAccountByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal[] listProductAccountByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listProductAccountByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listProductAccountByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal[] listProductAccountByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductAccountVal as ProductAccountVal");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (ProductAccountVal[]) list.toArray(new ProductAccountVal[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listProductAccountByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal[] listProductAccountByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductAccountVal as ProductAccountVal");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (ProductAccountVal[]) list.toArray(new ProductAccountVal[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listProductAccountByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal loadProductAccountByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadProductAccountByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadProductAccountByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal loadProductAccountByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadProductAccountByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadProductAccountByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal loadProductAccountByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		ProductAccountVal[] productAccounts = listProductAccountByQuery(session, condition, orderBy);
		if (productAccounts != null && productAccounts.length > 0)
			return productAccounts[0];
		else
			return null;
	}
	
	public ProductAccountVal loadProductAccountByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		ProductAccountVal[] productAccounts = listProductAccountByQuery(session, condition, orderBy, lockMode);
		if (productAccounts != null && productAccounts.length > 0)
			return productAccounts[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateProductAccountByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateProductAccountByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateProductAccountByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateProductAccountByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateProductAccountByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateProductAccountByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateProductAccountByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductAccountVal as ProductAccountVal");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateProductAccountByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateProductAccountByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductAccountVal as ProductAccountVal");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateProductAccountByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal createProductAccount() {
		return new com.sift.easycoopfin.models.ProductAccountVal();
	}
	
	public boolean save(com.sift.easycoopfin.models.ProductAccountVal productAccount) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(productAccount);
			
                        return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.easycoopfin.models.ProductAccountVal productAccountVal)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.ProductAccountVal productAccount) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(productAccount);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.easycoopfin.models.ProductAccountVal ProductAccountVal)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.ProductAccountVal productAccount) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(productAccount);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.easycoopfin.models.ProductAccountVal ProductAccountVal)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.ProductAccountVal productAccount) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(productAccount);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.easycoopfin.models.ProductAccountVal ProductAccountVal)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountVal loadProductAccountByCriteria(ProductAccountValCriteria productAccountCriteria) {
		ProductAccountVal[] productAccounts = listProductAccountByCriteria(productAccountCriteria);
		if(productAccounts == null || productAccounts.length == 0) {
			return null;
		}
		return productAccounts[0];
	}
	
	public ProductAccountVal[] listProductAccountByCriteria(ProductAccountValCriteria productAccountCriteria) {
		return productAccountCriteria.listProductAccountVal();
	}

    @Override
    public List<ProductAccountVal> listAllProductAccountByCriteria(ProductAccountValCriteria productAccountCriteria) {
        return productAccountCriteria.listAllProductAccountVal();
    }

    @Override
    public boolean add(ProductAccountVal productAccount) throws PersistentException {
         PersistentSession session;
         // PersistentTransaction t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
         boolean status = false;
        try {
             session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
             session.saveOrUpdate(productAccount);
     
        
        } catch (PersistentException ex) {
          _logger.error("evict(ProductAccountVal productAccount)", ex);
        }
        return status;
    }
    
}
