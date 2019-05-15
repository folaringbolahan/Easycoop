package com.sift.easycoopfin.models.impl;

import com.sift.admin.model.InterestType;
import com.sift.easycoopfin.models.Currency;
import com.sift.easycoopfin.models.CurrencyCriteria;
import com.sift.easycoopfin.models.ProductsCriteria;
import com.sift.easycoopfin.models.Products;
import java.math.BigInteger;
import java.util.ArrayList;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;

public class ProductsDAOImpl implements com.sift.easycoopfin.models.dao.ProductsDAO {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(ProductsDAOImpl.class);

    public Products loadProductsByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductsByORMID(session, id);
        } catch (Exception e) {
            _logger.error("loadProductsByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public Products getProductsByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getProductsByORMID(session, id);
        } catch (Exception e) {
            _logger.error("getProductsByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public Products loadProductsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductsByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("loadProductsByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Products getProductsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getProductsByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("getProductsByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Products loadProductsByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (Products) session.load(com.sift.easycoopfin.models.Products.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("loadProductsByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public Products getProductsByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (Products) session.get(com.sift.easycoopfin.models.Products.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("getProductsByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public Products loadProductsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (Products) session.load(com.sift.easycoopfin.models.Products.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("loadProductsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Products getProductsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (Products) session.get(com.sift.easycoopfin.models.Products.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("getProductsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Products[] listProductsByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listProductsByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listProductsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Products[] listProductsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listProductsByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("listProductsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Products[] listProductsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.Products as Products");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List list = query.list();
            return (Products[]) list.toArray(new Products[list.size()]);
        } catch (Exception e) {
            _logger.error("listProductsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Products[] listProductsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.Products as Products");
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
            return (Products[]) list.toArray(new Products[list.size()]);
        } catch (Exception e) {
            _logger.error("listProductsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Products loadProductsByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductsByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("loadProductsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Products loadProductsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductsByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("loadProductsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Products loadProductsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        Products[] productses = listProductsByQuery(session, condition, orderBy);
        if (productses != null && productses.length > 0) {
            return productses[0];
        } else {
            return null;
        }
    }

    public Products loadProductsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        Products[] productses = listProductsByQuery(session, condition, orderBy, lockMode);
        if (productses != null && productses.length > 0) {
            return productses[0];
        } else {
            return null;
        }
    }

    public static java.util.Iterator iterateProductsByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateProductsByQuery(session, condition, orderBy);

        } catch (Exception e) {
            _logger.error("iterateProductsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateProductsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateProductsByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("iterateProductsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateProductsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.Products as Products");
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
            _logger.error("iterateProductsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateProductsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.Products as Products");
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
            _logger.error("iterateProductsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Products createProducts() {
        return new com.sift.easycoopfin.models.Products();
    }

    public boolean save(com.sift.easycoopfin.models.Products products) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(products);

            return true;
        } catch (Exception e) {
            _logger.error("save(com.sift.easycoopfin.models.Products products)", e);
            throw new PersistentException(e);
        }
    }

    public boolean delete(com.sift.easycoopfin.models.Products products) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(products);
            return true;
        } catch (Exception e) {
            _logger.error("delete(com.sift.easycoopfin.models.Products products)", e);
            throw new PersistentException(e);
        }
    }

    public boolean refresh(com.sift.easycoopfin.models.Products products) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(products);
            return true;
        } catch (Exception e) {
            _logger.error("refresh(com.sift.easycoopfin.models.Products products)", e);
            throw new PersistentException(e);
        }
    }

    public boolean evict(com.sift.easycoopfin.models.Products products) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(products);
            return true;
        } catch (Exception e) {
            _logger.error("evict(com.sift.easycoopfin.models.Products products)", e);
            throw new PersistentException(e);
        }
    }

    public Products loadProductsByCriteria(ProductsCriteria productsCriteria) {
        Products[] products = listProductsByCriteria(productsCriteria);
        if (products == null || products.length == 0) {
            return null;
        }
        return products[0];
    }

    public Products[] listProductsByCriteria(ProductsCriteria productsCriteria) {
        return productsCriteria.listProducts();
    }

    public List<Products> listAllProductsByQuery(String condition, String orderBy) throws PersistentException {


        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listProductByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listProductsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public List<Products> lisAlltProductsByQuery(String condition, String orderBy, LockMode lockMode) throws PersistentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Products> listAllProductsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Products> listAllProductsByQuery(PersistentSession session, String condition, String orderBy, LockMode lockMode) throws PersistentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Products> listProductByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.Products as Products");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List<Products> list = query.list();
            //return (Products[]) list.toArray(new Products[list.size()]);
            return list;
        } catch (Exception e) {
            _logger.error("listProductsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Long getLastInseriID() throws PersistentException {
        PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
        Long lastId = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
        return lastId;
    }

    public Currency getCurrencyByCode(CurrencyCriteria currencyCriteria) {
        Currency[] currencies = currencyCriteria.listCurrency();
        if (currencies == null || currencies.length == 0) {
            return null;
        }
        return currencies[0];

    }

    @Override
    public List<Products> listAllProductsByCriteria(ProductsCriteria productsCriteria) {
        return productsCriteria.listAllProducts();
    }

    public List<InterestType> listAllInterestTypes() {
        PersistentSession session;
        List<InterestType> interestTypes = new ArrayList<InterestType>();
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            SQLQuery query = session.createSQLQuery("select * from `loan_interest_type` ");
            query.addEntity(InterestType.class);
            List<InterestType> rows = query.list();


            // return accounts;
        } catch (PersistentException ex) {
            _logger.error("listInterestTypes", ex);
        }
        return interestTypes;
    }

    public List<InterestType> listPurchasesInterest() {
        PersistentSession session;
        List<InterestType> interestTypes = new ArrayList<InterestType>();
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            SQLQuery query = session.createSQLQuery("select * from `hpintcalcmtd` ");

            List<Object[]> rows = query.list();
            if (!rows.isEmpty()) {
                for (Object[] row : rows) {
                    InterestType interest = new InterestType();
                    interest.setId(Integer.valueOf(row[0].toString()));
                    interest.setTypeCode(row[1].toString());
                    interest.setTypeName(row[2].toString());
                    interestTypes.add(interest);
                }
            }

            // return accounts;
        } catch (PersistentException ex) {
            _logger.error("listInterestTypes", ex);
        }
        return interestTypes;
    }

    public List<InterestType> listInterestTypes() {
        PersistentSession session;
        List<InterestType> interestTypes = new ArrayList<InterestType>();
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            //SQLQuery query = session.createSQLQuery("select * from `loan_interest_type` ");
            SQLQuery query = session.createSQLQuery("select * from `loan_interest_type` where active = 'y'");

            List<Object[]> rows = query.list();
            if (!rows.isEmpty()) {
                for (Object[] row : rows) {
                    InterestType interest = new InterestType();
                    interest.setId(Integer.valueOf(row[0].toString()));
                    //System.out.println("row[1].toString() :: " + row[1].toString());
                    interest.setActive(row[1].toString());
                    interest.setTypeCode(row[7].toString());
                    interest.setTypeName(row[8].toString());
                    interestTypes.add(interest);
                }
            }

            // return accounts;
        } catch (PersistentException ex) {
            _logger.error("listInterestTypes", ex);
        }
        //System.out.println("interestTypes :: " + interestTypes);
        return interestTypes;
    }

    public boolean add(com.sift.easycoopfin.models.Products products) throws PersistentException {
        PersistentSession session;

        boolean status = false;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            session.saveOrUpdate(products);
            /**
             * String sql = "INSERT INTO `products`(`code`, `is_deleted`,
             * `has_interest`, `is_default`, `is_taxable`, `has_penalty`,
             * `name`, `tax_code1`, `tax_code2`, `tax_code3`, `company_id`,
             * `branch_id`, `currency_id`, `initial_amount_max`,
             * `initial_amount_min`, `interest_rate_min`, `penalty`,
             * `interest_rate_max`, `interest_rate`, `segment_code`,
             * `product_type_code`)"; sql +=
             * "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
             *
             * int q = session.createSQLQuery(sql) .setParameter(0,
             * products.getCode()) .setParameter(1, products.getIsDeleted())
             * .setParameter(2, products.getHasInterest()) .setParameter(3,
             * products.getIsDefault()) .setParameter(4,
             * products.getIsTaxable()) .setParameter(5,
             * products.getHasPenalty()) .setParameter(6, products.getName())
             * .setParameter(7, products.getTaxCode1()) .setParameter(8,
             * products.getTaxCode2()) .setParameter(9, products.getTaxCode3())
             * .setParameter(10, products.getCompanyId()) .setParameter(11,
             * products.getBranchId()) .setParameter(12,
             * products.getCurrencyId()) .setParameter(13,
             * products.getInitialAmountMax()) .setParameter(14,
             * products.getInitialAmountMin()) .setParameter(15,
             * products.getInterestRateMin()) .setParameter(16,
             * products.getPenalty()) .setParameter(17,
             * products.getInterestRateMax()) .setParameter(18,
             * products.getInterestRate()) .setParameter(19,
             * products.getSegmentCode()) .setParameter(20,
             * products.getProductTypeCode()) .executeUpdate();
             *
             *
             * System.out.println("Query result: " + q); if (q > 0) { status =
             * true; } else { status = false; }
             *
             */
        } catch (PersistentException ex) {
            _logger.error("add(com.sift.easycoopfin.models.Products products)", ex);
        }
        return true;
    }
}
