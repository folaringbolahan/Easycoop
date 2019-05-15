
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.VotesCriteria;
import com.sift.easycoopfin.models.Votes;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class VotesDAOImpl implements com.sift.easycoopfin.models.dao.VotesDAO {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VotesDAOImpl.class);
	public Votes loadVotesByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVotesByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("loadVotesByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes getVotesByORMID(int id) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getVotesByORMID(session, id);
		}
		catch (Exception e) {
			_logger.error("getVotesByORMID(int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes loadVotesByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVotesByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadVotesByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes getVotesByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return getVotesByORMID(session, id, lockMode);
		}
		catch (Exception e) {
			_logger.error("getVotesByORMID(int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes loadVotesByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Votes) session.load(com.sift.easycoopfin.models.Votes.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("loadVotesByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes getVotesByORMID(PersistentSession session, int id) throws PersistentException {
		try {
			return (Votes) session.get(com.sift.easycoopfin.models.Votes.class, new Integer(id));
		}
		catch (Exception e) {
			_logger.error("getVotesByORMID(PersistentSession session, int id)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes loadVotesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Votes) session.load(com.sift.easycoopfin.models.Votes.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadVotesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes getVotesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Votes) session.get(com.sift.easycoopfin.models.Votes.class, new Integer(id), lockMode);
		}
		catch (Exception e) {
			_logger.error("getVotesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes[] listVotesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listVotesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listVotesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes[] listVotesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return listVotesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listVotesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes[] listVotesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Votes as Votes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Votes[]) list.toArray(new Votes[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listVotesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes[] listVotesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Votes as Votes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Votes[]) list.toArray(new Votes[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listVotesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes loadVotesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVotesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadVotesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes loadVotesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return loadVotesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadVotesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes loadVotesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Votes[] voteses = listVotesByQuery(session, condition, orderBy);
		if (voteses != null && voteses.length > 0)
			return voteses[0];
		else
			return null;
	}
	
	public Votes loadVotesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Votes[] voteses = listVotesByQuery(session, condition, orderBy, lockMode);
		if (voteses != null && voteses.length > 0)
			return voteses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateVotesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateVotesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateVotesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateVotesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
			return iterateVotesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateVotesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateVotesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Votes as Votes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateVotesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateVotesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From com.sift.models.Votes as Votes");
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
			_logger.error("iterateVotesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes createVotes() {
		return new com.sift.easycoopfin.models.Votes();
	}
	
	public boolean save(com.sift.easycoopfin.models.Votes votes) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(votes);
			return true;
		}
		catch (Exception e) {
			_logger.error("save(com.sift.models.Votes votes)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete(com.sift.easycoopfin.models.Votes votes) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(votes);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete(com.sift.models.Votes votes)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh(com.sift.easycoopfin.models.Votes votes) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(votes);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh(com.sift.models.Votes votes)", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict(com.sift.easycoopfin.models.Votes votes) throws PersistentException {
		try {
			com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(votes);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict(com.sift.models.Votes votes)", e);
			throw new PersistentException(e);
		}
	}
	
	public Votes loadVotesByCriteria(VotesCriteria votesCriteria) {
		Votes[] voteses = listVotesByCriteria(votesCriteria);
		if(voteses == null || voteses.length == 0) {
			return null;
		}
		return voteses[0];
	}
	
	public Votes[] listVotesByCriteria(VotesCriteria votesCriteria) {
		return votesCriteria.listVotes();
	}

    @Override
    public List<Votes> listAllVotesByCriteria(VotesCriteria votesCriteria) {
        return votesCriteria.listAllVotes();
    }
}
