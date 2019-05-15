/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.Custaccountdetails;
import com.sift.easycoopfin.models.CustaccountdetailsCriteria;
import com.sift.easycoopfin.models.dao.AccountDAO;
import com.sift.gl.model.Account;
import java.util.List;
import org.orm.PersistentException;
import org.orm.PersistentSession;

import java.math.BigInteger;
import java.util.ArrayList;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author logzy
 */
public class AccountDAOImpl implements AccountDAO {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(AccountDAOImpl.class);

    public List<com.sift.gl.model.Account> getAllAccountsByQuery(String sql) {
        PersistentSession session;
        List<com.sift.gl.model.Account> accounts = new ArrayList<com.sift.gl.model.Account>();
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            SQLQuery query = session.createSQLQuery(sql);

            List<Object[]> rows = query.list();
            if (rows != null) {
                for (Object[] row : rows) {
                    com.sift.gl.model.Account account = new com.sift.gl.model.Account();
                    account.setAcId(Integer.valueOf(row[0].toString()));
                    account.setAccountNo(row[1].toString());
                    account.setName(row[2].toString());
                    account.setAcType(row[4].toString());
                    account.setAcGroup(Integer.valueOf(row[5].toString()));
                    account.setAcStruct(row[6].toString());
                    if (row[17] == null) {
                        account.setAsegc("");
                    } else {
                        account.setAsegc(row[17].toString());
                    }

                    account.setBranch(Integer.valueOf(row[30].toString()));
                    account.setCompany(Integer.valueOf(row[31].toString()));
                    account.setControlAccount(Boolean.valueOf(row[20].toString()));
                    account.setCurrency(row[18].toString());
                    accounts.add(account);
                }
            }

            return accounts;
        } catch (PersistentException ex) {
            _logger.error("getAllAccountsByQuery(String query)", ex);
        }
        return accounts;
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber, int branchId, int companyId) {
        PersistentSession session;
        Account account = null;
        String cc="";
        try {
            String sql = "select * from `accounts`  account where AccountNo='" + accountNumber + "' and Branch=" + branchId + " and Companyid=" + companyId;
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            SQLQuery query = session.createSQLQuery(sql);

            List<Object[]> rows = query.list();
            System.out.println("Size of rows: " + rows.size()+" Branch: "+branchId+" company "+companyId);
            if (rows.size() <= 0) {
                account = null;
            } else {
                Object[] row = rows.get(0);
                account = new Account();
                account.setAcId(Integer.valueOf(row[0].toString()));
                account.setAccountNo(row[1].toString());
                cc  = row[1].toString();
                account.setName(row[2].toString());
                account.setAcType(row[4].toString());
                account.setAcGroup(Integer.valueOf(row[5].toString()));
                account.setAcStruct(row[6].toString());
                if (row[17] == null) {
                    account.setAsegc("");
                } else {
                    account.setAsegc(row[17].toString());
                }

                account.setBranch(Integer.valueOf(row[30].toString()));
                account.setCompany(Integer.valueOf(row[31].toString()));
                account.setControlAccount(Boolean.valueOf(row[20].toString()));
                account.setCurrency(row[18].toString());
            }

        } catch (PersistentException ex) {
            _logger.error("getAllAccountsByQuery(String query)", ex);
        }/**finally{
            try {
                com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().close();
            } catch (PersistentException ex) {
                Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }**/
        System.out.println("Account :: " + cc);
        return account;
    }

    @Override
    public Custaccountdetails getCustomerAccountByAccountNumber(String accountNumber, int branchId, int companyId) {
       Custaccountdetails account = null ;
        try {
            CustaccountdetailsCriteria criteria = new CustaccountdetailsCriteria();
            criteria.add(Restrictions.eq("id",accountNumber));
            criteria.add(Restrictions.eq("branchId",branchId));
            criteria.add(Restrictions.eq("companyId",companyId));
            
            account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCustaccountdetailsDAO().loadAccountByCriteria(criteria);
            
        } catch (PersistentException ex) {
            _logger.error("getCustomerAccountByAccountNumber(String accountNumber, int branchId, int companyId)", ex);
        }
       return account;
    }
}
