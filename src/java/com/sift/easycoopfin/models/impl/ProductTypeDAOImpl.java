package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.ProductType;
import com.sift.easycoopfin.models.ProductTypeCriteria;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class ProductTypeDAOImpl implements com.sift.easycoopfin.models.dao.ProductTypeDAO {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(ProductTypeDAOImpl.class);

    public ProductType loadProductTypeByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductTypeByORMID(session, id);
        } catch (Exception e) {
            _logger.error("loadProductTypeByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType getProductTypeByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getProductTypeByORMID(session, id);
        } catch (Exception e) {
            _logger.error("getProductTypeByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType loadProductTypeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductTypeByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("loadProductTypeByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType getProductTypeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getProductTypeByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("getProductTypeByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType loadProductTypeByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (ProductType) session.load(com.sift.easycoopfin.models.ProductType.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("loadProductTypeByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType getProductTypeByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (ProductType) session.get(com.sift.easycoopfin.models.ProductType.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("getProductTypeByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType loadProductTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (ProductType) session.load(com.sift.easycoopfin.models.ProductType.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("loadProductTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType getProductTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (ProductType) session.get(com.sift.easycoopfin.models.ProductType.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("getProductTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType[] listProductTypeByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listProductTypeByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listProductTypeByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType[] listProductTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listProductTypeByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("listProductTypeByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType[] listProductTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductType as ProductType");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List list = query.list();
            return (ProductType[]) list.toArray(new ProductType[list.size()]);
        } catch (Exception e) {
            _logger.error("listProductTypeByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType[] listProductTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductType as ProductType");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            query.setLockMode("this", lockMode);
            List list = query.list();
            return (ProductType[]) list.toArray(new ProductType[list.size()]);
        } catch (Exception e) {
            _logger.error("listProductTypeByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType loadProductTypeByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductTypeByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("loadProductTypeByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType loadProductTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductTypeByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("loadProductTypeByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType loadProductTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        ProductType[] productTypes = listProductTypeByQuery(session, condition, orderBy);
        if (productTypes != null && productTypes.length > 0) {
            return productTypes[0];
        } else {
            return null;
        }
    }

    public ProductType loadProductTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        ProductType[] productTypes = listProductTypeByQuery(session, condition, orderBy, lockMode);
        if (productTypes != null && productTypes.length > 0) {
            return productTypes[0];
        } else {
            return null;
        }
    }

    public static java.util.Iterator iterateProductTypeByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateProductTypeByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("iterateProductTypeByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateProductTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateProductTypeByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("iterateProductTypeByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateProductTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductType as ProductType");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            return query.iterate();
        } catch (Exception e) {
            _logger.error("iterateProductTypeByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateProductTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductType as ProductType");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            query.setLockMode("this", lockMode);
            return query.iterate();
        } catch (Exception e) {
            _logger.error("iterateProductTypeByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType createProductType() {
        return new com.sift.easycoopfin.models.ProductType();
    }

    public boolean save(com.sift.easycoopfin.models.ProductType productType) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(productType);
            return true;
        } catch (Exception e) {
            _logger.error("save(com.sift.models.ProductType productType)", e);
            throw new PersistentException(e);
        }
    }

    public boolean delete(com.sift.easycoopfin.models.ProductType productType) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(productType);
            return true;
        } catch (Exception e) {
            _logger.error("delete(com.sift.models.ProductType productType)", e);
            throw new PersistentException(e);
        }
    }

    public boolean refresh(com.sift.easycoopfin.models.ProductType productType) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(productType);
            return true;
        } catch (Exception e) {
            _logger.error("refresh(com.sift.easycoopfin.models.ProductType productType)", e);
            throw new PersistentException(e);
        }
    }

    public boolean evict(com.sift.easycoopfin.models.ProductType productType) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(productType);
            return true;
        } catch (Exception e) {
            _logger.error("evict(com.sift.easycoopfin.models.ProductType productType)", e);
            throw new PersistentException(e);
        }
    }

    public ProductType loadProductTypeByCriteria(ProductTypeCriteria productTypeCriteria) {
        ProductType[] productTypes = listProductTypeByCriteria(productTypeCriteria);
        if (productTypes == null || productTypes.length == 0) {
            return null;
        }
        return productTypes[0];
    }

    public ProductType[] listProductTypeByCriteria(ProductTypeCriteria productTypeCriteria) {
        return productTypeCriteria.listProductType();
    }

    public List<ProductType> listAllProductTypeByQuery(String condition, String orderBy) throws PersistentException {
        PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductType as ProductType");
        if (condition != null) {
            sb.append(" Where ").append(condition);
            
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List<ProductType> list = query.list();
            return list;
            //return (Company[]) list.toArray(new Company[list.size()]);
        } catch (Exception e) {
            _logger.error("listAllProductTypeByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public List<ProductType> listAllProductAccountByCriteria(ProductTypeCriteria productTypeCriteria) {
       return productTypeCriteria.listAllProductType();
    }
    
    
}
