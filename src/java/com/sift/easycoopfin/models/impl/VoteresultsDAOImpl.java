
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.Voteresults;
import com.sift.easycoopfin.models.VoteresultsCriteria;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class VoteresultsDAOImpl implements com.sift.easycoopfin.models.dao.VoteresultsDAO {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VoteresultsDAOImpl.class);
	public Voteresults loadVoteresultsByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVoteresultsByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadVoteresultsByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults getVoteresultsByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getVoteresultsByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getVoteresultsByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults loadVoteresultsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVoteresultsByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadVoteresultsByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults getVoteresultsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getVoteresultsByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getVoteresultsByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults loadVoteresultsByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Voteresults) session.load(com.sift.easycoopfin.models.Voteresults.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadVoteresultsByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults getVoteresultsByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Voteresults) session.get(com.sift.easycoopfin.models.Voteresults.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getVoteresultsByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults loadVoteresultsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Voteresults) session.load(com.sift.easycoopfin.models.Voteresults.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadVoteresultsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults getVoteresultsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Voteresults) session.get(com.sift.easycoopfin.models.Voteresults.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getVoteresultsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults[] listVoteresultsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listVoteresultsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listVoteresultsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults[] listVoteresultsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listVoteresultsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listVoteresultsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults[] listVoteresultsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Voteresults as Voteresults");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Voteresults[]) list.toArray(new Voteresults[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listVoteresultsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults[] listVoteresultsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Voteresults as Voteresults");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Voteresults[]) list.toArray(new Voteresults[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listVoteresultsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults loadVoteresultsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVoteresultsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadVoteresultsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults loadVoteresultsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVoteresultsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadVoteresultsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults loadVoteresultsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Voteresults[] voteresultses = listVoteresultsByQuery(session, condition, orderBy);
		if (voteresultses != null && voteresultses.length > 0)
			return voteresultses[0];
		else
			return null;
	}
	
	public Voteresults loadVoteresultsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Voteresults[] voteresultses = listVoteresultsByQuery(session, condition, orderBy, lockMode);
		if (voteresultses != null && voteresultses.length > 0)
			return voteresultses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateVoteresultsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateVoteresultsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateVoteresultsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateVoteresultsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateVoteresultsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateVoteresultsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateVoteresultsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Voteresults as Voteresults");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateVoteresultsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateVoteresultsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Voteresults as Voteresults");
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
			_logger.error("iterateVoteresultsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults createVoteresults() {
		return new com.sift.easycoopfin.models.Voteresults();
	}
	
	public boolean save(com.sift.easycoopfin.models.Voteresults voteresults) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(voteresults);
			return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.models.Voteresults voteresults)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.Voteresults voteresults) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(voteresults);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.models.Voteresults voteresults)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.Voteresults voteresults) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(voteresults);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.models.Voteresults voteresults)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.Voteresults voteresults) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(voteresults);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.models.Voteresults voteresults)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteresults loadVoteresultsByCriteria(VoteresultsCriteria voteresultsCriteria) {
		Voteresults[] voteresultses = listVoteresultsByCriteria(voteresultsCriteria);
		if(voteresultses == null || voteresultses.length == 0) {
			return null;
		}
		return voteresultses[0];
	}
	
	public Voteresults[] listVoteresultsByCriteria(VoteresultsCriteria voteresultsCriteria) {
		return voteresultsCriteria.listVoteresults();
	}

    @Override
    public List<Voteresults> listAllVoteresultsByCriteria(VoteresultsCriteria voteresultsCriteria) {
       return voteresultsCriteria.listAllVoteresults();
    }
}
