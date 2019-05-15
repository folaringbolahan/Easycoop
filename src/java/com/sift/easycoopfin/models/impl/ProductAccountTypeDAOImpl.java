
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.ProductAccountType;
import com.sift.easycoopfin.models.ProductAccountTypeCriteria;
import com.sift.easycoopfin.models.ProductType;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class ProductAccountTypeDAOImpl implements com.sift.easycoopfin.models.dao.ProductAccountTypeDAO {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(ProductAccountTypeDAOImpl.class);
	public ProductAccountType loadProductAccountTypeByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadProductAccountTypeByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadProductAccountTypeByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType getProductAccountTypeByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getProductAccountTypeByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getProductAccountTypeByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType loadProductAccountTypeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadProductAccountTypeByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadProductAccountTypeByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType getProductAccountTypeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getProductAccountTypeByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getProductAccountTypeByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType loadProductAccountTypeByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (ProductAccountType) session.load(com.sift.easycoopfin.models.ProductAccountType.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadProductAccountTypeByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType getProductAccountTypeByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (ProductAccountType) session.get(com.sift.easycoopfin.models.ProductAccountType.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getProductAccountTypeByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType loadProductAccountTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (ProductAccountType) session.load(com.sift.easycoopfin.models.ProductAccountType.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadProductAccountTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType getProductAccountTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (ProductAccountType) session.get(com.sift.easycoopfin.models.ProductAccountType.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getProductAccountTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType[] listProductAccountTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listProductAccountTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listProductAccountTypeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType[] listProductAccountTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listProductAccountTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listProductAccountTypeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType[] listProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.ProductAccountType as ProductAccountType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (ProductAccountType[]) list.toArray(new ProductAccountType[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType[] listProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.ProductAccountType as ProductAccountType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (ProductAccountType[]) list.toArray(new ProductAccountType[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType loadProductAccountTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadProductAccountTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadProductAccountTypeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType loadProductAccountTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadProductAccountTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadProductAccountTypeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType loadProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		ProductAccountType[] productAccountTypes = listProductAccountTypeByQuery(session, condition, orderBy);
		if (productAccountTypes != null && productAccountTypes.length > 0)
			return productAccountTypes[0];
		else
			return null;
	}
	
	public ProductAccountType loadProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		ProductAccountType[] productAccountTypes = listProductAccountTypeByQuery(session, condition, orderBy, lockMode);
		if (productAccountTypes != null && productAccountTypes.length > 0)
			return productAccountTypes[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateProductAccountTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateProductAccountTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateProductAccountTypeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateProductAccountTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateProductAccountTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateProductAccountTypeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.ProductAccountType as ProductAccountType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.ProductAccountType as ProductAccountType");
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
			_logger.error("iterateProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType createProductAccountType() {
		return new com.sift.easycoopfin.models.ProductAccountType();
	}
	
	public boolean save(com.sift.easycoopfin.models.ProductAccountType productAccountType) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(productAccountType);
			return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.models.ProductAccountType productAccountType)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.ProductAccountType productAccountType) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(productAccountType);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.models.ProductAccountType productAccountType)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.ProductAccountType productAccountType) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(productAccountType);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.models.ProductAccountType productAccountType)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.ProductAccountType productAccountType) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(productAccountType);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.models.ProductAccountType productAccountType)", e);
			throw new PersistentException(e);
		}
	}
	
	public ProductAccountType loadProductAccountTypeByCriteria(ProductAccountTypeCriteria productAccountTypeCriteria) {
		ProductAccountType[] productAccountTypes = listProductAccountTypeByCriteria(productAccountTypeCriteria);
		if(productAccountTypes == null || productAccountTypes.length == 0) {
			return null;
		}
		return productAccountTypes[0];
	}
	
	public ProductAccountType[] listProductAccountTypeByCriteria(ProductAccountTypeCriteria productAccountTypeCriteria) {
		return productAccountTypeCriteria.listProductAccountType();
	}

    @Override
    public List<ProductAccountType> listAllProductAccountTypeByQuery(String condition, String orderBy) throws PersistentException {
        PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductAccountType as ProductAccountType");
        if (condition != null) {
            sb.append(" Where ").append(condition);
            
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List<ProductAccountType> list = query.list();
            return list;
            //return (Company[]) list.toArray(new Company[list.size()]);
        } catch (Exception e) {
            _logger.error("listAllProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    
    }
}
