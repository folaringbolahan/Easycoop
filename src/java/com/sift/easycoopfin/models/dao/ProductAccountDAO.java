
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.ProductAccount;
import com.sift.easycoopfin.models.ProductAccountCriteria;
import com.sift.easycoopfin.models.ProductAccountRel;
import java.util.List;
import org.orm.*;

public interface ProductAccountDAO {
	public ProductAccount loadProductAccountByORMID(int id) throws PersistentException;
	public ProductAccount getProductAccountByORMID(int id) throws PersistentException;
	public ProductAccount loadProductAccountByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccount getProductAccountByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccount loadProductAccountByORMID(PersistentSession session, int id) throws PersistentException;
	public ProductAccount getProductAccountByORMID(PersistentSession session, int id) throws PersistentException;
	public ProductAccount loadProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccount getProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccount[] listProductAccountByQuery(String condition, String orderBy) throws PersistentException;
	public ProductAccount[] listProductAccountByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccount[] listProductAccountByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public ProductAccount[] listProductAccountByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccount loadProductAccountByQuery(String condition, String orderBy) throws PersistentException;
	public ProductAccount loadProductAccountByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccount loadProductAccountByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public ProductAccount loadProductAccountByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public ProductAccount createProductAccount();
	public boolean save(com.sift.easycoopfin.models.ProductAccount productAccount) throws PersistentException;
        public boolean add(com.sift.easycoopfin.models.ProductAccount productAccount) throws PersistentException;
    
	public boolean delete(com.sift.easycoopfin.models.ProductAccount productAccount) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.ProductAccount productAccount) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.ProductAccount productAccount) throws PersistentException;
	public ProductAccount loadProductAccountByCriteria(ProductAccountCriteria productAccountCriteria);
	public ProductAccount[] listProductAccountByCriteria(ProductAccountCriteria productAccountCriteria);
        public List<ProductAccount> listAllProductAccountByCriteria(ProductAccountCriteria productAccountCriteria);
        public List<ProductAccountRel> getAccountRelByProductId(int productId);
}
