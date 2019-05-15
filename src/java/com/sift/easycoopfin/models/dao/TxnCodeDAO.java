/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.TxnCode;
import com.sift.easycoopfin.models.TxnCodeCriteria;
import java.util.List;
import org.orm.PersistentException;
import org.orm.PersistentSession;

/**
 *
 * @author logzy
 */
public interface TxnCodeDAO {
    public TxnCode loadTxnCodeByORMID(int id) throws PersistentException;
	public TxnCode getTxnCodeByORMID(int id) throws PersistentException;
	public TxnCode loadTxnCodeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public TxnCode getTxnCodeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public TxnCode loadTxnCodeByORMID(PersistentSession session, int id) throws PersistentException;
	public TxnCode getTxnCodeByORMID(PersistentSession session, int id) throws PersistentException;
	public TxnCode loadTxnCodeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public TxnCode getTxnCodeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public TxnCode[] listTxnCodeByQuery(String condition, String orderBy) throws PersistentException;
	public TxnCode[] listTxnCodeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public TxnCode[] listTxnCodeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public TxnCode[] listTxnCodeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public TxnCode loadTxnCodeByQuery(String condition, String orderBy) throws PersistentException;
	public TxnCode loadTxnCodeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public TxnCode loadTxnCodeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public TxnCode loadTxnCodeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public TxnCode createTxnCode();
	public boolean save(com.sift.easycoopfin.models.TxnCode voteoptions) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.TxnCode voteoptions) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.TxnCode voteoptions) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.TxnCode voteoptions) throws PersistentException;
	public TxnCode loadTxnCodeByCriteria(TxnCodeCriteria voteoptionsCriteria);
	public TxnCode[] listTxnCodeByCriteria(TxnCodeCriteria voteoptionsCriteria);
        public List<TxnCode> listAllTxnCodeByCriteria(TxnCodeCriteria voteoptionsCriteria);
}
