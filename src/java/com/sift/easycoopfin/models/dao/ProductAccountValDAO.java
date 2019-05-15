/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.dao;
import com.sift.easycoopfin.models.ProductAccountVal;
import com.sift.easycoopfin.models.ProductAccountValCriteria;
import java.util.List;
import org.orm.*;
/**
 *
 * @author logzy
 */
public interface ProductAccountValDAO {
    public ProductAccountVal loadProductAccountByORMID(int id) throws PersistentException;
	public ProductAccountVal getProductAccountByORMID(int id) throws PersistentException;
	public ProductAccountVal loadProductAccountByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountVal getProductAccountByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountVal loadProductAccountByORMID(PersistentSession session, int id) throws PersistentException;
	public ProductAccountVal getProductAccountByORMID(PersistentSession session, int id) throws PersistentException;
	public ProductAccountVal loadProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountVal getProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountVal[] listProductAccountByQuery(String condition, String orderBy) throws PersistentException;
	public ProductAccountVal[] listProductAccountByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountVal[] listProductAccountByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public ProductAccountVal[] listProductAccountByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountVal loadProductAccountByQuery(String condition, String orderBy) throws PersistentException;
	public ProductAccountVal loadProductAccountByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountVal loadProductAccountByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public ProductAccountVal loadProductAccountByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccountVal createProductAccount();
	public boolean save(com.sift.easycoopfin.models.ProductAccountVal productAccount) throws PersistentException;
        public boolean add(com.sift.easycoopfin.models.ProductAccountVal productAccount) throws PersistentException;
    
	public boolean delete(com.sift.easycoopfin.models.ProductAccountVal productAccount) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.ProductAccountVal productAccount) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.ProductAccountVal productAccount) throws PersistentException;
	public ProductAccountVal loadProductAccountByCriteria(ProductAccountValCriteria productAccountCriteria);
	public ProductAccountVal[] listProductAccountByCriteria(ProductAccountValCriteria productAccountCriteria);
        public List<ProductAccountVal> listAllProductAccountByCriteria(ProductAccountValCriteria productAccountCriteria);
}
