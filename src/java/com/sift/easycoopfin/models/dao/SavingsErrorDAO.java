/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.SavingsError;
import com.sift.easycoopfin.models.SavingsErrorCriteria;
import org.orm.*;
import java.util.List;

/**
 *
 * @author logzy
 */
public interface SavingsErrorDAO {

    public SavingsError loadSavingsErrorByORMID(int id) throws PersistentException;

    public SavingsError getSavingsErrorByORMID(int id) throws PersistentException;

    public SavingsError loadSavingsErrorByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;

    public SavingsError getSavingsErrorByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;

    public SavingsError loadSavingsErrorByORMID(PersistentSession session, int id) throws PersistentException;

    public SavingsError getSavingsErrorByORMID(PersistentSession session, int id) throws PersistentException;

    public SavingsError loadSavingsErrorByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;

    public SavingsError getSavingsErrorByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;

    public SavingsError[] listSavingsErrorByQuery(String condition, String orderBy) throws PersistentException;

    public SavingsError[] listSavingsErrorByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;

    public SavingsError[] listSavingsErrorByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;

    public SavingsError[] listSavingsErrorByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;

    public SavingsError loadSavingsErrorByQuery(String condition, String orderBy) throws PersistentException;

    public SavingsError loadSavingsErrorByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;

    public SavingsError loadSavingsErrorByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;

    public SavingsError loadSavingsErrorByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;

    public SavingsError createSavingsError();

    public boolean save(com.sift.easycoopfin.models.SavingsError savingsError) throws PersistentException;

    public boolean delete(com.sift.easycoopfin.models.SavingsError savingsError) throws PersistentException;

    public boolean refresh(com.sift.easycoopfin.models.SavingsError savingsError) throws PersistentException;

    public boolean evict(com.sift.easycoopfin.models.SavingsError savingsError) throws PersistentException;

    public SavingsError loadSavingsErrorByCriteria(SavingsErrorCriteria savingsErrorCriteria);

    public SavingsError[] listSavingsErrorByCriteria(SavingsErrorCriteria savingsErrorCriteria);

    public List<SavingsError> listAllSavingByQuery(String condition, String orderBy) throws PersistentException;
    public List<SavingsError> listAllSavingsErrorByCriteria(SavingsErrorCriteria savingsErrorCriteria);
}
