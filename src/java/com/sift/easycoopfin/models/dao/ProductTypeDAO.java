
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.ProductType;
import com.sift.easycoopfin.models.ProductTypeCriteria;
import java.util.List;
import org.orm.*;

public interface ProductTypeDAO {
	public ProductType loadProductTypeByORMID(int id) throws PersistentException;
	public ProductType getProductTypeByORMID(int id) throws PersistentException;
	public ProductType loadProductTypeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductType getProductTypeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductType loadProductTypeByORMID(PersistentSession session, int id) throws PersistentException;
	public ProductType getProductTypeByORMID(PersistentSession session, int id) throws PersistentException;
	public ProductType loadProductTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductType getProductTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
        public List<ProductType> listAllProductTypeByQuery(String condition, String orderBy) throws PersistentException;
	public ProductType[] listProductTypeByQuery(String condition, String orderBy) throws PersistentException;
	public ProductType[] listProductTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductType[] listProductTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public ProductType[] listProductTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductType loadProductTypeByQuery(String condition, String orderBy) throws PersistentException;
	public ProductType loadProductTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductType loadProductTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public ProductType loadProductTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductType createProductType();
	public boolean save(com.sift.easycoopfin.models.ProductType productType) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.ProductType productType) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.ProductType productType) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.ProductType productType) throws PersistentException;
	public ProductType loadProductTypeByCriteria(ProductTypeCriteria productTypeCriteria);
	public ProductType[] listProductTypeByCriteria(ProductTypeCriteria productTypeCriteria);
        public List<ProductType> listAllProductAccountByCriteria(ProductTypeCriteria productTypeCriteria);
}
