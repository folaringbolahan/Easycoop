
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.ProductAccountType;
import com.sift.easycoopfin.models.ProductAccountTypeCriteria;
import java.util.List;
import org.orm.*;

public interface ProductAccountTypeDAO {
	public ProductAccountType loadProductAccountTypeByORMID(int id) throws PersistentException;
	public ProductAccountType getProductAccountTypeByORMID(int id) throws PersistentException;
	public ProductAccountType loadProductAccountTypeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountType getProductAccountTypeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountType loadProductAccountTypeByORMID(PersistentSession session, int id) throws PersistentException;
	public ProductAccountType getProductAccountTypeByORMID(PersistentSession session, int id) throws PersistentException;
	public ProductAccountType loadProductAccountTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountType getProductAccountTypeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public List<ProductAccountType> listAllProductAccountTypeByQuery(String condition, String orderBy) throws PersistentException;
        public ProductAccountType[] listProductAccountTypeByQuery(String condition, String orderBy) throws PersistentException;
	public ProductAccountType[] listProductAccountTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountType[] listProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public ProductAccountType[] listProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountType loadProductAccountTypeByQuery(String condition, String orderBy) throws PersistentException;
	public ProductAccountType loadProductAccountTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountType loadProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public ProductAccountType loadProductAccountTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountType createProductAccountType();
	public boolean save(com.sift.easycoopfin.models.ProductAccountType productAccountType) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.ProductAccountType productAccountType) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.ProductAccountType productAccountType) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.ProductAccountType productAccountType) throws PersistentException;
	public ProductAccountType loadProductAccountTypeByCriteria(ProductAccountTypeCriteria productAccountTypeCriteria);
	public ProductAccountType[] listProductAccountTypeByCriteria(ProductAccountTypeCriteria productAccountTypeCriteria);
}
