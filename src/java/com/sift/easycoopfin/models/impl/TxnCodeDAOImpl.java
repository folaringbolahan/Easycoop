/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.TxnCode;
import com.sift.easycoopfin.models.TxnCodeCriteria;
import com.sift.easycoopfin.models.dao.TxnCodeDAO;
import java.util.List;
import org.hibernate.Query;
import org.orm.PersistentException;
import org.orm.PersistentSession;

/**
 *
 * @author logzy
 */
public class TxnCodeDAOImpl implements TxnCodeDAO{
    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(TxnCodeDAOImpl.class);
	public TxnCode loadTxnCodeByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadTxnCodeByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadTxnCodeByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode getTxnCodeByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getTxnCodeByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getTxnCodeByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode loadTxnCodeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadTxnCodeByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadTxnCodeByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode getTxnCodeByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getTxnCodeByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getTxnCodeByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode loadTxnCodeByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (TxnCode) session.load(com.sift.easycoopfin.models.TxnCode.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadTxnCodeByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode getTxnCodeByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (TxnCode) session.get(com.sift.easycoopfin.models.TxnCode.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getTxnCodeByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode loadTxnCodeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (TxnCode) session.load(com.sift.easycoopfin.models.TxnCode.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadTxnCodeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode getTxnCodeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (TxnCode) session.get(com.sift.easycoopfin.models.TxnCode.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getTxnCodeByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode[] listTxnCodeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listTxnCodeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listTxnCodeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode[] listTxnCodeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listTxnCodeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listTxnCodeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode[] listTxnCodeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.TxnCode as TxnCode");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (TxnCode[]) list.toArray(new TxnCode[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listTxnCodeByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode[] listTxnCodeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.TxnCode as TxnCode");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (TxnCode[]) list.toArray(new TxnCode[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listTxnCodeByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode loadTxnCodeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadTxnCodeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadTxnCodeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode loadTxnCodeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadTxnCodeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadTxnCodeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode loadTxnCodeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		TxnCode[] voteoptionses = listTxnCodeByQuery(session, condition, orderBy);
		if (voteoptionses != null && voteoptionses.length > 0)
			return voteoptionses[0];
		else
			return null;
	}
	
	public TxnCode loadTxnCodeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		TxnCode[] voteoptionses = listTxnCodeByQuery(session, condition, orderBy, lockMode);
		if (voteoptionses != null && voteoptionses.length > 0)
			return voteoptionses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateTxnCodeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateTxnCodeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateTxnCodeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateTxnCodeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateTxnCodeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateTxnCodeByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateTxnCodeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.TxnCode as TxnCode");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateTxnCodeByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateTxnCodeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.TxnCode as TxnCode");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateTxnCodeByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode createTxnCode() {
		return new com.sift.easycoopfin.models.TxnCode();
	}
	
	public boolean save(com.sift.easycoopfin.models.TxnCode txnCode) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(txnCode);
			return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.models.TxnCode txnCode)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.TxnCode txnCode) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(txnCode);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.models.TxnCode txnCode)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.TxnCode voteoptions) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(voteoptions);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.models.TxnCode txnCode)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.TxnCode voteoptions) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(voteoptions);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.models.TxnCode txnCode)", e);
			throw new PersistentException(e);
		}
	}
	
	public TxnCode loadTxnCodeByCriteria(TxnCodeCriteria txnCodeCriteria) {
		TxnCode[] txnCodes = listTxnCodeByCriteria(txnCodeCriteria);
		if(txnCodes == null || txnCodes.length == 0) {
			return null;
		}
		return txnCodes[0];
	}
	
	public TxnCode[] listTxnCodeByCriteria(TxnCodeCriteria txnCodesCriteria) {
		return txnCodesCriteria.listTxnCode();
	}

    @Override
    public List<TxnCode> listAllTxnCodeByCriteria(TxnCodeCriteria txnCodesCriteria) {
        return txnCodesCriteria.listAllTxnCode();
    }
}
