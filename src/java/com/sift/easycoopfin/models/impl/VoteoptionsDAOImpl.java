
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.VoteoptionsCriteria;
import com.sift.easycoopfin.models.Voteoptions;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class VoteoptionsDAOImpl implements com.sift.easycoopfin.models.dao.VoteoptionsDAO {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VoteoptionsDAOImpl.class);
	public Voteoptions loadVoteoptionsByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVoteoptionsByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadVoteoptionsByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions getVoteoptionsByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getVoteoptionsByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getVoteoptionsByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions loadVoteoptionsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVoteoptionsByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadVoteoptionsByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions getVoteoptionsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getVoteoptionsByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getVoteoptionsByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions loadVoteoptionsByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Voteoptions) session.load(com.sift.easycoopfin.models.Voteoptions.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadVoteoptionsByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions getVoteoptionsByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Voteoptions) session.get(com.sift.easycoopfin.models.Voteoptions.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getVoteoptionsByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions loadVoteoptionsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Voteoptions) session.load(com.sift.easycoopfin.models.Voteoptions.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadVoteoptionsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions getVoteoptionsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Voteoptions) session.get(com.sift.easycoopfin.models.Voteoptions.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getVoteoptionsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions[] listVoteoptionsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listVoteoptionsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listVoteoptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions[] listVoteoptionsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listVoteoptionsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listVoteoptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions[] listVoteoptionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Voteoptions as Voteoptions");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Voteoptions[]) list.toArray(new Voteoptions[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listVoteoptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions[] listVoteoptionsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Voteoptions as Voteoptions");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Voteoptions[]) list.toArray(new Voteoptions[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listVoteoptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions loadVoteoptionsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVoteoptionsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadVoteoptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions loadVoteoptionsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVoteoptionsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadVoteoptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions loadVoteoptionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Voteoptions[] voteoptionses = listVoteoptionsByQuery(session, condition, orderBy);
		if (voteoptionses != null && voteoptionses.length > 0)
			return voteoptionses[0];
		else
			return null;
	}
	
	public Voteoptions loadVoteoptionsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Voteoptions[] voteoptionses = listVoteoptionsByQuery(session, condition, orderBy, lockMode);
		if (voteoptionses != null && voteoptionses.length > 0)
			return voteoptionses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateVoteoptionsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateVoteoptionsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateVoteoptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateVoteoptionsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateVoteoptionsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateVoteoptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateVoteoptionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Voteoptions as Voteoptions");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateVoteoptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateVoteoptionsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Voteoptions as Voteoptions");
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
			_logger.error("iterateVoteoptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions createVoteoptions() {
		return new com.sift.easycoopfin.models.Voteoptions();
	}
	
	public boolean save(com.sift.easycoopfin.models.Voteoptions voteoptions) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(voteoptions);
			return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.models.Voteoptions voteoptions)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.Voteoptions voteoptions) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(voteoptions);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.models.Voteoptions voteoptions)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.Voteoptions voteoptions) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(voteoptions);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.models.Voteoptions voteoptions)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.Voteoptions voteoptions) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(voteoptions);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.models.Voteoptions voteoptions)", e);
			throw new PersistentException(e);
		}
	}
	
	public Voteoptions loadVoteoptionsByCriteria(VoteoptionsCriteria voteoptionsCriteria) {
		Voteoptions[] voteoptionses = listVoteoptionsByCriteria(voteoptionsCriteria);
		if(voteoptionses == null || voteoptionses.length == 0) {
			return null;
		}
		return voteoptionses[0];
	}
	
	public Voteoptions[] listVoteoptionsByCriteria(VoteoptionsCriteria voteoptionsCriteria) {
		return voteoptionsCriteria.listVoteoptions();
	}

    @Override
    public List<Voteoptions> listAllVoteoptionsByCriteria(VoteoptionsCriteria voteoptionsCriteria) {
        return voteoptionsCriteria.listAllVoteoptions();
    }
}
