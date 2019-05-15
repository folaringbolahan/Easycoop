
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.Savings;
import com.sift.easycoopfin.models.SavingsAccount;
import com.sift.easycoopfin.models.SavingsCriteria;
import java.math.BigDecimal;
import java.util.List;
import org.orm.*;

public interface SavingsDAO {
	public Savings loadSavingsByORMID(int id) throws PersistentException;
	public Savings getSavingsByORMID(int id) throws PersistentException;
	public Savings loadSavingsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Savings getSavingsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Savings loadSavingsByORMID(PersistentSession session, int id) throws PersistentException;
	public Savings getSavingsByORMID(PersistentSession session, int id) throws PersistentException;
	public Savings loadSavingsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Savings getSavingsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Savings[] listSavingsByQuery(String condition, String orderBy) throws PersistentException;
	public Savings[] listSavingsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Savings[] listSavingsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Savings[] listSavingsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Savings loadSavingsByQuery(String condition, String orderBy) throws PersistentException;
	public Savings loadSavingsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Savings loadSavingsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Savings loadSavingsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Savings createSavings();
	public boolean save(com.sift.easycoopfin.models.Savings savings) throws PersistentException;
        public boolean add(com.sift.easycoopfin.models.Savings savings) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.Savings savings) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.Savings savings) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.Savings savings) throws PersistentException;
	public Savings loadSavingsByCriteria(SavingsCriteria savingsCriteria);
	public Savings[] listSavingsByCriteria(SavingsCriteria savingsCriteria);
        public List<Savings> listAllSavingByQuery(String condition, String orderBy) throws PersistentException;
        public List<Savings> listAllSavingsByCriteria(SavingsCriteria savingsCriteria);
        public void updateFileUpload(int id,int successCount,int failureCount,BigDecimal processedSum,int fileCount);
        public void updateSavingStatus(String id);
        public List<SavingsAccount> getSavingsAccountByBranch(int branchId, int companyId);
        public List<SavingsAccount> getAllSavingsAccountByBranch(int branchId, int companyId);
        public SavingsAccount getSavingsAccountByNumber(String accountNumber, int branchId, int companyId);
       // public void updateFileUpload(int id);
}
