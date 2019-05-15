package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.ProductAccount;
import com.sift.easycoopfin.models.ProductAccountCriteria;
import com.sift.easycoopfin.models.ProductAccountRel;
import com.sift.easycoopfin.models.ProductAccountType;
import java.util.ArrayList;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;
import org.hibernate.SQLQuery;

public class ProductAccountDAOImpl implements com.sift.easycoopfin.models.dao.ProductAccountDAO {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(ProductAccountDAOImpl.class);

    public ProductAccount loadProductAccountByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductAccountByORMID(session, id);
        } catch (Exception e) {
            _logger.error("loadProductAccountByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount getProductAccountByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getProductAccountByORMID(session, id);
        } catch (Exception e) {
            _logger.error("getProductAccountByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount loadProductAccountByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductAccountByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("loadProductAccountByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount getProductAccountByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getProductAccountByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("getProductAccountByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount loadProductAccountByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (ProductAccount) session.load(com.sift.easycoopfin.models.ProductAccount.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("loadProductAccountByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount getProductAccountByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (ProductAccount) session.get(com.sift.easycoopfin.models.ProductAccount.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("getProductAccountByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount loadProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (ProductAccount) session.load(com.sift.easycoopfin.models.ProductAccount.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("loadProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount getProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (ProductAccount) session.get(com.sift.easycoopfin.models.ProductAccount.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("getProductAccountByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount[] listProductAccountByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listProductAccountByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listProductAccountByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount[] listProductAccountByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listProductAccountByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("listProductAccountByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount[] listProductAccountByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductAccount as ProductAccount");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List list = query.list();
            return (ProductAccount[]) list.toArray(new ProductAccount[list.size()]);
        } catch (Exception e) {
            _logger.error("listProductAccountByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount[] listProductAccountByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.models.ProductAccount as ProductAccount");
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
            return (ProductAccount[]) list.toArray(new ProductAccount[list.size()]);
        } catch (Exception e) {
            _logger.error("listProductAccountByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount loadProductAccountByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductAccountByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("loadProductAccountByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount loadProductAccountByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadProductAccountByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("loadProductAccountByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount loadProductAccountByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        ProductAccount[] productAccounts = listProductAccountByQuery(session, condition, orderBy);
        if (productAccounts != null && productAccounts.length > 0) {
            return productAccounts[0];
        } else {
            return null;
        }
    }

    public ProductAccount loadProductAccountByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        ProductAccount[] productAccounts = listProductAccountByQuery(session, condition, orderBy, lockMode);
        if (productAccounts != null && productAccounts.length > 0) {
            return productAccounts[0];
        } else {
            return null;
        }
    }

    public static java.util.Iterator iterateProductAccountByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateProductAccountByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("iterateProductAccountByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateProductAccountByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateProductAccountByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("iterateProductAccountByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateProductAccountByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductAccount as ProductAccount");
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
            _logger.error("iterateProductAccountByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateProductAccountByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.ProductAccount as ProductAccount");
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
            _logger.error("iterateProductAccountByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount createProductAccount() {
        return new com.sift.easycoopfin.models.ProductAccount();
    }

    public boolean save(com.sift.easycoopfin.models.ProductAccount productAccount) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(productAccount);

            return true;
        } catch (Exception e) {
            _logger.error("save(com.sift.easycoopfin.models.ProductAccount productAccount)", e);
            throw new PersistentException(e);
        }
    }

    public boolean delete(com.sift.easycoopfin.models.ProductAccount productAccount) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(productAccount);
            return true;
        } catch (Exception e) {
            _logger.error("delete(com.sift.easycoopfin.models.ProductAccount ProductAccount)", e);
            throw new PersistentException(e);
        }
    }

    public boolean refresh(com.sift.easycoopfin.models.ProductAccount productAccount) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(productAccount);
            return true;
        } catch (Exception e) {
            _logger.error("refresh(com.sift.easycoopfin.models.ProductAccount ProductAccount)", e);
            throw new PersistentException(e);
        }
    }

    public boolean evict(com.sift.easycoopfin.models.ProductAccount productAccount) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(productAccount);
            return true;
        } catch (Exception e) {
            _logger.error("evict(com.sift.easycoopfin.models.ProductAccount ProductAccount)", e);
            throw new PersistentException(e);
        }
    }

    public ProductAccount loadProductAccountByCriteria(ProductAccountCriteria productAccountCriteria) {
        ProductAccount[] productAccounts = listProductAccountByCriteria(productAccountCriteria);
        if (productAccounts == null || productAccounts.length == 0) {
            return null;
        }
        return productAccounts[0];
    }

    public ProductAccount[] listProductAccountByCriteria(ProductAccountCriteria productAccountCriteria) {
        return productAccountCriteria.listProductAccount();
    }

    @Override
    public List<ProductAccount> listAllProductAccountByCriteria(ProductAccountCriteria productAccountCriteria) {
        return productAccountCriteria.listAllProductAccount();
    }

    @Override
    public boolean add(ProductAccount productAccount) throws PersistentException {
        PersistentSession session;
        // PersistentTransaction t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
        boolean status = false;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            session.saveOrUpdate(productAccount);


        } catch (PersistentException ex) {
            _logger.error("evict(ProductAccount productAccount)", ex);
        }
        return status;
    }

    @Override
    public List<ProductAccountRel> getAccountRelByProductId(int productId) {
        PersistentSession session;
        List<ProductAccountRel> rels = null;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            SQLQuery query = session.createSQLQuery("SELECT a.id,a.gl_account_number,a.product_id, a.product_account_type_code, b.description FROM productaccount a, productaccounttype b where b.code=a.product_account_type_code and a.product_id="+productId);

            List<Object[]> rows = query.list();

            if (!rows.isEmpty()) {
                rels = new ArrayList<ProductAccountRel>();
                for (Object[] row : rows) {
                    ProductAccountRel rel = new ProductAccountRel();
                    ProductAccount acc = new ProductAccount();
                    acc.setId(Integer.valueOf(row[0].toString()));
                    acc.setGlAccountNumber(row[1].toString());
                    acc.setProductId(Integer.valueOf(row[2].toString()));
                    acc.setProductAccountTypeCode(row[3].toString());

                    ProductAccountType accType = new ProductAccountType();
                    accType.setDescription(row[4].toString());

                    rel.setId(acc.getId());
                    rel.setGlAccountNumber(acc.getGlAccountNumber());
                    rel.setProductAccountTypeCode(acc.getProductAccountTypeCode());
                    rel.setDescription(accType.getDescription());

                    rels.add(rel);
                }
            }
        } catch (PersistentException ex) {
            _logger.error("Join Error", ex);
        }
        return rels;
    }
}
