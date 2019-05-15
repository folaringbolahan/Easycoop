package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.Savings;
import com.sift.easycoopfin.models.SavingsAccount;
import com.sift.easycoopfin.models.SavingsCriteria;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

public class SavingsDAOImpl implements com.sift.easycoopfin.models.dao.SavingsDAO {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SavingsDAOImpl.class);
    PersistentTransaction t = null;

    public Savings loadSavingsByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();

            return loadSavingsByORMID(session, id);
        } catch (Exception e) {
            _logger.error("loadSavingsByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public Savings getSavingsByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getSavingsByORMID(session, id);
        } catch (Exception e) {
            _logger.error("getSavingsByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public Savings loadSavingsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadSavingsByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("loadSavingsByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Savings getSavingsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getSavingsByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("getSavingsByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Savings loadSavingsByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (Savings) session.load(com.sift.easycoopfin.models.Savings.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("loadSavingsByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public Savings getSavingsByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (Savings) session.get(com.sift.easycoopfin.models.Savings.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("getSavingsByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public Savings loadSavingsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (Savings) session.load(com.sift.easycoopfin.models.Savings.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("loadSavingsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Savings getSavingsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (Savings) session.get(com.sift.easycoopfin.models.Savings.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("getSavingsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Savings[] listSavingsByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listSavingsByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listSavingsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Savings[] listSavingsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listSavingsByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("listSavingsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Savings[] listSavingsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.models.Savings as Savings");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List list = query.list();
            return (Savings[]) list.toArray(new Savings[list.size()]);
        } catch (Exception e) {
            _logger.error("listSavingsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Savings[] listSavingsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.models.Savings as Savings");
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
            return (Savings[]) list.toArray(new Savings[list.size()]);
        } catch (Exception e) {
            _logger.error("listSavingsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Savings loadSavingsByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadSavingsByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("loadSavingsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Savings loadSavingsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadSavingsByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("loadSavingsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Savings loadSavingsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        Savings[] savingses = listSavingsByQuery(session, condition, orderBy);
        if (savingses != null && savingses.length > 0) {
            return savingses[0];
        } else {
            return null;
        }
    }

    public Savings loadSavingsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        Savings[] savingses = listSavingsByQuery(session, condition, orderBy, lockMode);
        if (savingses != null && savingses.length > 0) {
            return savingses[0];
        } else {
            return null;
        }
    }

    public static java.util.Iterator iterateSavingsByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateSavingsByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("iterateSavingsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateSavingsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateSavingsByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("iterateSavingsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateSavingsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.models.Savings as Savings");
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
            _logger.error("iterateSavingsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateSavingsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.models.Savings as Savings");
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
            _logger.error("iterateSavingsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Savings createSavings() {
        return new com.sift.easycoopfin.models.Savings();
    }

    public boolean save(com.sift.easycoopfin.models.Savings savings) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(savings);           
            return true;
        } catch (Exception e) {
            _logger.error("save(com.sift.models.Savings savings)", e);
            e.printStackTrace();
            throw new PersistentException(e);
        }
    }

    public boolean delete(com.sift.easycoopfin.models.Savings savings) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(savings);
            return true;
        } catch (Exception e) {
            _logger.error("delete(com.sift.models.Savings savings)", e);
            throw new PersistentException(e);
        }
    }

    public boolean refresh(com.sift.easycoopfin.models.Savings savings) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(savings);
            return true;
        } catch (Exception e) {
            _logger.error("refresh(com.sift.models.Savings savings)", e);
            throw new PersistentException(e);
        }
    }

    public boolean evict(com.sift.easycoopfin.models.Savings savings) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(savings);
            return true;
        } catch (Exception e) {
            _logger.error("evict(com.sift.models.Savings savings)", e);
            throw new PersistentException(e);
        }
    }

    public Savings loadSavingsByCriteria(SavingsCriteria savingsCriteria) {
        Savings[] savingses = listSavingsByCriteria(savingsCriteria);
        if (savingses == null || savingses.length == 0) {
            return null;
        }
        return savingses[0];
    }

    public Savings[] listSavingsByCriteria(SavingsCriteria savingsCriteria) {
        return savingsCriteria.listSavings();
    }

    @Override
    public List<Savings> listAllSavingByQuery(String condition, String orderBy) throws PersistentException {
        PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
        //StringBuffer sb = new StringBuffer("select Savings From com.sift.easycoopfin.models.Savings as Savings INNER JOIN Savings.accountNameobj as Accountnameobj");
       StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.Savings as Savings");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List<Savings> list = query.list();
           
            return list;
            //return (Company[]) list.toArray(new Company[list.size()]);
        } catch (Exception e) {
            _logger.error("listAllSavingsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public boolean add(com.sift.easycoopfin.models.Savings savings) throws PersistentException {
        PersistentSession session;
        boolean status = false;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();

            String sql1 = "INSERT INTO savings(company_id,branch_id,account_number,member_id,amount,description,user_id,reference_number,trx_date,product_id,trx_type,status)";
            sql1 += "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            int q = session.createSQLQuery(sql1)
                    .setParameter(0, savings.getCompanyId())
                    .setParameter(1, savings.getBranchId())
                    .setParameter(2, savings.getAccountNumber())
                    .setParameter(3, savings.getMemberId())
                    .setParameter(4, savings.getAmount())
                    .setParameter(5, savings.getDescription())
                    .setParameter(6, savings.getUserId())
                    .setParameter(7, savings.getReferenceNumber())
                    .setParameter(8, savings.getTrxDate())
                    .setParameter(9, savings.getProductId())
                    .setParameter(10, savings.getTrxType())
                    .setParameter(11, savings.getStatus())
                    .executeUpdate();
            System.out.println("Query result: " + q);
            if (q > 0) {
                status = true;
            } else {
                status = false;
            }

        } catch (PersistentException ex) {
            Logger.getLogger(SavingsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    /**
     * public void updateFileUpload(int id) {
     *
     * PersistentSession session; try { session =
     * com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
     * session.createQuery("update FileUpload set status= 1 where id = " +
     * id).executeUpdate();
     *
     * } catch (PersistentException ex) {
     * Logger.getLogger(SavingsDAOImpl.class.getName()).log(Level.SEVERE, null,
     * ex); } }
     *
     */
    public void updateFileUpload(int id, int successCount, int failureCount, BigDecimal processedSum, int fileCount) {

        PersistentSession session;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            System.out.println("processedSum in SavingsDAOImpl :: "  + processedSum);
            session.createQuery("update FileUpload set status= 1,success_count=" + successCount + ", failed_count=" + failureCount + ",processed_sum=" + processedSum + ",attempt_count=attempt_count+1 where id = " + id).executeUpdate();

        } catch (PersistentException ex) {
            Logger.getLogger(SavingsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } /**finally {
            try {
                com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().close();
            } catch (PersistentException ex) {
                Logger.getLogger(SavingsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }**/
    }

    public void updateSavingStatus(String id) {
        try {
            updateApproveStatus(id);
        } catch (PersistentException ex) {
            _logger.error("updateSavingStatus )", ex);
        } catch (Exception ex) {
            _logger.error("updateSavingStatus )", ex);
        }
    }

    public void updateApproveStatus(String id) throws PersistentException {
        PersistentSession session;
        try {
            t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();

            // sessionFactory.getCurrentSession().createSQLQuery("update branch_serial set serial=serial+1 where company_id="+coyId).executeUpdate(); 
            session.createSQLQuery("update savings set is_approved=1 where id in (" + id + ")").executeUpdate();
            // session.createQuery("update Savings set status= 1 where id ="+Integer.valueOf(id)).executeUpdate();
            t.commit();
        } catch (PersistentException ex) {
            t.rollback();
            _logger.error("updateApproveStatus )", ex);
        }
    }

    @Override
    public List<Savings> listAllSavingsByCriteria(SavingsCriteria savingsCriteria) {
        return savingsCriteria.listAllSavings();
    }

    @Override
    public SavingsAccount getSavingsAccountByNumber(String accountNumber, int branchId, int companyId) {
        PersistentSession session;
        SavingsAccount acc = null;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            //SQLQuery query = session.createSQLQuery("SELECT p.id, c.AccountNo, p.product_type_code, p.company_id, p.branch_id, a.Balance,c.title,p.name FROM custaccountdetails c join accounts  a on a.AccountNo=c.AccountNo join products p on p.code=c.Product  where a.AccountNo=" + accountNumber + " and p.branch_id=" + branchId + " and c.Companyid=" + companyId + " and c.Branchid=" + branchId);
            //SQLQuery query = session.createSQLQuery("SELECT p.id, c.AccountNo, p.product_type_code, p.company_id, p.branch_id, a.Balance,c.title,p.name FROM custaccountdetails c join accounts  a on a.AccountNo=c.AccountNo  and a.companyid=c.Companyid and a.branch=c.Branchid  join products p on p.code=c.Product  where a.AccountNo=" + accountNumber + " and p.branch_id=" + branchId + " and c.Companyid=" + companyId + " and c.Branchid=" + branchId);
            String queryString = "SELECT p.id, c.AccountNo, p.product_type_code, p.company_id, p.branch_id, a.Balance,c.title,p.name FROM custaccountdetails c join accounts  a on a.AccountNo=c.AccountNo  and a.companyid=c.Companyid and a.branch=c.Branchid  join products p on p.code=c.Product  where a.AccountNo=" + accountNumber + " and p.branch_id=" + branchId + " and c.Companyid=" + companyId + " and c.Branchid=" + branchId;
            System.out.println("queryString :: " + queryString);
            SQLQuery query = session.createSQLQuery(queryString);

            List<Object[]> rows = query.list();

            if (!rows.isEmpty()) {
                Object[] row = rows.get(0);
                acc = new SavingsAccount();
                acc.setId(Integer.valueOf(row[0].toString()));
                acc.setAccountNumber(row[1].toString());
                acc.setProductTypeCode(row[2].toString());
                acc.setCompanyId(Integer.valueOf(row[3].toString()));
                acc.setBranchId(Integer.valueOf(row[4].toString()));
                acc.setBalance(Float.valueOf(row[5].toString()));
                acc.setTitle(row[6].toString());
                acc.setName(row[7].toString());

            }
        } catch (PersistentException ex) {
            _logger.error("Join Error", ex);
        }
        return acc;
    }

    @Override
    public List<SavingsAccount> getSavingsAccountByBranch(int branchId, int companyId) {
        PersistentSession session;
        List<SavingsAccount> accounts = null;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            //SQLQuery query = session.createSQLQuery("SELECT p.id, c.AccountNo, p.product_type_code, p.company_id, p.branch_id, a.Balance,c.title,p.name FROM custaccountdetails c join accounts  a on a.AccountNo=c.AccountNo join products p on p.code=c.Product  where p.company_id=" + companyId + " and p.branch_id=" + branchId + " and c.Companyid=" + companyId + " and c.Branchid=" + branchId + " and (p.product_type_code='C' or p.product_type_code='S')");
            SQLQuery query = session.createSQLQuery("SELECT p.id, c.AccountNo, p.product_type_code, p.company_id, p.branch_id, a.Balance,c.title,p.name FROM custaccountdetails c  join accounts  a on a.AccountNo=c.AccountNo and a.companyid=c.Companyid and a.branch=c.Branchid   join products p on p.code=c.Product and p.company_id = c.Companyid and p.branch_id = c.Branchid where p.company_id=c.Companyid and p.branch_id=c.Branchid  and c.Companyid=" + companyId + " and c.Branchid=" + branchId + " and (p.product_type_code='C' or p.product_type_code='S')");

            List<Object[]> rows = query.list();

            if (!rows.isEmpty()) {
                accounts = new ArrayList<SavingsAccount>();
                for (Object[] row : rows) {
                    SavingsAccount acc = new SavingsAccount();

                    acc.setId(Integer.valueOf(row[0].toString()));
                    acc.setAccountNumber(row[1].toString());
                    acc.setProductTypeCode(row[2].toString());
                    acc.setCompanyId(Integer.valueOf(row[3].toString()));
                    acc.setBranchId(Integer.valueOf(row[4].toString()));
                    acc.setBalance(Float.valueOf(row[5].toString()));
                    acc.setTitle(row[6].toString());
                    acc.setName(row[7].toString());
                    accounts.add(acc);
                }
            }
        } catch (PersistentException ex) {
            _logger.error("Join Error", ex);
        }
        return accounts;

    }

    @Override
    public List<SavingsAccount> getAllSavingsAccountByBranch(int branchId, int companyId) {
        PersistentSession session;
        List<SavingsAccount> accounts = null;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            //SQLQuery query = session.createSQLQuery("SELECT p.id, c.AccountNo, p.product_type_code, p.company_id, p.branch_id, a.Balance,c.title,p.name, p.code FROM custaccountdetails c join accounts  a on a.AccountNo=c.AccountNo join products p on p.code=c.Product  where p.company_id=" + companyId + " and p.branch_id=" + branchId + " and c.Companyid=" + companyId + " and c.Branchid=" + branchId);
            SQLQuery query = session.createSQLQuery("SELECT p.id, c.AccountNo, p.product_type_code, p.company_id, p.branch_id, a.Balance,c.title,p.name, p.code FROM custaccountdetails c join accounts  a on a.AccountNo=c.AccountNo and a.companyid=c.Companyid and a.branch=c.Branchid join products p on p.code=c.Product  where p.company_id=" + companyId + " and p.branch_id=" + branchId + " and c.Companyid=" + companyId + " and c.Branchid=" + branchId);
            
            List<Object[]> rows = query.list();

            if (!rows.isEmpty()) {
                accounts = new ArrayList<SavingsAccount>();
                for (Object[] row : rows) {
                    SavingsAccount acc = new SavingsAccount();

                    acc.setId(Integer.valueOf(row[0].toString()));
                    acc.setAccountNumber(row[1].toString());
                    acc.setProductTypeCode(row[2].toString());
                    acc.setCompanyId(Integer.valueOf(row[3].toString()));
                    acc.setBranchId(Integer.valueOf(row[4].toString()));
                    acc.setBalance(Float.valueOf(row[5].toString()));
                    acc.setTitle(row[6].toString());
                    acc.setName(row[7].toString());
                    acc.setProductCode(row[8].toString());
                    accounts.add(acc);
                }
            }
        } catch (PersistentException ex) {
            _logger.error("Join Error", ex);
        }
        return accounts;

    }
}
