package com.sift.easycoopfin.models.dao;

import com.sift.admin.model.InterestType;
import com.sift.easycoopfin.models.Currency;
import com.sift.easycoopfin.models.CurrencyCriteria;
import com.sift.easycoopfin.models.ProductsCriteria;
import com.sift.easycoopfin.models.Products;
import java.util.List;
import org.orm.*;

public interface ProductsDAO {

    public Products loadProductsByORMID(int id) throws PersistentException;

    public Products getProductsByORMID(int id) throws PersistentException;

    public Products loadProductsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;

    public Products getProductsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;

    public Products loadProductsByORMID(PersistentSession session, int id) throws PersistentException;

    public Products getProductsByORMID(PersistentSession session, int id) throws PersistentException;

    public Products loadProductsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;

    public Products getProductsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;

    public Products[] listProductsByQuery(String condition, String orderBy) throws PersistentException;

    public Products[] listProductsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;

    public Products[] listProductsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;

    public Products[] listProductsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;

    public List<Products> listAllProductsByQuery(String condition, String orderBy) throws PersistentException;

    public List<Products> lisAlltProductsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;

    public List<Products> listAllProductsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;

    public List<Products> listAllProductsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;

    public Products loadProductsByQuery(String condition, String orderBy) throws PersistentException;

    public Products loadProductsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;

    public Products loadProductsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;

    public Products loadProductsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;

    public Products createProducts();

    public boolean save(com.sift.easycoopfin.models.Products products) throws PersistentException;

    public boolean delete(com.sift.easycoopfin.models.Products products) throws PersistentException;

    public boolean refresh(com.sift.easycoopfin.models.Products products) throws PersistentException;

    public boolean evict(com.sift.easycoopfin.models.Products products) throws PersistentException;

    public Products loadProductsByCriteria(ProductsCriteria productsCriteria);

    public Products[] listProductsByCriteria(ProductsCriteria productsCriteria);

    public Long getLastInseriID() throws PersistentException;

    public List<Products> listAllProductsByCriteria(ProductsCriteria productsCriteria);

    public Currency getCurrencyByCode(CurrencyCriteria currencyCriteria);

    public boolean add(com.sift.easycoopfin.models.Products products) throws PersistentException; //public Long save(PersistentSession session,com.sift.easycoopfin.models.Products products) throws PersistentException;
    
    public List<InterestType> listInterestTypes();
    public List<InterestType> listPurchasesInterest();
    public List<InterestType> listAllInterestTypes();

}
